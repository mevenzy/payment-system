package PaymentSystem.Entity;

public enum TransactionType {
    TRANSFER ("Перевод денежных средств"),
    DEPOSIT ("Пополнение баланса"),
    WITHDRAW ("Вывод средств");

    private final String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}