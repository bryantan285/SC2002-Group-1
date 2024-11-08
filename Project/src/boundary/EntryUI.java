package boundary;

import boundary.user.administrator.A_Home;
import boundary.user.doctor.D_Home;
import boundary.user.patient.P_Home;
import boundary.user.pharmacist.PH_Home;
import control.user.UserController;
import interfaces.boundary.IUserInterface;
import java.util.Scanner;

public class EntryUI {
    private final UserController userController;
    private final Scanner scanner;

    public static void main(String[] args) {
        EntryUI entryUI = new EntryUI();
        entryUI.login();
    }

    public EntryUI() {
        this.userController = new UserController();
        this.scanner = new Scanner(System.in);
    }

    public void login() {
        System.out.println("██╗  ██╗███╗   ███╗███████╗");
        System.out.println("██║  ██║████╗ ████║██╔════╝");
        System.out.println("███████║██╔████╔██║███████╗");
        System.out.println("██╔══██║██║╚██╔╝██║╚════██║");
        System.out.println("██║  ██║██║ ╚═╝ ██║███████║");
        System.out.println("╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝");
        System.out.println("===========================");
        
        while (true) {
            System.out.print("Enter your User ID: ");
            String inputId = scanner.nextLine();

            System.out.print("Enter your Password: ");
            String inputPassword = scanner.nextLine();

            boolean isLoggedIn = userController.login(inputId, inputPassword);

            if (isLoggedIn) {
                System.out.println("Login successful. Welcome, " + inputId + "!");
                if (inputPassword.equals("password")) {
                    System.out.println("You are using the default password. You are required to change it before proceeding.");
                    changePassword(inputId);
                }
                break;
            } else {
                System.out.println("Invalid User ID or Password. Please try again.\n");
            }
        }
    }
    
    public void changePassword(String userId) {
        
        Scanner scanner = new Scanner(System.in);
        String newPassword;
        String confirmPassword;

        while (true) {
            System.out.print("Enter your new password: ");
            newPassword = scanner.nextLine();
    
            System.out.print("Confirm your new password: ");
            confirmPassword = scanner.nextLine();
    
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");;
            } else {
                break;
            }
        }

        userController.passwordChange(userId, newPassword);

        System.out.println("Password changed successfully.");
    }

    public void nextStep(String inputId) {
        IUserInterface nextUI;
        if (inputId.startsWith("D")) {
            nextUI = new D_Home();
        } else if (inputId.startsWith("PH")) {
            nextUI = new PH_Home();
        } else if (inputId.startsWith("A")) {
            nextUI = new A_Home();
        } else {
            nextUI = new P_Home();
        }
        nextUI.show_options();;
    }
}
