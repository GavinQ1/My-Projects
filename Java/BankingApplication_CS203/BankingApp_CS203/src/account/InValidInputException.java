package account;

/**
 *
 * @author Gavin
 */
public class InValidInputException extends RuntimeException {
    public InValidInputException() {}

    public InValidInputException(String message) {
        super(message);
    }

    public InValidInputException(Throwable cause) {
        super(cause);
    }

    public InValidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
