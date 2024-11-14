package exception.user;

public class NoUserLoggedInException extends Exception {
    public NoUserLoggedInException() {
        super("No user is currently logged in.");
    }
}