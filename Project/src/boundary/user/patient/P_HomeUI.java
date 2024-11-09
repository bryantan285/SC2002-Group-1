package boundary.user.patient;

import interfaces.boundary.IUserInterface;
import java.util.Scanner;
import utility.InputHandler;

public class P_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();

    public static void main(String[] args) {
        
    }

    @Override
    public void show_options() {
            boolean exit = false;
    
            while (!exit) {
                System.out.println("\n=== Patient Menu ===");
                System.out.println("1. View Medical Record");
                System.out.println("2. Update Personal Information");
                System.out.println("3. View Available Appointment Slots");
                System.out.println("4. Schedule an Appointment");
                System.out.println("5. Reschedule an Appointment");
                System.out.println("6. Cancel an Appointment");
                System.out.println("7. View Scheduled Appointments");
                System.out.println("8. View Past Appointment Outcome Records");
                System.out.println("9. Logout");
                System.out.println("====================");
                System.out.print("Please select an option: ");

    
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 9) exit = true;
                handle_option(choice);
            }
    
            System.out.println("You have successfully logged out. Goodbye!");
        }

    @Override
    public void handle_option(int choice) {
        switch (choice) {
            case 1 -> viewMedicalRecord();
            case 2 -> updatePersonalInformation();
            case 3 ->viewAvailableAppointmentSlots();
            case 4 -> scheduleAppointment();
            case 5 -> rescheduleAppointment();
            case 6 -> cancelAppointment();
            case 7 -> viewScheduledAppointments();
            case 8 -> viewPastAppointmentOutcomeRecords();
            case 9 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public void viewMedicalRecord() {

    }

    public void updatePersonalInformation() {

    }

    public void viewAvailableAppointmentSlots() {

    }

    public void scheduleAppointment() {

    }

    public void rescheduleAppointment() {

    }

    public void cancelAppointment() {

    }

    public void viewScheduledAppointments() {

    }

    public void viewPastAppointmentOutcomeRecords() {

    }
}
