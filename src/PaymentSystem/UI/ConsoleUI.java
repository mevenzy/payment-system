package PaymentSystem.UI;

import PaymentSystem.Entity.*;
import PaymentSystem.Service.*;
import java.util.*;

public class ConsoleUI {
    private final UserService userService;
    private final PaymentService paymentService;

    Scanner scanner = new Scanner(System.in);

    public ConsoleUI(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    public void start() {
        while (true) {
            printMenu();

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice){
                    case 1 -> createUser();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> transfer();
                    case 5 -> getAllUsers();
                    case 6 -> getTransactionsHistory();
                    case 0 -> {
                        System.out.println("Выход...");
                        return;
                    }
                    default -> {
                        System.out.println("❌ Вы ввели неправильную команду, попробуйте еще раз");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Введите число!");
            } catch (Exception e) {
                System.out.println("❌ Произошла ошибка: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("==== PAYMENT SYSTEM ====");
        System.out.println("1. Создать пользователя");
        System.out.println("2. Пополнить баланс");
        System.out.println("3. Снять деньги");
        System.out.println("4. Перевести деньги");
        System.out.println("5. Показать всех пользователей");
        System.out.println("6. История транзакций");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }


    // Создать пользователя
    private void createUser() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        userService.addUser(name);
    }


    // Пополнить баланс
    private void deposit() {
        System.out.print("Введите имя пользователя: ");
        String name = scanner.nextLine();

        System.out.print("Введите сумму: ");
        double amount = Double.parseDouble(scanner.nextLine());

        paymentService.deposit(name, amount);
    }


    // Снять деньги
    private void withdraw() {
        System.out.print("Введите имя пользователя: ");
        String name = scanner.nextLine();

        System.out.print("Введите сумму: ");
        double amount = Double.parseDouble(scanner.nextLine());

        paymentService.withdraw(name, amount);
    }


    // Перевести деньги
    private void transfer() {
        System.out.print("От кого: ");
        String fromName = scanner.nextLine();

        System.out.print("Кому: ");
        String toName = scanner.nextLine();

        System.out.print("Введите сумму: ");
        double amount = Double.parseDouble(scanner.nextLine());

        PaymentMethod method = choosePaymentMethod();

        paymentService.transfer(fromName, toName, amount, method);
    }


    // Показать всех пользователей
    private void getAllUsers() {
        userService.getAllUsers();
    }


    // История транзакций
    private void getTransactionsHistory() {
        paymentService.getTransactionsHistory();
    }


    // Выбрать способ оплаты
    private PaymentMethod choosePaymentMethod() {
        System.out.println("Введите способ оплаты: ");

        PaymentMethod[] methods = PaymentMethod.values();

        for (int i = 0; i < methods.length; i++) {
            System.out.println((i + 1) + ". " + methods[i].toString());
        }

        int choice = Integer.parseInt(scanner.nextLine());

        if (choice < 1 || choice > methods.length) {
            throw new IllegalArgumentException("неизвестный способ оплаты");
        }

        return methods[choice - 1];
    }
}