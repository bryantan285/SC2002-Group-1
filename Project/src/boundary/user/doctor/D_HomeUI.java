package boundary.user.doctor;

import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.prescription.PrescriptionController;
import control.prescription.PrescriptionItemController;
import control.user.PatientController;
import control.user.SessionManager;
import control.user.UnavailableDateController;
import entity.appointment.Appointment;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
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
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept/Decline Appointment Request");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
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
            case 1 -> viewPatientMedicalRecords();
            case 2 -> updatePatientMedicalRecord();
            case 3 -> viewSchedule();
            case 4 -> setAvailability();
            case 5 -> acceptDeclineApptRequest();
            case 6 -> viewUpcomingAppointments();
            case 7 -> recordApptOutcome();
            case 8 -> {
                System.out.println("Logging out...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    // private void viewSchedule() {
    //     try {
    //         User user = session.getCurrentUser();
    //         Doctor doctor = (Doctor) user;
    
    //         LocalDate today = LocalDate.now();
    //         LocalDate endDate = today.plusWeeks(1); // Adjust range as needed
            
    //         System.out.println("\n===============================================");
    //         System.out.println("Schedule for Dr. " + doctor.getName() + " (Next 7 days):");
    //         System.out.println("=================================================");
    
    //         // Iterate over each day in the range
    //         for (LocalDate date = today; date.isBefore(endDate); date = date.plusDays(1)) {
    //             System.out.println(date + ":");
    
    //             // Fetch appointments for the day
    //             List<Appointment> dailyAppointments = AppointmentController.getApptByDate(doctor, date);
    
    //             if (dailyAppointments.isEmpty()) {
    //                 System.out.println("  No appointments.");
    //             } else {
    //                 for (Appointment appt : dailyAppointments) {
    //                     System.out.printf("  %s - Patient: %s, Service: %s%n",
    //                             DateFormat.formatWithTime(appt.getApptDateTime()),
    //                             appt.getPatientId(),
    //                             appt.getService().name());
    //                 }
    //             }
    
    //             // Fetch unavailable slots for the day
    //             List<UnavailableDate> unavailableSlots = UnavailableDateController.getUnavailableDatesByDate(doctor, date);
    //             if (!unavailableSlots.isEmpty()) {
    //                 System.out.println("  Unavailable Slots:");
    //                 for (UnavailableDate slot : unavailableSlots) {
    //                     System.out.printf("    %s%n", DateFormat.formatWithTime(slot.getDate()));
    //                 }
    //             }
    //             System.out.println("-------------------------------------------------");
    //         }
    //     } catch (NoUserLoggedInException | InvalidInputException e) {
    //         System.out.println("Error: " + e.getMessage());
    //     } finally {
    //         IKeystrokeWait.waitForKeyPress();
    //         IClearConsole.clearConsole();
    //     }
    // }

    private void updatePatientMedicalRecord() {
        
    }

    private void viewSchedule() {
        try {
            User user = session.getCurrentUser();
            Doctor doctor = (Doctor) user;
            
            Scanner scanner = new Scanner(System.in);

            // Get the start date from the user
            System.out.println("Enter the start date (YYYY-MM-DD): ");
            String startDateInput = scanner.nextLine();
            LocalDate startDate = LocalDate.parse(startDateInput);
            
            // Get the end date from the user
            System.out.println("Enter the end date (YYYY-MM-DD): ");
            String endDateInput = scanner.nextLine();
            LocalDate endDate = LocalDate.parse(endDateInput);

            System.out.println("\n===============================================");
            System.out.println("Schedule for Dr. " + doctor.getName() + " (" + startDate + " to " + endDate + "):");
            System.out.println("=================================================");

            // Iterate over each day in the range
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                System.out.println(date + ":");

                // Fetch appointments for the day
                List<Appointment> dailyAppointments = AppointmentController.getApptByDate(doctor, date);

                if (dailyAppointments.isEmpty()) {
                    System.out.println("  No appointments.");
                } else {
                    for (Appointment appt : dailyAppointments) {
                        System.out.printf("  %s - Patient: %s, Service: %s%n",
                                DateFormat.formatWithTime(appt.getApptDateTime()),
                                appt.getPatientId(),
                                appt.getService().name());
                    }
                }

                // Fetch unavailable slots for the day
                List<UnavailableDate> unavailableSlots = UnavailableDateController.getUnavailableDatesByDate(doctor, date);
                if (!unavailableSlots.isEmpty()) {
                    System.out.println("  Unavailable Slots:");
                    for (UnavailableDate slot : unavailableSlots) {
                        System.out.printf("    %s%n", DateFormat.formatWithTime(slot.getDate()));
                    }
                }
                System.out.println("-------------------------------------------------");
            }
        } catch (NoUserLoggedInException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid date format. Please try again with the correct format (YYYY-MM-DD).");
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }
    
    private void viewUnavailableDates() {
        try {
            User user = session.getCurrentUser();
            
            List<LocalDateTime> availableSlots = AppointmentController.getAvailableSlots((Doctor) user);
            
            if (availableSlots.isEmpty()) {
                System.out.println("No available slots for this doctor.");
                return;
            }
            
            System.out.println("Doctor " + user.getId() + " is available on:");
            for (int i = 0; i < availableSlots.size(); i++) {
                System.out.println((i + 1) + ". " + availableSlots.get(i));
            }
            
            List<UnavailableDate> unavailableDates = UnavailableDateController.getUnavailableDates((HospitalStaff) user);
            unavailableDates.sort(Comparator.comparing(UnavailableDate::getDate));
            LocalDateTime threshold = LocalDateTime.now().minusDays(1);
            System.out.println("You are unavailable on:");
            for (UnavailableDate date : unavailableDates) {
                if (date.getDate().isAfter(threshold)) {
                    System.out.printf("%s%n", DateFormat.formatWithTime(date.getDate()));
                }
            }
            System.out.println("=============================");
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }

    private void setAvailability() {
        try {
            User user = session.getCurrentUser();
            Doctor doctor = (Doctor) user;
    
            System.out.println("Default working hours: 9 AM - 5 PM (Break: 12 PM - 2 PM)");
            System.out.print("Enter start date for availability (yyyy-MM-dd): ");
            String startDateInput = scanner.nextLine().trim();
            System.out.print("Enter end date for availability (yyyy-MM-dd): ");
            String endDateInput = scanner.nextLine().trim();
    
            LocalDate startDate = LocalDate.parse(startDateInput);
            LocalDate endDate = LocalDate.parse(endDateInput);
    
            if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
                System.out.println("Invalid date range.");
                return;
            }
    
            // Iterate through selected date range
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                System.out.println("\nSetting availability for: " + date);
    
                // Show available time slots
                List<LocalDateTime> defaultSlots = getDefaultTimeSlots(date);
                for (int i = 0; i < defaultSlots.size(); i++) {
                    LocalDateTime slot = defaultSlots.get(i);
                    System.out.printf("%d. %s%n", (i + 1), DateFormat.formatWithTime(slot));
                }
    
                System.out.println("Options:");
            System.out.println("1. Mark specific slots as unavailable");
            System.out.println("2. Keep all slots available for this date");

            System.out.print("Enter your choice (1 or 2): ");
            String choice = scanner.nextLine().trim();

            if (null == choice) {
                System.out.println("Invalid choice. Skipping to the next date.");
            } else switch (choice) {
                    case "2" -> {
                        System.out.println("All slots for " + date + " remain available.");
                        continue; // Skip to the next date
                    }
                    case "1" -> {
                        System.out.print("Enter slot numbers to mark as unavailable (comma-separated): ");
                        String[] inputs = scanner.nextLine().split(",");
                        for (String input : inputs) {
                            try {
                                int index = Integer.parseInt(input.trim()) - 1;
                                if (index >= 0 && index < defaultSlots.size()) {
                                    UnavailableDateController.addUnavailability(doctor, defaultSlots.get(index));
                                    System.out.println("Slot marked as unavailable: " + DateFormat.formatWithTime(defaultSlots.get(index)));
                                } else {
                                    System.out.println("Invalid slot number: " + input);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input: " + input);
                            }
                        }
                    }
                    default -> System.out.println("Invalid choice. Skipping to the next date.");
                }
        }
    
            System.out.println("Availability updated.");
        } catch (NoUserLoggedInException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }
    
    private List<LocalDateTime> getDefaultTimeSlots(LocalDate date) {
        List<LocalDateTime> slots = List.of(
                date.atTime(9, 0),
                date.atTime(10, 0),
                date.atTime(11, 0),
                date.atTime(14, 0),
                date.atTime(15, 0),
                date.atTime(16, 0)
        );
        return slots;
    }

    private void acceptDeclineApptRequest() {
        try {
            User user = session.getCurrentUser();
            List<Appointment> pendingAppointments = AppointmentController.getPendingAppts((Doctor) user);
    
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
            scanner.nextLine();  // Consume the newline
            switch (choice) {
                case 1 -> {
                    AppointmentController.apptRequestDecision(selectedAppt, true);
                    System.out.println("Appointment accepted.");
                }
                case 0 -> {
                    AppointmentController.apptRequestDecision(selectedAppt, false);
                    System.out.println("Appointment declined.");
                }
                default -> System.out.println("Invalid choice. Returning to main menu.");
            }
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
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
    
            // Get appointment outcome details
            System.out.print("Enter diagnosis: ");
            String diagnosis = scanner.nextLine();
    
            System.out.print("Enter consultation notes: ");
            String consultationNotes = scanner.nextLine();
    
            // Fetch available medicines from MedicineController
            List<Medicine> availableMedicines = MedicineController.getAllMedicines();
            if (availableMedicines.isEmpty()) {
                System.out.println("No medicines available in the system.");
                return;
            }
    
            // Display the list of available medicines
            System.out.println("Available Medicines:");
            for (int i = 0; i < availableMedicines.size(); i++) {
                Medicine medicine = availableMedicines.get(i);
                System.out.println((i + 1) + ". " + medicine.getMedicineName() + " (Stock: " + medicine.getStockQuantity() + ")");
            }
    
            // Create prescriptions and prescription items
            List<Prescription> prescriptions = new ArrayList<>();
            System.out.print("Enter the numbers of the medicines to prescribe (comma separated): ");
            String[] selectedMedicineNumbers = scanner.nextLine().split(",");
    
            for (String num : selectedMedicineNumbers) {
                int medicineIndex = Integer.parseInt(num.trim()) - 1;
    
                if (medicineIndex >= 0 && medicineIndex < availableMedicines.size()) {
                    Medicine selectedMedicine = availableMedicines.get(medicineIndex);
    
                    System.out.println("Selected medicine: " + selectedMedicine.getMedicineName());
    
                    // Get quantity for the selected medicine
                    System.out.print("Enter quantity for " + selectedMedicine.getMedicineName() + ": ");
                    int quantity = Integer.parseInt(scanner.nextLine());
    
                    if (quantity > selectedMedicine.getStockQuantity()) {
                        System.out.println("Insufficient stock for " + selectedMedicine.getMedicineName() + ".");
                        continue;
                    }
    
                    // Get additional notes for the prescription item
                    System.out.print("Enter notes for " + selectedMedicine.getMedicineName() + ": ");
                    String notes = scanner.nextLine();
    
                    // Create the prescription using the controller
                    PrescriptionController.createPrescription(appointmentId, true);
                    Prescription prescription = PrescriptionController.getActivePrescriptions()
                            .stream()
                            .filter(p -> p.getApptId().equals(appointmentId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Prescription", "for appointment " + appointmentId));
    
                    prescriptions.add(prescription);
    
                    // Create the prescription item
                    PrescriptionItemController.createPrescriptionItem(prescription.getId(), selectedMedicine.getId(), quantity, notes);
    
                    System.out.println("Prescription created for " + selectedMedicine.getMedicineName());
                } else {
                    System.out.println("Invalid selection: " + num.trim());
                }
            }
    
            // Record the appointment outcome
            String outcome = "Outcome recorded: Diagnosis: " + diagnosis;
            AppointmentController.completeAppointment(appt, diagnosis, consultationNotes, prescriptions, outcome);
    
            System.out.println("Outcome recorded successfully.");
        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
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
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }

    private void viewUpcomingAppointments() {
        try {
            User user = session.getCurrentUser();

            List<Appointment> upcomingAppointments = AppointmentController.getDoctorAppts((Doctor) user);
    
            // Display the appointments
            System.out.println("\n=== Upcoming Appointments ===");
            if (upcomingAppointments.isEmpty()) {
                System.out.println("You have no upcoming appointments.");
            } else {
                for (Appointment appt : upcomingAppointments) {
                    System.out.printf("Appointment ID: %s\nDate: %s\nPatient: %s\nService: %s\n",
                            appt.getId(),
                            DateFormat.formatWithTime(appt.getApptDateTime()),
                            appt.getPatientId(),
                            appt.getService().name());
                    System.out.println("------------------------------");
                }
            }
        } catch (NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            IKeystrokeWait.waitForKeyPress();
            IClearConsole.clearConsole();
        }
    }
    
}
