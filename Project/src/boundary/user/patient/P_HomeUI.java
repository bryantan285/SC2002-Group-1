package boundary.user.patient;

import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.notification.NotificationController;
import control.user.HospitalStaffController;
import control.user.PatientController;
import control.user.SessionManager;
import control.user.UserController;
import entity.appointment.Appointment;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import entity.notification.Notification;
import entity.user.Doctor;
import entity.user.HospitalStaff;
import entity.user.Patient;
import entity.user.User;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import exception.user.NoUserLoggedInException;
import interfaces.boundary.IUserInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import utility.ClearConsole;
import utility.DateTimeSelect;
import utility.InputHandler;
import utility.KeystrokeWait;

public class P_HomeUI implements IUserInterface {
    private static final Scanner scanner = InputHandler.getInstance();
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
            System.out.println("9. View Notifications");
            System.out.println("10. Logout");
            System.out.println("====================");
    
            int choice = 0;
    
            while (true) {
                try {
                    System.out.print("Please select an option: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
    
                    if (choice >= 1 && choice <= 9) {
                        break;
                    } else {
                        System.out.println("Enter only a number between 1 and 10.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 10.");
                    scanner.nextLine();
                }
            }
            if (choice == 10) {
                exit = true;
            } else {
                ClearConsole.clearConsole();
                handle_option(choice);
            }
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
            case 9 -> viewNotifications();
            case 10 -> {
                System.out.println("Logging out...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void viewMedicalRecord() {
        try {
            Patient patient = (Patient) session.getCurrentUser();
            System.out.println("View Medical Record");
            System.out.println("==================");
            System.out.println(patient.toString());
            System.out.println("==================");
        } catch (NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    private void updatePersonalInformation() {
        try {
            Patient patient = (Patient) session.getCurrentUser();
            
            while (true) {
                System.out.println("\nWhat would you like to update?");
                System.out.println("1. Update Email");
                System.out.println("2. Update Contact Number");
                System.out.println("3. Change password");
                System.out.println("4. Go Back");
                System.out.print("Enter your choice: ");
                
                String choice = scanner.nextLine();
                ClearConsole.clearConsole();
                
                switch (choice) {
                    case "1" -> updateEmail(patient);
                    case "2" -> updateContactNumber(patient);
                    case "3" -> changePassword(patient);
                    case "4" -> {
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
        while (true) {
            try {
                System.out.println("Update email");
                System.out.println("==================");
                System.out.print("Enter new email (enter nothing to go back to previous menu): ");
                String email = scanner.nextLine();
                if (email.isEmpty()) {
                    ClearConsole.clearConsole();
                    return;
                }
    
                PatientController.changeEmail(patient, email);
                System.out.println("Email updated successfully.");
                break;
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    
        KeystrokeWait.waitForKeyPress();
        ClearConsole.clearConsole();
    }
    
    private void updateContactNumber(Patient patient) {
        while (true) {
            try {
                System.out.println("Update contact number");
                System.out.println("==================");
                System.out.print("Enter new contact number (enter nothing to go back to previous menu): ");
                String contactNumber = scanner.nextLine();
                if (contactNumber.isEmpty()) {
                    ClearConsole.clearConsole();
                    return;
                }
    
                PatientController.changeContactNumber(patient, contactNumber);
                System.out.println("Contact number updated successfully.");
                break;
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    
        KeystrokeWait.waitForKeyPress();
        ClearConsole.clearConsole();
    }

    private void changePassword(Patient patient) {
        String newPassword;
        String confirmPassword;

        System.out.println("Change Password");
        System.out.println("==================");
        while (true) {
            try {
                System.out.print("Enter your new password (Enter nothing to go back to previous menu): ");
                newPassword = scanner.nextLine().trim();

                if (newPassword.isEmpty()) {
                    return;
                }

                if (newPassword.isEmpty() || newPassword.length() < 8) {
                    throw new InvalidInputException("Password must be at least 8 characters long.");
                }

                System.out.print("Confirm your new password: ");
                confirmPassword = scanner.nextLine().trim();

                if (!newPassword.equals(confirmPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                } else {
                    UserController.passwordChange(patient, newPassword);
                    System.out.println("Password changed successfully.");
                    KeystrokeWait.waitForKeyPress();
                    ClearConsole.clearConsole();
                    break;
                }
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                ClearConsole.clearConsole();
            }
        }
    }
    

    private void viewAvailableAppointmentSlots() {
        while (true) {
            try {
                System.out.print("Enter doctor ID to view available slots (enter nothing to go back to previous menu): ");
                String doctorId = scanner.nextLine().trim();
    
                if (doctorId.isEmpty()) {
                    return;
                }
    
                Doctor doc = (Doctor) HospitalStaffController.findById(doctorId);
    
                LocalDate selectedDate;
                while (true) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    System.out.print("Enter the date (dd-MM-yyyy) to view available slots (today or future dates only, enter nothing to go back): ");
                    String dateInput = scanner.nextLine().trim();
                    
                    if (dateInput.isEmpty()) {
                        return;
                    }
    
                    try {
                        selectedDate = LocalDate.parse(dateInput, formatter);
                        if (selectedDate.isBefore(LocalDate.now())) {
                            System.out.println("Invalid date. The date must be today or a future date.");
                        } else {
                            break;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                    }
                }
    
                List<LocalDateTime> availableSlots = AppointmentController.getAvailableSlots(doc, selectedDate);
    
                if (availableSlots.isEmpty()) {
                    System.out.println("No available slots for this doctor on " + selectedDate + ".");
                    KeystrokeWait.waitForKeyPress();
                    ClearConsole.clearConsole();
                    return;
                }
    
                System.out.println("Available slots for Doctor " + doctorId + " on " + selectedDate + ":");
                for (int i = 0; i < availableSlots.size(); i++) {
                    System.out.println((i + 1) + ". " + availableSlots.get(i));
                }
    
                KeystrokeWait.waitForKeyPress();
                ClearConsole.clearConsole();
                return;
    
            } catch (EntityNotFoundException e) {
                System.out.println("Error: Doctor not found. Please enter a valid doctor ID.");
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                ClearConsole.clearConsole();
            }
        }
    }
    

    private void scheduleAppointment() {
        try {
            System.out.println("List of Doctors:");
            List<HospitalStaff> list = HospitalStaffController.getAllStaff()
                .stream()
                .filter(staff -> staff.getId().startsWith("D"))
                .sorted(Comparator.comparing(HospitalStaff::getId))
                .collect(Collectors.toList());
    
            if (list.isEmpty()) {
                System.out.println("No doctors available.");
                KeystrokeWait.waitForKeyPress();
                ClearConsole.clearConsole();
                return;
            }
    
            for (HospitalStaff s : list) {
                System.out.println("==================");
                System.out.println("ID: " + s.getId());
                System.out.println("Name: " + s.getName());
            }
            System.out.println("==================");
            System.out.print("Enter doctor ID to schedule an appointment (enter nothing to go back): ");
            String doctorId = scanner.nextLine().trim();
    
            if (doctorId.isEmpty()) {
                return;
            }
    
            Doctor doc = (Doctor) HospitalStaffController.findById(doctorId);
            LocalDateTime selectedSlot = DateTimeSelect.selectNewDateAndTime(doc);
    
            if (selectedSlot == null) {
                return;
            }
    
            System.out.println("Choose a service type:");
            Appointment.Service[] services = Appointment.Service.values();
            for (int i = 0; i < services.length; i++) {
                System.out.println((i + 1) + ". " + services[i].name());
            }
    
            int serviceChoice;
            while (true) {
                System.out.print("Enter the number corresponding to the service: ");
                try {
                    serviceChoice = Integer.parseInt(scanner.nextLine());
                    if (serviceChoice < 1 || serviceChoice > services.length) {
                        System.out.println("Invalid choice. Please enter a number between 1 and " + services.length);
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }
    
            Appointment.Service service = services[serviceChoice - 1];
            AppointmentController.scheduleAppointment(doc, (Patient) session.getCurrentUser(), service, selectedSlot);
            System.out.println("Appointment scheduled successfully.");
    
        } catch (EntityNotFoundException | InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            ClearConsole.clearConsole();
        }
    }
    
    


    private void rescheduleAppointment() {
        try {
            List<Appointment> list = AppointmentController.getScheduledAppointments((Patient) session.getCurrentUser());
            list.sort(Comparator.comparing(Appointment::getId));
    
            if (list.isEmpty()) {
                System.out.println("You have no scheduled appointments.");
                KeystrokeWait.waitForKeyPress();
                return;
            }
    
            System.out.println("Your Scheduled Appointments:");
            for (Appointment appt : list) {
                System.out.println("=============================");
                System.out.println(appt.toString());
            }
            System.out.println("=============================");
    
            Appointment selectedAppointment = null;
            while (selectedAppointment == null) {
                System.out.print("Enter the appointment ID to reschedule (enter nothing to go back): ");
                String apptId = scanner.nextLine().trim();
    
                if (apptId.isEmpty()) {
                    return;
                }
    
                selectedAppointment = list.stream()
                    .filter(appt -> appt.getId().equals(apptId))
                    .findFirst()
                    .orElse(null);
    
                if (selectedAppointment == null) {
                    System.out.println("Invalid appointment ID. Please enter a valid appointment ID from the list.");
                }
            }
            Doctor doc = (Doctor) HospitalStaffController.findById(selectedAppointment.getDoctorId());
            LocalDateTime newDateTime = DateTimeSelect.selectNewDateAndTime(doc);
    
            if (newDateTime == null) {
                ClearConsole.clearConsole();
                return;
            }
    
            AppointmentController.rescheduleAppointment(selectedAppointment, newDateTime);
            System.out.println("Appointment rescheduled successfully.");
    
        } catch (InvalidInputException | EntityNotFoundException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            ClearConsole.clearConsole();
        }
    }
    
    
    

    private void cancelAppointment() {
        try {
            List<Appointment> list = AppointmentController.getScheduledAppointments((Patient) session.getCurrentUser());
            list.sort(Comparator.comparing(Appointment::getId));
    
            if (list.isEmpty()) {
                System.out.println("You have no scheduled appointments.");
                KeystrokeWait.waitForKeyPress();
                return;
            }
    
            System.out.println("Scheduled Appointments:");
            for (Appointment appt : list) {
                System.out.println("=============================");
                System.out.println(appt.toString());
            }
            System.out.println("=============================");
            
            Appointment selectedAppointment = null;
            while (selectedAppointment == null) {
                System.out.print("Enter appointment ID to cancel (enter nothing to go back): ");
                String apptId = scanner.nextLine().trim();
    
                if (apptId.isEmpty()) {
                    return;
                }
    
                selectedAppointment = list.stream()
                    .filter(appt -> appt.getId().equals(apptId))
                    .findFirst()
                    .orElse(null);
    
                if (selectedAppointment == null) {
                    System.out.println("Invalid appointment ID. Please enter a valid appointment ID from the list.");
                }
            }
    
            AppointmentController.cancelAppointment(selectedAppointment);
            System.out.println("Appointment canceled successfully.");
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            ClearConsole.clearConsole();
        }
    }
    

    private void viewScheduledAppointments() {
        try {
            List<Appointment> list = AppointmentController.getScheduledAppointments((Patient) session.getCurrentUser());
            list.sort(Comparator.comparing(Appointment::getId));
            System.out.println("Scheduled Appointments:");
            for (Appointment appt : list) {
                System.out.println("=============================");
                System.out.println(appt.toString());
            }
            System.out.println("=============================");
        } catch (InvalidInputException | NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }

    private void viewPastAppointmentOutcomeRecords() {
        try {
            System.out.println("Past Appointment Outcome Records:");
            List<Object> list = AppointmentController.getAppointmentOutcomes((Patient) session.getCurrentUser());
            List<Appointment> pastAppts = (List<Appointment>) list.get(0);
            HashMap<String, Prescription> pastPrescriptions = (HashMap<String, Prescription>) list.get(1);
            HashMap<String, List<PrescriptionItem>> prescriptionItems = (HashMap<String, List<PrescriptionItem>>) list.get(2);
            for (Appointment appt : pastAppts) {
                System.out.println("=============================");
                System.out.println("ID: " + appt.getId());
                System.out.println("Service: " + appt.getService());
                System.out.println("Prescription items:");
                Prescription prescription = pastPrescriptions.get(appt.getId());
                if (prescription == null) {
                    System.out.println("No prescribed items.");
                } else {
                    for (PrescriptionItem item : prescriptionItems.get(prescription.getId())) {
                        Medicine med = MedicineController.getMedicineById(item.getMedicineId());
                        System.out.println("=============");
                        System.out.println("Name: " + med.getMedicineName());
                        System.out.println("Dosage: " + med.getDosage());
                        System.out.println("Quantity: " + item.getQuantity());
                        System.out.println("Status: " + item.getStatus());
                    }
                    System.out.println("=============");
                }
            }
            System.out.println("=============================");

        } catch (InvalidInputException | NoUserLoggedInException | EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            KeystrokeWait.waitForKeyPress();
            ClearConsole.clearConsole();
        }
    }
    
    public void viewNotifications() {
        try {
            List<Notification> list = NotificationController.getNotificationByUser((User) session.getCurrentUser());
            for (Notification noti : list) {
                System.out.println(noti.getMessage());
            }
        } catch (NoUserLoggedInException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
