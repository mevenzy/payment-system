package PaymentSystem.Repository;

import PaymentSystem.Entity.*;

import java.util.*;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findByName(String name) {

        for (User user : users) {
            if (user.getName().equals(name)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}