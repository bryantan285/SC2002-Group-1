package boundary.user.patient;

import control.appointment.AppointmentController;
import control.user.HospitalStaffController;
import control.user.PatientController;
import control.user.SessionManager;
import entity.appointment.Appointment;
import entity.user.Doctor;
import entity.user.Patient;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import exception.user.NoUserLoggedInException;
import interfaces.boundary.IClearConsole;
import interfaces.boundary.IKeystrokeWait;
import interfaces.boundary.IUserInterface;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import utility.InputHandler;

public class P_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();
    private final SessionManager session;

    public P_HomeUI(SessionManager session) {
        this.session = session;
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
            case 3 -> viewAvailableAppointmentSlots();
            case 4 -> scheduleAppointment();
            case 5 -> rescheduleAppointment();
            case 6 -> cancelAppointment();
            case 7 -> viewScheduledAppointments();
            case 8 -> viewPastAppointmentOutcomeRecords();
            case 9 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewMedicalRecord() {
        try {
            Patient patient = (Patient) session.getCurrentUser();
            if (patient != null) {
                System.out.println(patient.toString());
            } else {
                System.out.println("Patient record not found.");
            }
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updatePersonalInformation() {
        try {
            Patient patient = (Patient) session.getCurrentUser();
            
            while (true) {
                System.out.println("\nWhat would you like to update?");
                System.out.println("1. Update Email");
                System.out.println("2. Update Contact Number");
                System.out.println("3. Go Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine();
                IClearConsole.clearConsole();
                
                switch (choice) {
                    case "1" -> updateEmail(patient);
                    case "2" -> updateContactNumber(patient);
                    case "3" -> {
                        System.out.println("Returning to the previous menu.");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void updateEmail(Patient patient) {
        try {
            System.out.print("Enter new email: ");
            String email = scanner.nextLine();
            PatientController.changeEmail(patient, email);
            System.out.println("Email updated successfully.");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private void updateContactNumber(Patient patient) {
        try {
            System.out.print("Enter new contact number: ");
            String contactNumber = scanner.nextLine();
            PatientController.changeContactNumber(patient, contactNumber);
            System.out.println("Contact number updated successfully.");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAvailableAppointmentSlots() {
        try {
            System.out.print("Enter doctor ID to view available slots: ");
            String doctorId = scanner.nextLine();
            Doctor doc = (Doctor) HospitalStaffController.findById(doctorId);
            
            List<LocalDateTime> availableSlots = AppointmentController.getAvailableSlots(doc);
            
            if (availableSlots.isEmpty()) {
                System.out.println("No available slots for this doctor.");
                return;
            }
            
            System.out.println("Available slots for Doctor " + doctorId + ":");
            for (int i = 0; i < availableSlots.size(); i++) {
                System.out.println((i + 1) + ". " + availableSlots.get(i));
            }
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void scheduleAppointment() {
        try {
            System.out.print("Enter doctor ID to schedule an appointment: ");
            String doctorId = scanner.nextLine();
            Doctor doc = (Doctor) HospitalStaffController.findById(doctorId);
            
            List<LocalDateTime> availableSlots = AppointmentController.getAvailableSlots(doc);
            
            if (availableSlots.isEmpty()) {
                System.out.println("No available slots.");
                return;
            }
            
            System.out.println("Available slots for Doctor " + doctorId + ":");
            for (int i = 0; i < availableSlots.size(); i++) {
                System.out.println((i + 1) + ". " + availableSlots.get(i));
            }
            
            System.out.print("Choose an available slot number: ");
            int slotChoice = scanner.nextInt();
            scanner.nextLine();
            LocalDateTime selectedSlot = availableSlots.get(slotChoice - 1);
            
            System.out.println("Choose a service type:");
            Appointment.Service[] services = Appointment.Service.values();
            for (int i = 0; i < services.length; i++) {
                System.out.println((i + 1) + ". " + services[i].name());
            }
            
            System.out.print("Enter the number corresponding to the service: ");
            int serviceChoice;
            serviceChoice = Integer.parseInt(scanner.nextLine());
            if (serviceChoice < 1 || serviceChoice > services.length) {
                System.out.println("Invalid choice. Please enter a number between 1 and " + services.length);
                return;
            }
            Appointment.Service service = services[serviceChoice - 1];
            AppointmentController.scheduleAppointment(doc,(Patient) session.getCurrentUser(), service, selectedSlot);
            System.out.println("Appointment scheduled successfully.");
           
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (EntityNotFoundException | InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void rescheduleAppointment() {
        try {
            System.out.print("Enter appointment ID to reschedule: ");
            String apptId = scanner.nextLine();
            Appointment appt = AppointmentController.getAppt(apptId);
            
            System.out.print("Enter new date and time (YYYY-MM-DD HH:MM): ");
            String dateTimeInput = scanner.nextLine();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime newDateTime;
            newDateTime = LocalDateTime.parse(dateTimeInput, formatter);
            
            AppointmentController.rescheduleAppointment(appt, newDateTime);
            System.out.println("Appointment rescheduled successfully.");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cancelAppointment() {
        try {
            System.out.print("Enter appointment ID to cancel: ");
            String apptId = scanner.nextLine();
            Appointment appt = AppointmentController.getAppt(apptId);
            
            AppointmentController.cancelAppointment(appt);
            System.out.println("Appointment canceled successfully.");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewScheduledAppointments() {
        try {
            List<Appointment> list = AppointmentController.getScheduledAppointments((Patient) session.getCurrentUser());
            System.out.println("Scheduled Appointments:");
            for (Appointment appt : list) {
                System.out.println("=============================");
                System.out.println(appt.toString());
            }
            System.out.println("=============================");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPastAppointmentOutcomeRecords() {
        try {
            System.out.println("Past Appointment Outcome Records:");
            List<String> list = AppointmentController.getAppointmentOutcomes((Patient) session.getCurrentUser());
            
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
