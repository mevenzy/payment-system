package PaymentSystem.Repository;

import PaymentSystem.Entity.*;

import java.util.*;

public class TransactionRepository {
    List<Transaction> transactions = new ArrayList<>();

    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> findAll() {
        return transactions;
    }
}