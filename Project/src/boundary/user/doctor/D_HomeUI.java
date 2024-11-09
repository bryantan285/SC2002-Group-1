package boundary.user.doctor;

import interfaces.boundary.IUserInterface;
import java.util.Scanner;
import utility.InputHandler;

public class D_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();

    public static void main(String[] args) {
        
    }

    @Override
    public void show_options() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.println("====================");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (choice == 8) exit = true;
            handle_option(choice);
        }

        System.out.println("You have successfully logged out. Goodbye!");
    }

    @Override
    public void handle_option(int choice) {
        switch (choice) {
            case 1 -> viewPatientMedicalRecords();
            case 2 -> updatePatientMedicalRecords();
            case 3 -> viewPersonalSchedule();
            case 4 -> setAvailabilityForAppointments();
            case 5 -> acceptOrDeclineAppointmentRequests();
            case 6 -> viewUpcomingAppointments();
            case 7 -> recordAppointmentOutcome();
            case 8 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewPatientMedicalRecords() {

    }

    private void updatePatientMedicalRecords() {

    }

    private void viewPersonalSchedule() {

    }

    private void setAvailabilityForAppointments() {

    }

    private void acceptOrDeclineAppointmentRequests() {

    }

    private void viewUpcomingAppointments() {

    }

    private void recordAppointmentOutcome() {

    }
}
