package boundary.user.administrator;

import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.notification.NotificationController;
import control.request.MedicineRequestController;
import control.user.HospitalStaffController;
import control.user.SessionManager;
import entity.appointment.Appointment;
import entity.medicine.Medicine;
import entity.request.MedicineRequest;
import entity.user.HospitalStaff;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import exception.user.NoUserLoggedInException;
import interfaces.boundary.IUserInterface;
import interfaces.observer.IObserver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import observer.NotificationObserver;
import utility.ClearConsole;
import utility.InputHandler;
import utility.KeystrokeWait;

/**
 * Represents the administrator's home UI.
 * Provides options for managing hospital staff, appointments, medication inventory, replenishment requests, and notifications.
 */
public class A_HomeUI implements IUserInterface {
    private static final Scanner scanner = InputHandler.getInstance();
    private final SessionManager session;
    private final NotificationController notificationController;
    private IObserver observer;

    /**
     * Constructs the A_HomeUI with the specified session manager.
     * 
     * @param session The session manager for the current administrator.
     */
    public A_HomeUI(SessionManager session) {
        this.session = session;
        this.notificationController = NotificationController.getInstance();
        try {
            this.observer = new NotificationObserver(session.getCurrentUser());
            notificationController.registerObserver(session.getCurrentUser().getId(), observer);
            this.observer.setNotificationHistory();
        } catch (NoUserLoggedInException e) {
            System.out.println("No user logged in");
        }
    }

    /**
     * Displays the main menu for the administrator and handles user choices.
     */
    @Override
    public void show_options() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Administrator Menu ===");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. View Notifications");
            System.out.println("6. Logout");
            System.out.println("====================");

            int choice = 0;

            while (true) {
                try {
                    System.out.print("Please select an option: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 1 && choice <= 6) break;
                    System.out.println("Enter only a number between 1 and 6.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 6.");
                    scanner.nextLine();
                }
            }

            if (choice == 6) exit = true;
            else {
                ClearConsole.clearConsole();
                handle_option(choice);
            }
        }

        System.out.println("You have successfully logged out. Goodbye!");
    }

    /**
     * Handles the option chosen by the administrator from the menu.
     * 
     * @param choice The option selected by the administrator.
     */
    @Override
    public void handle_option(int choice) {
        switch (choice) {
            case 1 -> viewAndManageHospitalStaff();
            case 2 -> viewAppointmentsDetails();
            case 3 -> viewAndManageMedicationInventory();
            case 4 -> manageReplenishmentRequests();
            case 5 -> viewNotifications();
            case 6 -> {
                System.out.println("Logging out...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    /**
     * Displays and manages options for viewing, updating, and adding hospital staff.
     */
    private void viewAndManageHospitalStaff() {
        boolean exit = false;

        while (!exit) {
            System.out.println("View and Manage Hospital Staff");
            System.out.println("====================================");
            System.out.println("1. View/update/delete Hospital Staff");
            System.out.println("2. Add Hospital Staff");
            System.out.println("3. Return to Previous Menu");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            ClearConsole.clearConsole();
            switch (choice) {
                case 1 -> viewHospitalStaff();
                case 2 -> addHospitalStaff();
                case 3 -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid choice. Please select an option from 1 to 3.");
                }
            }
        }
    }

    /**
     * Displays a list of hospital staff and allows updating or deleting staff records.
     */
    private void viewHospitalStaff() {
        List<HospitalStaff> list = HospitalStaffController.getAllStaff()
                                .stream()
                                .filter(staff -> staff.getRole() == HospitalStaff.Role.DOCTOR || staff.getRole() == HospitalStaff.Role.PHARMACIST)
                                .collect(Collectors.toCollection(ArrayList::new));

        list.sort(Comparator.comparing(HospitalStaff::getId));
        System.out.println("View Hospital Staff");

        if (list.isEmpty()) {
            System.out.println("No hospital staff found.");
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
            return;
        }

        for (HospitalStaff staff : list) {
            System.out.println("==================");
            System.out.println(staff.toString());
        }
        System.out.println("==================");

        try {
            HospitalStaff targetStaff = null;
            while (targetStaff == null) {
                System.out.print("Enter hospital staff ID to update or delete (enter nothing to go back to previous menu): ");
                String staffId = scanner.nextLine().trim();

                if (staffId.isEmpty()) {
                    ClearConsole.clearConsole();
                    return;
                }

                targetStaff = list.stream()
                    .filter(staff -> staff.getId().equals(staffId))
                    .findFirst()
                    .orElse(null);

                if (targetStaff == null) {
                    System.out.println("Error: Hospital staff not found. Please enter a valid ID.");
                }
            }

            while (true) {
                System.out.println("Selected Staff: " + targetStaff.getId());
                System.out.println("1. Update Staff");
                System.out.println("2. Delete Staff");
                System.out.println("3. Return to Previous Menu");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> {
                        ClearConsole.clearConsole();
                        updateHospitalStaff(targetStaff);
                        KeystrokeWait.waitForKeyPress();
                        ClearConsole.clearConsole();
                        return;
                    }
                    case "2" -> {
                        ClearConsole.clearConsole();
                        HospitalStaffController.removeStaff(targetStaff);
                        System.out.println("Hospital staff deleted successfully.");
                        KeystrokeWait.waitForKeyPress();
                        ClearConsole.clearConsole();
                        return;
                    }
                    case "3" -> {
                        ClearConsole.clearConsole();
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        KeystrokeWait.waitForKeyPress();
        ClearConsole.clearConsole();
    }
    /**
     * Displays all appointments and their details.
     */
    private void viewAppointmentsDetails() {
        List<Appointment> list = AppointmentController.getAllAppts().stream().collect(Collectors.toCollection(ArrayList::new));
        list.sort(Comparator.comparing(Appointment::getId));

        System.out.println("Appointments");
        for (Appointment appt : list) {
            System.out.println("==================");
            System.out.println(appt.toString());
        }
        System.out.println("==================");
        KeystrokeWait.waitForKeyPress();
        ClearConsole.clearConsole();
    }

    /**
     * Displays and manages medication inventory, allowing updates and stock changes.
     */
    private void viewAndManageMedicationInventory() {
        List<Medicine> medList = MedicineController.getAllMedicines();
        if (medList.isEmpty()) {
            System.out.println("No medicines.");
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
            return;
        }

        System.out.println("Medicine Inventory");
        for (Medicine med : medList) {
            System.out.println("==================");
            System.out.println(med.toString());
        }
        System.out.println("==================");
        System.out.println("Enter medicine ID to manage (enter nothing to go back to previous menu): ");
        try {
            String medId = scanner.nextLine();

            if (medId.isEmpty()) {
                ClearConsole.clearConsole();
                return;
            }
            Medicine med = MedicineController.getMedicineById(medId);
            ClearConsole.clearConsole();
            manageMedicationInventory(med);

        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Allows managing inventory for a specific medicine, including stock adjustments.
     * 
     * @param med The medicine to manage.
     */
    private void manageMedicationInventory(Medicine med) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Manage Medication Inventory");
            System.out.println("===========================");
            System.out.println(med.toString());
            System.out.println("===========================");
            System.out.println("1. Increase Medicine Stock");
            System.out.println("2. Decrease Medicine Stock");
            System.out.println("3. Update Medicine Stock");
            System.out.println("4. Update low stock threshold");
            System.out.println("5. Return to Previous Menu");

            int choice = 0;

            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 1 && choice <= 5) break;
                    System.out.println("Enter only a number between 1 and 5.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    scanner.nextLine();
                }
            }

            ClearConsole.clearConsole();

            switch (choice) {
                case 1 -> increaseMedicineStock(med);
                case 2 -> decreaseMedicineStock(med);
                case 3 -> updateMedicineStock(med);
                case 4 -> updateMedicineThreshold(med);
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice. Please select an option from 1 to 5.");
            }
        }
    }

    /**
     * Updates the low stock threshold for a specified medicine.
     * 
     * @param medicine The medicine whose threshold is to be updated.
     */
    private void updateMedicineThreshold(Medicine medicine) {
        try {
            int newThreshold;
            while (true) {
                System.out.print("Enter the new threshold value (0 to go back to previous menu): ");
                try {
                    newThreshold = scanner.nextInt();
                    scanner.nextLine();
                    if (newThreshold == 0) {
                        System.out.print("Going back to previous menu... ");
                        return;
                    } else if (newThreshold > 0) {
                        break;
                    } else {
                        System.out.println("Error: Invalid input. Please enter a positive number.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }

            if (MedicineController.setLowStockThreshold(medicine, newThreshold)) {
                System.out.printf("Updated threshold for medicine ID %s to %d units.%n", medicine.getId(), newThreshold);
            }
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    /**
     * Increases the stock for a specified medicine.
     * 
     * @param medicine The medicine whose stock is to be increased.
     */
    private void increaseMedicineStock(Medicine medicine) {
        try {
            int quantity;
            while (true) {
                System.out.print("Enter the quantity to increase (0 to go back to previous menu): ");
                try {
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    if (quantity == 0) {
                        System.out.print("Going back to previous menu... ");
                        return;
                    } else if (quantity > 0) {
                        break;
                    } else {
                        System.out.println("Error: Invalid input. Please enter a positive number.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }

            if (MedicineController.incMedStock(medicine, quantity)) {
                System.out.printf("Increased stock for medicine ID %s by %d units.%n", medicine.getId(), quantity);
            }
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    /**
     * Decreases the stock for a specified medicine.
     * 
     * @param medicine The medicine whose stock is to be decreased.
     */
    private void decreaseMedicineStock(Medicine medicine) {
        try {
            int quantity;
            while (true) {
                System.out.print("Enter the quantity to decrease (0 to go back to previous menu): ");
                try {
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    if (quantity == 0) {
                        System.out.print("Going back to previous menu... ");
                        return;
                    } else if (quantity > 0) {
                        break;
                    } else {
                        System.out.println("Error: Invalid input. Please enter a positive number.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }
            if (MedicineController.decMedStock(medicine, quantity)) {
                System.out.printf("Decreased stock for medicine ID %s by %d units.%n", medicine.getId(), quantity);
            }

        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    /**
     * Updates the stock for a specified medicine to a new quantity.
     * 
     * @param medicine The medicine whose stock is to be updated.
     */
    private void updateMedicineStock(Medicine medicine) {
        try {
            int newQuantity;
            while (true) {
                System.out.print("Enter the new stock quantity (-1 to go back to previous menu): ");
                try {
                    newQuantity = scanner.nextInt();
                    scanner.nextLine();
                    if (newQuantity == -1) {
                        System.out.print("Going back to previous menu... ");
                        return;
                    } else if (newQuantity >= 0) {
                        break;
                    } else {
                        System.out.println("Error: Invalid input. Please enter a number greater than or equal to 0.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }
            if (MedicineController.updateMedStock(medicine, newQuantity)) {
                System.out.printf("Updated stock for medicine ID %s to %d units.%n", medicine.getId(), newQuantity);
            }
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }
}
