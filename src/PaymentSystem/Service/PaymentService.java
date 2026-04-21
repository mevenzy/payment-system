package PaymentSystem.Service;

import PaymentSystem.Entity.*;
import PaymentSystem.Exception.*;
import PaymentSystem.Repository.*;

public class PaymentService {
    private final UserRepository userRepo;
    private final TransactionRepository transactionRepo;

    public PaymentService(UserRepository userRepo, TransactionRepository transactionRepo) {
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
    }


    // Перевести деньги
    public void transfer(String fromName, String toName, double amount, PaymentMethod method) {
        if (amount <= 0) {
            throw new InvalidPaymentException("сумма должна быть больше 0₽");
        }

        User from = userRepo.findByName(fromName).
                orElseThrow(() -> new UserNotFoundException("пользователь не найден: " + fromName));

        User to = userRepo.findByName(toName).
                orElseThrow(() -> new UserNotFoundException("пользователь не найден: " + toName));

        if (fromName.equals(toName)) {
            throw new SameUserTransferException("нельзя перевести самому себе");
        }

        if (from.getBalance() < amount) {
            throw new InsufficientFundsException("недостоточно средств");
        }

        ValidatePaymentMethod(method, amount);

        double fromOldBalance = from.getBalance();
        double toOldBalance = to.getBalance();

        try {
            from.setBalance(fromOldBalance - amount);
            to.setBalance(toOldBalance + amount);

            Transaction transaction = new Transaction(
                generateId(),
                fromName,
                toName,
                amount,
                method,
                TransactionType.TRANSFER
            );

            transactionRepo.save(transaction);

            System.out.println("✅ Перевод выполнен");

        } catch (Exception e) {
            from.setBalance(fromOldBalance);
            to.setBalance(toOldBalance);

            throw new RuntimeException("❌ Произошла ошибка. Операция отменена. " + e.getMessage());
        }
    }


    // Пополнить баланс
    public void deposit(String name, double amount) {
        if (amount <= 0) {
            throw new InvalidPaymentException("сумма должна быть больше 0₽");
        }

        User user = userRepo.findByName(name).
                orElseThrow(() -> new UserNotFoundException("пользователь не найден"));

        double oldBalance = user.getBalance();

        try {
            user.setBalance(oldBalance + amount);

            Transaction transaction = new Transaction(
                    generateId(),
                    null,
                    name,
                    amount,
                    PaymentMethod.CARD,
                    TransactionType.DEPOSIT
            );

            transactionRepo.save(transaction);

            System.out.println("✅ Баланс успешно пополнен");

        } catch (Exception e) {
            System.out.println("❌ Произошла ошибка. Операция отменена. " + e.getMessage());
        }
    }


    // Снять деньги
    public void withdraw(String name, double amount) {
        if (amount <= 0) {
            throw new InvalidPaymentException("сумма должна быть больше 0₽");
        }

        User user = userRepo.findByName(name).
                orElseThrow(() -> new UserNotFoundException("пользователь не найден"));

        if (user.getBalance() < amount) {
            throw new InsufficientFundsException("недостаточно средств");
        }

        double oldBalance = user.getBalance();

        try {
            user.setBalance(oldBalance - amount);

            Transaction transaction = new Transaction(
                    generateId(),
                    name,
                    null,
                    amount,
                    PaymentMethod.CARD,
                    TransactionType.WITHDRAW
            );

            transactionRepo.save(transaction);

            System.out.println("✅ Списание прошло успешно");

        } catch (Exception e) {
            System.out.println("❌ Произошла ошибка. Операция отменена. " + e.getMessage());
        }
    }


    // История транзакций
    public void getTransactionsHistory() {
        if (transactionRepo.findAll().isEmpty()) {
            System.out.println("⚠️ История пуста");
            return;
        }

        System.out.println("=== ИСТОРИЯ ОПЕРАЦИЙ ===");
        for (Transaction transaction : transactionRepo.findAll()) {

            switch (transaction.getType()) {
                case TRANSFER -> System.out.println("#" + transaction.getId() +
                            " | " + transaction.getType().toString() +
                            " | " + transaction.getFromUser() + " → " + transaction.getToUser() +
                            " | " + transaction.getAmount() +
                            " | " + transaction.getMethod());

                case DEPOSIT -> System.out.println("#" + transaction.getId() +
                            " | " + transaction.getType().toString() +
                            " | " + transaction.getToUser() +
                            " | +" + transaction.getAmount());

                case WITHDRAW -> System.out.println("#" + transaction.getId() +
                            " | " + transaction.getType().toString() +
                            " | " + transaction.getFromUser() +
                            " | -" + transaction.getAmount());
            }
        }
    }


    // Валидация метода оплаты
    public void ValidatePaymentMethod(PaymentMethod method, double amount) {
        if (method == null) {
            throw new InvalidPaymentException("способ оплаты не указан");
        }

        switch (method) {
            case CRYPTO:
                if (amount < 100) {
                    throw new InvalidPaymentException("минимальный перевод для криптовалюты = 100");
                }
                break;

            case CASH:
                if (amount > 5000) {
                    throw new InvalidPaymentException("максимальный перевод наличными = 5000");
                }
                break;

            case CARD:
                break;

            default:
                throw new InvalidPaymentException("неизвестный способ оплаты");
        }
    }


    // Генерация id
    private int generateId() {
        return (int) (Math.random() * 10_000);
    }
}