package PaymentSystem.Service;

import PaymentSystem.Entity.*;
import PaymentSystem.Exception.*;
import PaymentSystem.Repository.*;

public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }


    // Создать пользователя
    public void addUser(String name) {
        validateName(name);

        userRepo.findByName(name).ifPresent(user -> {
            throw new DuplicateUserException("такой пользователь уже существует");
        });

        User user = new User(name, 0.0);
        userRepo.addUser(user);
        System.out.println("✅ Пользователь успешно добавлен");
    }


    // Показать всех пользователей
    public void getAllUsers() {
        if (userRepo.findAll().isEmpty()) {
            System.out.println("⚠️ Список пуст");
            return;
        }

        System.out.println("=== СПИСОК ПОЛЬЗОВАТЕЛЕЙ ===");
        for (User user : userRepo.findAll()) {
            System.out.println(user.getName() + " | Баланс: " + user.getBalance() + "₽");
        }
    }

    // Валидация имени
    private void validateName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("имя не может быть пустым");
        }

        if (name.length() < 3) {
            throw new IllegalArgumentException("длина имени не может быть менее 3 символов");
        }
    }
}