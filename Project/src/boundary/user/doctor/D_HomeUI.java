package boundary.user.doctor;

import control.appointment.AppointmentController;
import control.billing.InvoiceController;
import control.medicine.MedicineController;
import control.notification.NotificationController;
import control.prescription.PrescriptionController;
import control.user.SessionManager;
import control.user.UnavailableDateController;
import entity.appointment.Appointment;
import entity.appointment.Appointment.Status;
import entity.medicine.Medicine;
import entity.user.Doctor;
import entity.user.Patient;
import entity.user.UnavailableDate;
import entity.user.User;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import exception.user.NoUserLoggedInException;
import interfaces.boundary.IUserInterface;
import interfaces.observer.IObserver;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import observer.NotificationObserver;
import utility.ClearConsole;
import utility.DateFormat;
import utility.InputHandler;
import utility.KeystrokeWait;

public class D_HomeUI implements IUserInterface {
    private final Scanner scanner = InputHandler.getInstance();
    private final SessionManager session;
    private final NotificationController notificationController;
    private IObserver observer;

    public D_HomeUI(SessionManager session) {
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
            System.out.println("8. View notifications");
            System.out.println("9. Logout");
            System.out.println("====================");
    
            int choice = 0;
    
            while (true) {
                try {
                    System.out.print("Please select an option: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    if (choice >= 1 && choice <= 9) break;
                    System.out.println("Enter only a number between 1 and 9.");
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 9.");
                    scanner.nextLine();
                }
            }
    
            if (choice == 9) exit = true;
            else {
                ClearConsole.clearConsole();
                handle_option(choice);
            }
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
            case 8 -> viewNotifications();
            case 9 -> {
                System.out.println("Logging out...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewPatientMedicalRecords() {
        try {
            User user = session.getCurrentUser();

            List<Patient> patients = AppointmentController.getDoctorsPatients((Doctor) user);

            if (patients.isEmpty()) {
                System.out.println("You currently have no patients under your care.");
                KeystrokeWait.waitForKeyPress();
                ClearConsole.clearConsole();
                return;
            }

            System.out.println("Patients under your care:");
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
                System.out.println((i + 1) + ". " + patient.getId() + " - " + patient.getName());
            }

            System.out.print("Enter the number of the patient to view their medical records: ");
            String input = scanner.nextLine();

            int patientIndex;
            try {
                patientIndex = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                return;
            }

            if (patientIndex < 0 || patientIndex >= patients.size()) {
                System.out.println("Invalid selection. Please choose a valid patient number.");
                return;
            }

            Patient selectedPatient = patients.get(patientIndex);
            List<Object> list = AppointmentController.getAppointmentOutcomes(selectedPatient);
            List<Appointment> pastAppts = (List<Appointment>) list.get(0);

            System.out.println("\n==========================================");
            System.out.println("Medical Record for " + selectedPatient.getName() + ":");
            System.out.println(selectedPatient.toString());
            System.out.println("----------------------------------");
            System.out.println("Medical History:");
            System.out.println("----------------------------------");
            
            for (Appointment appt : pastAppts) {
                System.out.println("\tAppointment Date: " + DateFormat.formatWithTime(appt.getApptDateTime()));
                System.out.println("\tID: " + appt.getId());
                System.out.println("\tService: " + appt.getService());
                System.out.println("\tDiagnosis: " + appt.getDiagnosis());
                System.out.println("\tConsultation Notes: " + appt.getNotes());
                System.out.println("----------------------------------");
            }
            System.out.println("==========================================");

        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NoUserLoggedInException e1) {
                    e1.printStackTrace();
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    private void updatePatientMedicalRecord() {
        try {
            System.out.println("=== Update Patient Medical Records ===");
    
            Doctor doctor = (Doctor) session.getCurrentUser();
            List<Patient> patientsUnderCare = AppointmentController.getDoctorsPatients(doctor);
    
            if (patientsUnderCare.isEmpty()) {
                System.out.println("You have no patients under your care.");
                KeystrokeWait.waitForKeyPress();
                return;
            }
    
            System.out.println("Select a patient to update their medical record:");
            for (int i = 0; i < patientsUnderCare.size(); i++) {
                Patient patient = patientsUnderCare.get(i);
                System.out.printf("%d. %s (ID: %s)%n", i + 1, patient.getName(), patient.getId());
            }
    
            int patientChoice;
            while (true) {
                System.out.print("Enter the number of the patient to select (or 0 to cancel): ");
                try {
                    patientChoice = Integer.parseInt(scanner.nextLine().trim());
                    if (patientChoice == 0) {
                        System.out.println("Update canceled.");
                        return;
                    }
                    if (patientChoice < 1 || patientChoice > patientsUnderCare.size()) {
                        System.out.println("Invalid choice. Please try again.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
    
            Patient selectedPatient = patientsUnderCare.get(patientChoice - 1);
            List<Appointment> completedAppointments = AppointmentController.getPastAppointments(selectedPatient);
    
            if (completedAppointments.isEmpty()) {
                System.out.println("This patient has no completed appointments to update.");
                KeystrokeWait.waitForKeyPress();
                return;
            }
    
            int appointmentChoice;
            while (true) {
                System.out.println("\nSelect an appointment to update its medical record:");
                for (int i = 0; i < completedAppointments.size(); i++) {
                    Appointment appointment = completedAppointments.get(i);
                    System.out.printf("%d. Appointment ID: %s, Date: %s, Status: %s%n",
                            i + 1,
                            appointment.getId(),
                            DateFormat.formatWithTime(appointment.getApptDateTime()),
                            appointment.getStatus().name());
                }
    
                System.out.print("Enter the number of the appointment to update (or 0 to cancel): ");
                try {
                    appointmentChoice = Integer.parseInt(scanner.nextLine().trim());
                    if (appointmentChoice == 0) {
                        System.out.println("Update canceled.");
                        return;
                    }
                    if (appointmentChoice < 1 || appointmentChoice > completedAppointments.size()) {
                        System.out.println("Invalid choice. Please try again.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
    
            Appointment selectedAppointment = completedAppointments.get(appointmentChoice - 1);
    
            String updatedDiagnosis;
            do {
                System.out.print("\nEnter updated diagnosis (cannot be empty): ");
                updatedDiagnosis = scanner.nextLine().trim();
                if (updatedDiagnosis.isEmpty()) {
                    System.out.println("Diagnosis cannot be empty. Please enter a valid diagnosis.");
                }
            } while (updatedDiagnosis.isEmpty());
    
            String updatedNotes;
            do {
                System.out.print("Enter updated consultation notes (cannot be empty): ");
                updatedNotes = scanner.nextLine().trim();
                if (updatedNotes.isEmpty()) {
                    System.out.println("Consultation notes cannot be empty. Please enter valid notes.");
                }
            } while (updatedNotes.isEmpty());
    
            String prescriptionChoice;
            while (true) {
                System.out.print("Do you want to update the prescription? (yes/no) (enter nothing to go back to previous menu): ");
                prescriptionChoice = scanner.nextLine().trim().toLowerCase();
                if (prescriptionChoice.isEmpty()) {
                    return;
                }
                if (prescriptionChoice.equals("yes") || prescriptionChoice.equals("no")) {
                    break;
                }
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
    
            HashMap<String, List<Object>> updatedPrescribedMedication = new HashMap<>();
            if (prescriptionChoice.equals("yes")) {

                List<Medicine> availableMedicines = MedicineController.getAllMedicines();
    
                if (availableMedicines.isEmpty()) {
                    System.out.println("No medicines available in the system.");
                } else {
                    System.out.println("Available Medicines:");
                    for (int i = 0; i < availableMedicines.size(); i++) {
                        Medicine medicine = availableMedicines.get(i);
                        System.out.printf("%d. %s, %.2fmg%n", i + 1, medicine.getMedicineName(), medicine.getDosage());
                    }
    
                    System.out.print("Enter the numbers of the medicines to prescribe (comma-separated): ");
                    String[] selectedMedicineNumbers = scanner.nextLine().split(",");
    
                    for (String num : selectedMedicineNumbers) {
                        try {
                            int medicineIndex = Integer.parseInt(num.trim()) - 1;
                            if (medicineIndex < 0 || medicineIndex >= availableMedicines.size()) {
                                System.out.println("Invalid medicine selection. Skipping.");
                                continue;
                            }
    
                            Medicine selectedMedicine = availableMedicines.get(medicineIndex);
    
                            int quantity;
                            while (true) {
                                System.out.print("Enter quantity for " + selectedMedicine.getMedicineName() + ": ");
                                try {
                                    quantity = Integer.parseInt(scanner.nextLine().trim());
                                    if (quantity <= 0) {
                                        System.out.println("Invalid quantity. It must be a positive integer.");
                                    } else {
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid positive integer.");
                                }
                            }
    
                            System.out.print("Enter medication notes for " + selectedMedicine.getMedicineName() + ": ");
                            String prescriptionNotes = scanner.nextLine().trim();
                            if (prescriptionNotes.isEmpty()) {
                                System.out.println("Medication notes cannot be empty. Skipping this medicine.");
                                continue;
                            }
    
                            List<Object> medicineDetails = new ArrayList<>();
                            medicineDetails.add(quantity);
                            medicineDetails.add(prescriptionNotes + " (Prescribed for updated diagnosis)");
                            updatedPrescribedMedication.put(selectedMedicine.getId(), medicineDetails);
    
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Skipping medicine.");
                        }
                    }
                }
            }
    
            AppointmentController.updateAppointmentOutcomes(selectedAppointment, updatedDiagnosis, updatedNotes);
            PrescriptionController.updatePrescription(selectedAppointment, updatedPrescribedMedication);
            InvoiceController.recalculateInvoiceCost(selectedAppointment.getId());
            System.out.println("Medical record updated successfully.");
    
        } catch (NoUserLoggedInException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }
    
    
    
    private void viewSchedule() {
        try {
            Doctor doctor = (Doctor) session.getCurrentUser();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate startDate;
            while (true) {
                System.out.print("Enter the start date (dd-MM-yyyy): ");
                String startDateInput = scanner.nextLine();
                try {
                    startDate = LocalDate.parse(startDateInput, formatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                }
            }

            LocalDate endDate;
            while (true) {
                System.out.print("Enter the end date (dd-MM-yyyy): ");
                String endDateInput = scanner.nextLine();
                try {
                    endDate = LocalDate.parse(endDateInput, formatter);
                    if (!endDate.isBefore(startDate)) {
                        break;
                    } else {
                        System.out.println("End date cannot be before the start date. Please try again.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use dd-MM-yyyy.");
                }
            }

            System.out.println("\n===============================================");
            System.out.println("Schedule for Dr. " + doctor.getName() + " (" + startDate + " to " + endDate + "):");
            System.out.println("=================================================");

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                System.out.println(date + ":");

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
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
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
    
            for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                System.out.println("\nSetting availability for: " + date);
    
                List<LocalDateTime> defaultSlots = AppointmentController.getAvailableTimeSlots(doctor, date);
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
            } else { 
                switch (choice) {
                    case "2" -> {
                        System.out.println("All slots for " + date + " remain available.");
                        continue;
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
        }
            System.out.println("Availability updated.");
        } catch (NoUserLoggedInException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    private void acceptDeclineApptRequest() {
        try {
            User user = session.getCurrentUser();
            List<Appointment> pendingAppointments = AppointmentController.getDoctorAppts((Doctor) user).stream().filter(appt -> appt.getStatus() == Appointment.Status.PENDING).toList();
            
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
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
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
        } catch (NoUserLoggedInException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    private void recordApptOutcome() {
        try {
            System.out.println("Record Appointment Outcome");
            System.out.println("=============================");
            List<Appointment> list = AppointmentController.getDoctorAppts((Doctor) session.getCurrentUser())
                    .stream()
                    .filter(appt -> appt.getStatus() == Status.CONFIRMED && appt.getApptDateTime().toLocalDate().isEqual(LocalDate.now()))
                    .toList();
    
            if (list.isEmpty()) {
                System.out.println("No confirmed appointments for today.");
                return;
            }
    
            System.out.println("Your appointments today:");
            for (Appointment appt : list) {
                System.out.println("=============================");
                System.out.println(appt);
            }
            System.out.println("=============================");
    
            System.out.print("Enter appointment ID (enter nothing to go back to previous menu): ");
            String appointmentId = scanner.nextLine().trim();
    
            if (appointmentId.isEmpty()) {
                return;
            }
    
            Appointment appt = list.stream()
                    .filter(a -> a.getId().equals(appointmentId))
                    .findFirst()
                    .orElse(null);
    
            if (appt == null) {
                System.out.println("Invalid appointment ID. Please select an appointment from the list.");
                return;
            }
    
            String diagnosis;
            do {
                System.out.print("Enter diagnosis (cannot be empty): ");
                diagnosis = scanner.nextLine().trim();
                if (diagnosis.isEmpty()) {
                    System.out.println("Diagnosis cannot be empty. Please enter a valid diagnosis.");
                }
            } while (diagnosis.isEmpty());
    
            String consultationNotes;
            do {
                System.out.print("Enter consultation notes (cannot be empty): ");
                consultationNotes = scanner.nextLine().trim();
                if (consultationNotes.isEmpty()) {
                    System.out.println("Consultation notes cannot be empty. Please enter valid notes.");
                }
            } while (consultationNotes.isEmpty());
    
            HashMap<String, List<Object>> prescriptionItems = new HashMap<>();
    
            System.out.print("Would you like to prescribe medication? (yes/no): ");
            String prescribeMedication = scanner.nextLine().trim().toLowerCase();
    
            if (prescribeMedication.equals("yes")) {
                List<Medicine> availableMedicines = MedicineController.getAllMedicines();
                if (availableMedicines.isEmpty()) {
                    System.out.println("No medicines available in the system.");
                } else {
                    System.out.println("\nAvailable Medicines:");
                    for (int i = 0; i < availableMedicines.size(); i++) {
                        Medicine medicine = availableMedicines.get(i);
                        System.out.println((i + 1) + ". " + medicine.getMedicineName() + ", " + medicine.getDosage() + "mg");
                    }
    
                    System.out.print("Enter the numbers of the medicines to prescribe (comma separated, or press Enter to skip): ");
                    String input = scanner.nextLine().trim();
                    if (!input.isEmpty()) {
                        String[] selectedMedicineNumbers = input.split(",");
    
                        for (String num : selectedMedicineNumbers) {
                            int medicineIndex;
                            try {
                                medicineIndex = Integer.parseInt(num.trim()) - 1;
                            } catch (NumberFormatException e) {
                                System.out.println("\nInvalid selection: " + num.trim() + ". Please enter valid numbers.");
                                continue;
                            }
    
                            if (medicineIndex < 0 || medicineIndex >= availableMedicines.size()) {
                                System.out.println("\nInvalid selection: " + num.trim());
                                continue;
                            }
    
                            Medicine selectedMedicine = availableMedicines.get(medicineIndex);
    
                            System.out.println("\nSelected medicine: " + selectedMedicine.getMedicineName() + ", " + selectedMedicine.getDosage() + "mg");
    
                            int quantity;
                            do {
                                System.out.print("Enter quantity for " + selectedMedicine.getMedicineName() + ", " + selectedMedicine.getDosage() + "mg (positive integer): ");
                                try {
                                    quantity = Integer.parseInt(scanner.nextLine().trim());
                                    if (quantity <= 0) {
                                        System.out.println("Quantity must be a positive integer.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid positive integer.");
                                    quantity = -1;
                                }
                            } while (quantity <= 0);
    
                            System.out.print("Enter notes for " + selectedMedicine.getMedicineName() + ", " + selectedMedicine.getDosage() + "mg: ");
                            String notes = scanner.nextLine();
    
                            List<Object> quantityNotes = new ArrayList<>();
                            quantityNotes.add(quantity);
                            quantityNotes.add(notes);
                            prescriptionItems.put(selectedMedicine.getId(), quantityNotes);
                        }
                    }
                }
            }
    
            AppointmentController.completeAppointment(appt, diagnosis, consultationNotes);
            if (!prescriptionItems.isEmpty()) {
                PrescriptionController.updatePrescription(appt, prescriptionItems);
            }
            InvoiceController.createInvoice(appt.getPatientId(), appt.getId(), 0.09f);
            System.out.println("Outcome recorded successfully.");
        } catch (InvalidInputException | EntityNotFoundException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }
    
    public void viewNotifications() {
        List<List<String>> notiList = observer.getNotificationHistory();
        System.out.println("Notifications");
        System.out.println("=============");
        for (List<String> noti : notiList) {
            System.out.println("Message: " + noti.get(0) + " | Time sent: " + noti.get(1));
        }
        KeystrokeWait.waitForKeyPress();
        ClearConsole.clearConsole();
    }
}
