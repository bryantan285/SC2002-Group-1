package exception.user;

public class InvalidDurationException extends Exception {
    public InvalidDurationException() {
        super("Invalid duration: Start time must be before end time.");
    }
}
