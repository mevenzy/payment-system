package PaymentSystem.Entity;

public class Transaction {
    private int id;
    private String fromUser;
    private String toUser;
    private double amount;
    private PaymentMethod method;
    private TransactionType type;

    public Transaction(int id, String fromUser, String toUser, double amount, PaymentMethod method, TransactionType type) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.method = method;
        this.type = type;
    }

    public TransactionType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public double getAmount() {
        return amount;
    }

    public PaymentMethod getMethod() {
        return method;
    }
}