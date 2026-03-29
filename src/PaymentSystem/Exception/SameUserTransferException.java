package PaymentSystem.Exception;

public class SameUserTransferException extends RuntimeException {
    public SameUserTransferException(String message) {
        super(message);
    }
}
