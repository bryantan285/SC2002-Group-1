package boundary.user.pharmacist;

import control.medicine.PrescriptionController;
import control.medicine.PrescriptionItemController;
import control.user.PharmacistController;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import interfaces.boundary.IUserInterface;
import java.util.List;
import java.util.Scanner;
import utility.InputHandler;

public class PH_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();
    private final PharmacistController pharmacistController;
    private final PrescriptionController prescriptionController;
    private final PrescriptionItemController prescriptionItemController;

    public PH_HomeUI(String id) {
        this.pharmacistController = new PharmacistController();
        this.pharmacistController.setCurrentPharmacist(id);
        this.prescriptionController = new PrescriptionController();
        this.prescriptionItemController = new PrescriptionItemController();
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
        List<Prescription> activePrescriptions = pharmacistController.getActivePrescriptions();

        if (activePrescriptions.isEmpty()) {
            System.out.println("No active prescriptions.");
        } else {
            for (Prescription prescription : activePrescriptions) {
                System.out.printf("Prescription ID: %s, Appointment ID: %s, Active: %s%n",
                        prescription.getId(),
                        prescription.getApptId(),
                        prescription.getIsActive());
            }
        }
    }

    private void viewPrescriptionItems() {
        System.out.print("Enter prescription ID to view items: ");
        String prescriptionId = scanner.nextLine();
        Prescription prescription = prescriptionController.getPrescriptionById(prescriptionId);

        if (prescription == null) {
            System.out.println("Invalid prescription ID.");
            return;
        }

        List<PrescriptionItem> prescriptionItems = pharmacistController.getPrescriptionItems(prescription);
        if (prescriptionItems.isEmpty()) {
            System.out.println("No items found for this prescription.");
        } else {
            for (PrescriptionItem item : prescriptionItems) {
                System.out.printf("Item ID: %s, Medicine: %s, Quantity: %d%n",
                        item.getId(),
                        item.getMedicineId(),
                        item.getQuantity());
            }
        }
    }

    private void dispensePrescriptionItem() {
        System.out.print("Enter prescription item ID to dispense: ");
        String prescriptionItemId = scanner.nextLine();
        PrescriptionItem item = prescriptionItemController.getPrescriptionItemById(prescriptionItemId);

        if (item == null) {
            System.out.println("Invalid prescription item ID.");
            return;
        }

        boolean isDispensed = pharmacistController.dispensePrescriptionItem(item);
        if (isDispensed) {
            System.out.println("Item dispensed successfully.");
        } else {
            System.out.println("Failed to dispense item.");
        }
    }

    private void createReplenishmentRequest() {
        System.out.print("Enter medicine ID to replenish: ");
        String medicineId = scanner.nextLine();
        System.out.print("Enter quantity to replenish: ");
        int amount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String requestId = pharmacistController.createReplenishmentRequest(medicineId, amount);
        System.out.println("Replenishment request created. Request ID: " + requestId);
    }

    private void viewAllMedicines() {
        List<Medicine> medicines = pharmacistController.getAllMedicine();

        if (medicines.isEmpty()) {
            System.out.println("No medicines available.");
        } else {
            for (Medicine medicine : medicines) {
                System.out.printf("Medicine ID: %s, Name: %s, Stock: %d%n",
                        medicine.getId(),
                        medicine.getMedicineName(),
                        medicine.getStockQuantity());
            }
        }
    }
}
