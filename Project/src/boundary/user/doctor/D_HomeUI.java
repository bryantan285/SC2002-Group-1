package boundary.user.doctor;

import control.appointment.AppointmentController;
import control.user.PatientController;
import control.user.SessionManager;
import control.user.UnavailableDateController;
import entity.appointment.Appointment;
import entity.user.Doctor;
import entity.user.HospitalStaff;
import entity.user.Patient;
import entity.user.UnavailableDate;
import entity.user.User;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import exception.user.NoUserLoggedInException;
import interfaces.boundary.IClearConsole;
import interfaces.boundary.IKeystrokeWait;
import interfaces.boundary.IUserInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import utility.DateFormat;
import utility.InputHandler;

public class D_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();
    private final SessionManager session;

    public D_HomeUI(SessionManager session) {
        this.session = session;
    }

    @Override
    public void show_options() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Schedule");
            System.out.println("2. View Unavailable Dates");
            System.out.println("3. Add Unavailability");
            System.out.println("4. Accept/Decline Appointment Request");
            System.out.println("5. Record Appointment Outcome");
            System.out.println("6. View Patient Medical Records");
            System.out.println("7. Logout");
            System.out.println("====================");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 7) exit = true;
            IClearConsole.clearConsole();
            handle_option(choice);
        }

        System.out.println("You have successfully logged out. Goodbye!");
    }

    @Override
    public void handle_option(int choice) {
        switch (choice) {
            case 1 -> viewSchedule();
            case 2 -> viewUnavailableDates();
            case 3 -> addUnavailability();
            case 4 -> acceptDeclineApptRequest();
            case 5 -> recordApptOutcome();
            case 6 -> viewPatientMedicalRecords();
            case 7 -> System.out.println("Logging out...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewSchedule() {
        try {
            User user = session.getCurrentUser();
            List<Appointment> appointments = AppointmentController.getDoctorAppts((Doctor) user);
            
            if (appointments.isEmpty()) {
                System.out.println("No scheduled appointments.");
                return;
            }
            
            System.out.println("Schedule for Dr. " + user.getName() + ":");
            for (Appointment appt : appointments) {
                System.out.println("=============================");
                System.out.println(appt.toString());
                //     System.out.printf("ID: %s, Patient: %s, Date: %s, Service: %s%n",
                //             appt.getId(),
                //             appt.getPatientId(),
                //             DateFormat.formatWithTime(appt.getApptDateTime()),
                //             appt.getService().name());
            }
            System.out.println("=============================");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (NoUserLoggedInException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewUnavailableDates() {
        try {
            User user = session.getCurrentUser();
            List<UnavailableDate> unavailableDates = UnavailableDateController.getUnavailableDates((HospitalStaff) user);
            unavailableDates.sort(Comparator.comparing(UnavailableDate::getDate));
            LocalDateTime threshold = LocalDateTime.now().minusDays(1);
            System.out.println("You are unavailable on:");
            for (UnavailableDate date : unavailableDates) {
                if (date.getDate().isAfter(threshold)) {
                    System.out.printf("%s%n", DateFormat.formatNoTime(date.getDate()));
                }
            }
            System.out.println("=============================");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addUnavailability() {
        try {
            System.out.print("Enter date (yyyy-MM-dd): ");
            String inputDate = scanner.nextLine().trim();
            
            LocalDate date = LocalDate.parse(inputDate);
            if (date.isBefore(LocalDate.now())) {
                System.out.println("Cannot add date before today.");
                return;
            } else if (date.isEqual(LocalDate.now())) {
                System.out.println("Cannot add today.");
                return;
            }
            LocalDateTime dateTime = date.atStartOfDay();

            List<UnavailableDate> list = UnavailableDateController.getUnavailableDates((HospitalStaff) session.getCurrentUser())
                                        .stream()
                                        .filter(item -> !item.getDate().toLocalDate().isBefore(LocalDate.now()))
                                        .collect(Collectors.toList());;

            for (UnavailableDate item : list) {
                if (item.getDate().isEqual(dateTime)) {
                    System.out.println("Cannot add existing unavailable date.");
                    return;
                }
            }
            
            UnavailableDateController.addUnavailability((HospitalStaff) session.getCurrentUser(), dateTime);
            System.out.println("Unavailability added.");
        } catch (NoUserLoggedInException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid input.");
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }

    private void acceptDeclineApptRequest() {
        try {
            User user = session.getCurrentUser();
            List<Appointment> pendingAppointments = AppointmentController.getDoctorAppts((Doctor) user);
            
            System.out.println("Pending Appointments for Dr. " + user.getName() + ":");
            
            if (pendingAppointments.isEmpty()) {
                System.out.println("No pending appointments.");
                return;
            }
            
            for (Appointment appt : pendingAppointments) {
                System.out.printf("ID: %s, Patient: %s, Date: %s, Service: %s%n",
                        appt.getId(),
                        appt.getPatientId(),
                        DateFormat.formatWithTime(appt.getApptDateTime()),
                        appt.getService().name());
            }
            
            System.out.print("Enter appointment ID to accept/decline (or '0' to cancel): ");
            String appointmentId = scanner.nextLine();
            if (appointmentId.equals("0")) {
                System.out.println("Returning to main menu.");
                return;
            }
            
            Appointment selectedAppt = AppointmentController.getAppt(appointmentId);
            
            if (selectedAppt == null) {
                System.out.println("Invalid appointment ID.");
                return;
            }
            
            System.out.print("Enter 1 to accept or 0 to decline: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            boolean isAccepted = (choice == 1);
            AppointmentController.apptRequestDecision(selectedAppt, isAccepted);
            System.out.println(isAccepted ? "Appointment accepted." : "Appointment declined.");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | NoUserLoggedInException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void recordApptOutcome() {
        try {
            System.out.print("Enter appointment ID: ");
            String appointmentId = scanner.nextLine();
            Appointment appt = AppointmentController.getAppt(appointmentId);
            
            if (appt == null) {
                System.out.println("Invalid appointment ID.");
                return;
            }
            
            System.out.print("Enter appointment outcome: ");
            String outcome = scanner.nextLine();
            AppointmentController.completeAppointment(appt, outcome);
            System.out.println("Outcome recorded.");
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPatientMedicalRecords() {
        try {
            System.out.print("Enter patient ID to view medical records: ");
            String patientId = scanner.nextLine();
            Patient patient = PatientController.getById(patientId);
            
            if (patient == null) {
                System.out.println("Invalid patient ID.");
                return;
            }
            
            System.out.println("Medical Record for " + patient.getName() + ":");
            System.out.println(patient.toString());
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
