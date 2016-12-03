public class AlarmLockedException extends RuntimeException {
    public AlarmLockedException() {
        super();
    }

    public AlarmLockedException(String message) {
        super(message);
    }

    public AlarmLockedException(Throwable cause) {
        super(cause);
    }

    public AlarmLockedException(String message, Throwable cause) {
        super(message, cause);
    }

}
