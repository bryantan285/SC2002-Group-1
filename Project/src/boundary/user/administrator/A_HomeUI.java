package boundary.user.administrator;

import interfaces.boundary.IUserInterface;
import java.util.Scanner;
import utility.InputHandler;

public class A_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();

    public static void main(String[] args) {
        
    }

    @Override
    public void show_options() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.println("====================");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 5) exit = true;
            handle_option(choice);
        }

        System.out.println("You have successfully logged out. Goodbye!");
    }

    @Override
    public void handle_option(int choice) {
        switch (choice) {
            case 1 -> viewAndManageHospitalStaff();
            case 2 -> viewAppointmentsDetails();
            case 3 -> viewAndManageMedicationInventory();
            case 4 -> approveReplenishmentRequests();
            case 5 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewAndManageHospitalStaff() {
    }

    private void viewAppointmentsDetails() {
    }

    private void viewAndManageMedicationInventory() {
    }

    private void approveReplenishmentRequests() {
    }
}
