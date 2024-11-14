package boundary.user.administrator;

import control.medicine.MedicineController;
import control.request.MedicineRequestController;
import control.user.SessionManager;
import entity.medicine.Medicine;
import entity.request.MedicineRequest;
import entity.user.HospitalStaff;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import exception.user.NoUserLoggedInException;
import interfaces.boundary.IUserInterface;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import utility.InputHandler;

public class A_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();
    private final SessionManager session;

    public A_HomeUI(SessionManager session) {
        this.session = session;
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
            case 4 -> manageReplenishmentRequests();
            case 5 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewAndManageHospitalStaff() {
    }

    private void viewAppointmentsDetails() {
    }

    private void viewAndManageMedicationInventory() {
        boolean exit = false;
        while (!exit) {
            System.out.println("View and Manage Medication Inventory");
            System.out.println("====================================");
            System.out.println("1. View Inventory");
            System.out.println("2. Manage Inventory");
            System.out.println("3. Return to Previous Menu");
    
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1 -> viewMedicationInventory();
                case 2 ->  manageMedicationInventory();
                case 3 -> {
                    System.out.println("Returning to the previous menu.");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice. Please select an option from 1 to 3.");
                }
            }
        }
    }
    
    private void viewMedicationInventory() {
        List<Medicine> medList = MedicineController.getAllMedicines();
        System.out.println("Medicine Inventory");
        System.out.println("==================");
        for (Medicine med : medList) {
            System.out.println(med.toString());
        }
        System.out.println();
    }

    private void manageMedicationInventory() {
        boolean exit = false;
    
        while (!exit) {
            System.out.println("Manage Medication Inventory");
            System.out.println("===========================");
            System.out.println("1. Increase Medicine Stock");
            System.out.println("2. Decrease Medicine Stock");
            System.out.println("3. Update Medicine Stock");
            System.out.println("4. Return to Previous Menu");
    
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1 -> increaseMedicineStock();
                case 2 -> decreaseMedicineStock();
                case 3 -> updateMedicineStock();
                case 4 -> {
                    System.out.println("Returning to the previous menu.");
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Please select an option from 1 to 4.");
            }
        }
    }

    private void increaseMedicineStock() {
        System.out.print("Enter the medicine ID: ");
        String medicineId = scanner.nextLine();

        if (medicineId.isEmpty()) {
            System.out.println("Error: Medicine ID cannot be empty.");
            return;
        }

        System.out.print("Enter the quantity to increase: ");
        int quantity;
        try {
            quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity <= 0) {
                System.out.println("Error: Quantity must be a positive number.");
                return;
            }
            Medicine medicine = MedicineController.getMedicineById(medicineId);
            if (MedicineController.incMedStock(medicine, quantity)) {
                System.out.printf("Increased stock for medicine ID %s by %d units.%n", medicineId, quantity);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter a valid number.");
            scanner.nextLine();
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void decreaseMedicineStock() {
        System.out.print("Enter the medicine ID: ");
        String medicineId = scanner.nextLine();

        if (medicineId.isEmpty()) {
            System.out.println("Error: Medicine ID cannot be empty.");
            return;
        }

        System.out.print("Enter the quantity to decrease: ");
        int quantity;
        try {
            quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity <= 0) {
                System.out.println("Error: Quantity must be a positive number.");
                return;
            }
            Medicine medicine = MedicineController.getMedicineById(medicineId);
            if (MedicineController.decMedStock(medicine, quantity)) {
                System.out.printf("Decreased stock for medicine ID %s by %d units.%n", medicineId, quantity);
            }

        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateMedicineStock() {
        System.out.print("Enter the medicine ID: ");
        String medicineId = scanner.nextLine();

        if (medicineId.isEmpty()) {
            System.out.println("Error: Medicine ID cannot be empty.");
            return;
        }

        System.out.print("Enter the new stock quantity: ");
        int newQuantity;
        try {
            newQuantity = scanner.nextInt();
            scanner.nextLine();

            if (newQuantity < 0) {
                System.out.println("Error: Stock quantity cannot be negative.");
                return;
            }
            Medicine medicine = MedicineController.getMedicineById(medicineId);
            if (MedicineController.updateMedStock(medicine, newQuantity)) {
                System.out.printf("Updated stock for medicine ID %s to %d units.%n", medicineId, newQuantity);
            }
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    private void manageReplenishmentRequests() {
        try {
            List<MedicineRequest> reqList = MedicineRequestController.getPendingRequests();
            
            if (reqList.isEmpty()) {
                System.out.println("No pending replenishment requests.");
                return;
            }
            
            System.out.println("Pending Replenishment Requests:");
            for (MedicineRequest req : reqList) {
                System.out.println("=============================");
                Medicine med = MedicineController.getMedicineById(req.getMedicineId());
                System.out.println("Request ID: " + req.getId());
                System.out.println("Medicine ID: " + req.getMedicineId());
                System.out.println("Medicine Name: " + med.getMedicineName());
                System.out.println("Requestor ID: " + req.getRequestorId());
                System.out.println("Current Quantity: " + med.getStockQuantity());
                System.out.println("Requested Quantity: " + req.getQuantity());
            }
            System.out.println("=============================");
            MedicineRequest selectedRequest = null;
            while (selectedRequest == null) {
                System.out.print("Enter a valid request ID to process: ");
                String choice = scanner.nextLine();
                
                for (MedicineRequest req : reqList) {
                    if (req.getId().equals(choice)) {
                        selectedRequest = req;
                        break;
                    }
                }
                
                if (selectedRequest == null) {
                    System.out.println("Invalid request ID. Please try again.");
                }
            }
            Medicine selectedMedicine = MedicineController.getMedicineById(selectedRequest.getMedicineId());
            boolean actionExit = false;
            while (!actionExit) {
                System.out.println("Processing Replenishment Request:");
                System.out.println("=============================");
                System.out.println("Request ID: " + selectedRequest.getId());
                System.out.println("Medicine ID: " + selectedRequest.getMedicineId());
                System.out.println("Requestor ID: " + selectedRequest.getRequestorId());
                System.out.println("Medicine Name: " + selectedMedicine.getMedicineName());
                System.out.println("Current Quantity: " + selectedMedicine.getStockQuantity());
                System.out.println("Requested Quantity: " + selectedRequest.getQuantity());
                System.out.println("=============================");
                System.out.println("1. Approve request");
                System.out.println("2. Reject request");
                System.out.println("3. Go back to the previous menu");
                System.out.print("Enter your choice: ");
                
                int actionChoice = scanner.nextInt();
                scanner.nextLine();
                
                switch (actionChoice) {
                    case 1 -> {
                        MedicineRequestController.approveReplenishmentRequest((HospitalStaff) session.getCurrentUser(), selectedRequest);
                        System.out.println("Request approved. Stock has been updated.");
                        actionExit = true;
                    }
                    case 2 -> {
                        MedicineRequestController.rejectReplenishmentRequest((HospitalStaff) session.getCurrentUser(), selectedRequest);
                        System.out.println("Request rejected.");
                        actionExit = true;
                    }
                    case 3 -> {
                        System.out.println("Returning to the previous menu.");
                        actionExit = true;
                    }
                    default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }
        } catch (InvalidInputException | EntityNotFoundException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    
    }
}
