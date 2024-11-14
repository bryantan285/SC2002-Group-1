package exception.user;

public class InvalidUserTypeException extends Exception {
    public InvalidUserTypeException() {
        super("Invalid user type.");
    }
}