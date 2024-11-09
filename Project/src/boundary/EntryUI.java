package boundary;

import boundary.user.administrator.A_HomeUI;
import boundary.user.doctor.D_HomeUI;
import boundary.user.patient.P_HomeUI;
import boundary.user.pharmacist.PH_HomeUI;
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
        
        String inputId;
        String inputPassword;

        while (true) {
            System.out.print("Enter your User ID: ");
            inputId = scanner.nextLine();

            System.out.print("Enter your Password: ");
            inputPassword = scanner.nextLine();

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
        nextStep(inputId);
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
            nextUI = new D_HomeUI();
        } else if (inputId.startsWith("PH")) {
            nextUI = new PH_HomeUI();
        } else if (inputId.startsWith("A")) {
            nextUI = new A_HomeUI();
        } else {
            nextUI = new P_HomeUI();
        }
        nextUI.show_options();;
    }
}
