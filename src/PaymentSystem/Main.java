package PaymentSystem;

import PaymentSystem.Repository.*;
import PaymentSystem.Service.*;
import PaymentSystem.UI.*;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        UserService userService = new UserService(userRepository);
        PaymentService paymentService = new PaymentService(userRepository, transactionRepository);

        ConsoleUI ui = new ConsoleUI(userService, paymentService);

        ui.start();
    }
}