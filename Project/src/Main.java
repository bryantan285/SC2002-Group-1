import boundary.EntryUI;
import exception.user.InvalidUserTypeException;

//import User.Role;

public class Main {
    // private static ArrayList<User> users = new ArrayList<>();
    // private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        try {
            EntryUI entryUI = new EntryUI();
            entryUI.login();
        } catch (InvalidUserTypeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
