package boundary.user.pharmacist;

import control.medicine.MedicineController;
import control.prescription.PrescriptionController;
import control.prescription.PrescriptionItemController;
import control.request.MedicineRequestController;
import control.user.SessionManager;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import entity.user.HospitalStaff;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import exception.user.NoUserLoggedInException;
import interfaces.boundary.IClearConsole;
import interfaces.boundary.IKeystrokeWait;
import interfaces.boundary.IUserInterface;
import java.util.List;
import java.util.Scanner;
import utility.InputHandler;

public class PH_HomeUI implements IUserInterface {
    private final SessionManager session;
    private final Scanner scanner = InputHandler.getInstance();

    public PH_HomeUI(SessionManager session) {
        this.session = session;
    }

    @Override
    public void show_options() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Pharmacist Menu ===");
            System.out.println("1. View Active Prescriptions");
            System.out.println("2. View Prescription Items");
            System.out.println("3. Dispense Prescription Item");
            System.out.println("4. Create Replenishment Request");
            System.out.println("5. View All Medicines");
            System.out.println("6. Logout");
            System.out.println("====================");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 6) exit = true;
            IClearConsole.clearConsole();
            handle_option(choice);
        }

        System.out.println("You have successfully logged out. Goodbye!");
    }

    @Override
    public void handle_option(int choice) {
        switch (choice) {
            case 1 -> viewActivePrescriptions();
            case 2 -> viewPrescriptionItems();
            case 3 -> dispensePrescriptionItem();
            case 4 -> createReplenishmentRequest();
            case 5 -> viewAllMedicines();
            case 6 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewActivePrescriptions() {
        List<Prescription> activePrescriptions = PrescriptionController.getActivePrescriptions();

        if (activePrescriptions.isEmpty()) {
            System.out.println("No active prescriptions.");
        } else {
            System.out.println("Active prescriptions");
            for (Prescription prescription : activePrescriptions) {
                System.out.println("=============================");
                // System.out.printf("Prescription ID: %s, Appointment ID: %s, Active: %s%n",
                //         prescription.getId(),
                //         prescription.getApptId(),
                //         prescription.getIsActive());
                System.out.println(prescription.toString());
            }
            System.out.println("=============================");
        }
        IKeystrokeWait.waitForKeyPress();
        IClearConsole.clearConsole();
    }

    private void viewPrescriptionItems() {
        try {
            System.out.print("Enter prescription ID to view items: ");
            String prescriptionId = scanner.nextLine();
            Prescription prescription = PrescriptionController.getPrescriptionById(prescriptionId);
            
            if (prescription == null) {
                System.out.println("Invalid prescription ID.");
                return;
            }
            
            List<PrescriptionItem> prescriptionItems = PrescriptionItemController.getPrescriptionItems(prescription);
            if (prescriptionItems.isEmpty()) {
                System.out.println("No items found for this prescription.");
            } else {
                System.out.println("Prescription Items");
                for (PrescriptionItem item : prescriptionItems) {
                    System.out.println("=============================");
                    System.out.println(item.toString());
                    // System.out.printf("Item ID: %s, Medicine: %s, Quantity: %d%n",
                    //         item.getId(),
                    //         item.getMedicineId(),
                    //         item.getQuantity());
                }
                System.out.println("=============================");
            }
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }

    private void dispensePrescriptionItem() {
        try {
            System.out.print("Enter prescription item ID to dispense: ");
            String prescriptionItemId = scanner.nextLine();
            
            PrescriptionItem item = PrescriptionItemController.getPrescriptionItemById(prescriptionItemId);
            
            
            if (item == null) {
                System.out.println("Invalid prescription item ID.");
                return;
            }
            
            boolean isDispensed = PrescriptionItemController.dispensePrescriptionItem(item);
            
            if (isDispensed) {
                System.out.println("Item dispensed successfully.");
            } else {
                System.out.println("Failed to dispense item.");
            }
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }

    private void createReplenishmentRequest() {
        try {
            System.out.print("Enter medicine ID to replenish: ");
            String medicineId = scanner.nextLine();
            System.out.print("Enter quantity to replenish: ");
            int amount = scanner.nextInt();
            scanner.nextLine();
            
            String requestId = MedicineRequestController.createReplenishmentRequest((HospitalStaff) session.getCurrentUser(), medicineId, amount);
            System.out.println("Replenishment request created. Request ID: " + requestId);
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }

    private void viewAllMedicines() {
        List<Medicine> medicines = MedicineController.getAllMedicines();

        if (medicines.isEmpty()) {
            System.out.println("No medicines available.");
        } else {
            System.out.println("Medicines");
            for (Medicine medicine : medicines) {
                System.out.println("=============================");
                System.out.println(medicine.toString());
            //     System.out.printf("Medicine ID: %s, Name: %s, Stock: %d%n",
            //             medicine.getId(),
            //             medicine.getMedicineName(),
            //             medicine.getStockQuantity());
            }
            System.out.println("=============================");
        }
        IKeystrokeWait.waitForKeyPress();
        IClearConsole.clearConsole();
    }
}
