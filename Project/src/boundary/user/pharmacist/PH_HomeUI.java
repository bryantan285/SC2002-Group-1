package boundary.user.pharmacist;

import interfaces.boundary.IUserInterface;
import java.util.Scanner;

import control.user.PharmacistController;
import utility.InputHandler;

public class PH_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();
    private final PharmacistController pharmacistController;

    public PH_HomeUI(String id) {
        this.pharmacistController = new PharmacistController();
        this.pharmacistController.setCurrentPharmacist(id);
    }

    public static void main(String[] args) {
        
    }

    @Override
    public void show_options() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
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
            case 1 -> viewAppointmentOutcomeRecord();
            case 2 -> updatePrescriptionStatus();
            case 3 -> viewMedicationInventory();
            case 4 -> submitReplenishmentRequest();
            case 5 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewAppointmentOutcomeRecord() {
    }

    private void updatePrescriptionStatus() {
    }

    private void viewMedicationInventory() {
    }

    private void submitReplenishmentRequest() {
    }
}
