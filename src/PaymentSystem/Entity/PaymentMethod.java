package PaymentSystem.Entity;

public enum PaymentMethod {
    CARD ("Банковская карта"),
    CASH ("Наличные"),
    CRYPTO ("Криптовалюта");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}