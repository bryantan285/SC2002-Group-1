package boundary.user.doctor;

import control.appointment.AppointmentController;
import control.user.DoctorController;
import entity.appointment.Appointment;
import entity.user.Doctor;
import entity.user.Patient;
import interfaces.boundary.IUserInterface;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import utility.DateFormat;

public class D_HomeUI implements IUserInterface {
    private final Scanner scanner;
    private final DoctorController doctorController;
    private final AppointmentController appointmentController;

    public D_HomeUI(String id) {
        this.scanner = new Scanner(System.in);
        this.doctorController = new DoctorController();
        this.appointmentController = new AppointmentController();
        this.doctorController.setCurrentDoctor(id);
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
            scanner.nextLine(); // Consume newline
            if (choice == 7) exit = true;
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
        doctorController.viewSchedule();
    }

    private void viewUnavailableDates() {
        doctorController.viewUnavailableDates();
    }

    private void addUnavailability() {
        System.out.print("Enter start date and time (YYYY-MM-DD HH:MM): ");
        String startDateTimeStr = scanner.nextLine();
        System.out.print("Enter end date and time (YYYY-MM-DD HH:MM): ");
        String endDateTimeStr = scanner.nextLine();

        // Parse the input strings to LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr + ":00");
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr + ":00");

        doctorController.addUnavailability(startDateTime, endDateTime);
        System.out.println("Unavailability added.");
    }

    private void acceptDeclineApptRequest() {
        Doctor doctor = doctorController.getCurrentDoctor();
        List<Appointment> pendingAppointments = appointmentController.getDoctorAppts(doctor.getId());

        System.out.println("Pending Appointments for Dr. " + doctor.getName() + ":");

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

        // Allow the doctor to choose an appointment to accept or decline
        System.out.print("Enter appointment ID to accept/decline (or '0' to cancel): ");
        String appointmentId = scanner.nextLine();
        if (appointmentId.equals("0")) {
            System.out.println("Returning to main menu.");
            return;
        }

        Appointment selectedAppt = appointmentController.getAppt(appointmentId);

        if (selectedAppt == null) {
            System.out.println("Invalid appointment ID.");
            return;
        }

        System.out.print("Enter 1 to accept or 0 to decline: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        boolean isAccepted = (choice == 1);
        doctorController.acceptDeclineApptRequest(selectedAppt, isAccepted);
        System.out.println(isAccepted ? "Appointment accepted." : "Appointment declined.");
    }

    private void recordApptOutcome() {
        System.out.print("Enter appointment ID: ");
        String appointmentId = scanner.nextLine();
        Appointment appt = appointmentController.getAppt(appointmentId);

        if (appt == null) {
            System.out.println("Invalid appointment ID.");
            return;
        }

        System.out.print("Enter appointment outcome: ");
        String outcome = scanner.nextLine();
        doctorController.recordApptOutcome(appt, outcome);
        System.out.println("Outcome recorded.");
    }

    private void viewPatientMedicalRecords() {
        System.out.print("Enter patient ID to view medical records: ");
        String patientId = scanner.nextLine();
        Patient patient = doctorController.getPatient(patientId);

        if (patient == null) {
            System.out.println("Invalid patient ID.");
            return;
        }

        System.out.println("Medical Record for " + patient.getName() + ":");
        // Display medical records (You can expand this with details from patient object)
        // System.out.println("Diagnosis: " + patient.getMedicalRecord().getDiagnosis());
        // System.out.println("Prescription: " + patient.getMedicalRecord().getPrescription());
        // System.out.println("Treatment: " + patient.getMedicalRecord().getTreatment());
    }
}
