package account;

/**
 *
 * @author Gavin
 */
public class InsufficientFundException extends RuntimeException{
    public InsufficientFundException() {}

    public InsufficientFundException(String message) {
        super(message);
    }

    public InsufficientFundException(Throwable cause) {
        super(cause);
    }

    public InsufficientFundException(String message, Throwable cause) {
        super(message, cause);
    }
}
