// package boundary.user.patient;

// import control.appointment.AppointmentController;
// import control.user.PatientController;
// import entity.appointment.Appointment;
// import entity.user.Patient;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.List;
// import java.util.Scanner;

// public class PatientManagement {
//     private final Scanner scanner;
//     private final PatientController patientController;
//     private final AppointmentController appointmentController;

//     public PatientManagement() {
//         this.scanner = new Scanner(System.in);
//         this.patientController = new PatientController();
//         this.appointmentController = new AppointmentController();
//     }

//     public PatientManagement(AppointmentController appointmentController, List<LocalDateTime> availableSlots, PatientController patientController, Scanner scanner) {
//         this.appointmentController = appointmentController;
//         this.patientController = patientController;
//         this.scanner = scanner;
//     }

//     public void patientMenu(String patientId) {
//         patientController.setCurrentPatient(patientId);

//         while (true) {
//             System.out.println("\nPatient Menu:");
//             System.out.println("1. View Medical Record");
//             System.out.println("2. Update Personal Information");
//             System.out.println("3. View Available Appointment Slots");
//             System.out.println("4. Schedule an Appointment");
//             System.out.println("5. Reschedule an Appointment");
//             System.out.println("6. Cancel an Appointment");
//             System.out.println("7. View Scheduled Appointments");
//             System.out.println("8. View Past Appointment Outcome Records");
//             System.out.println("9. Logout");

//             int choice = scanner.nextInt();
//             scanner.nextLine(); // Consume newline

//             switch (choice) {
//                 case 1:
//                     viewMedicalRecord();
//                     break;
//                 case 2:
//                     updatePersonalInformation();
//                     break;
//                 case 3:
//                     viewAvailableAppointmentSlots();
//                     break;
//                 case 4:
//                     scheduleAppointment();
//                     break;
//                 case 5:
//                     rescheduleAppointment();
//                     break;
//                 case 6:
//                     cancelAppointment();
//                     break;
//                 case 7:
//                     viewScheduledAppointments();
//                     break;
//                 case 8:
//                     viewPastAppointmentOutcomeRecords();
//                     break;
//                 case 9:
//                     System.out.println("Logging out...");
//                     return;
//                 default:
//                     System.out.println("Invalid choice. Please try again.");
//             }
//         }
//     }

//     private void viewMedicalRecord() {
//         Patient patient = patientController.getCurrentPatient();
//         if (patient != null) {
//             System.out.println(patient.viewMedicalRecord());
//         } else {
//             System.out.println("Patient record not found.");
//         }
//     }

//     private void updatePersonalInformation() {
//         System.out.print("Enter new email: ");
//         String email = scanner.nextLine();
//         System.out.print("Enter new contact number: ");
//         String contactNumber = scanner.nextLine();

//         patientController.changeEmail(email);
//         patientController.changeContactNumber(contactNumber);
//         System.out.println("Personal information updated successfully.");
//     }

//     private void viewAvailableAppointmentSlots() {
//         System.out.print("Enter doctor ID to view available slots: ");
//         String doctorId = scanner.nextLine();

//         // Retrieve available slots for the given doctor
//         List<LocalDateTime> availableSlots = appointmentController.getAvailableSlots(doctorId);
        
//         if (availableSlots.isEmpty()) {
//             System.out.println("No available slots for this doctor.");
//             return;
//         }

//         // Display the available slots
//         System.out.println("Available slots for Doctor " + doctorId + ":");
//         for (int i = 0; i < availableSlots.size(); i++) {
//             System.out.println((i + 1) + ". " + availableSlots.get(i));
//         }
//     }


//     private void scheduleAppointment() {
//         System.out.print("Enter doctor ID to schedule an appointment: ");
//         String doctorId = scanner.nextLine();

//         // Call the method to display available slots and allow user to select one
//         List<LocalDateTime> availableSlots = appointmentController.getAvailableSlots(doctorId);

//         if (availableSlots.isEmpty()) {
//             System.out.println("No available slots.");
//             return;
//         }

//         // Display available slots for selection
//         System.out.println("Available slots for Doctor " + doctorId + ":");
//         for (int i = 0; i < availableSlots.size(); i++) {
//             System.out.println((i + 1) + ". " + availableSlots.get(i));
//         }

//         // Proceed with the scheduling process
//         System.out.print("Choose an available slot number: ");
//         int slotChoice = scanner.nextInt();
//         scanner.nextLine(); // consume the newline
//         LocalDateTime selectedSlot = availableSlots.get(slotChoice - 1);

//         System.out.println("Choose a service type:");
//         Appointment.Service[] services = Appointment.Service.values();
//         for (int i = 0; i < services.length; i++) {
//             System.out.println((i + 1) + ". " + services[i].name());
//         }

//         System.out.print("Enter the number corresponding to the service: ");
//         int serviceChoice;
//         try {
//             serviceChoice = Integer.parseInt(scanner.nextLine());
//             if (serviceChoice < 1 || serviceChoice > services.length) {
//                 System.out.println("Invalid choice. Please enter a number between 1 and " + services.length);
//                 return;
//             }
//             Appointment.Service service = services[serviceChoice - 1];
//             patientController.scheduleAppointment(doctorId, service, selectedSlot);
//             System.out.println("Appointment scheduled successfully.");
//         } catch (NumberFormatException e) {
//             System.out.println("Invalid input. Please enter a number.");
//         }
//     }

//     private void rescheduleAppointment() {
//         System.out.print("Enter appointment ID to reschedule: ");
//         String apptId = scanner.nextLine();
        
//         System.out.print("Enter new date and time (YYYY-MM-DD HH:MM): ");
//         String dateTimeInput = scanner.nextLine();

//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//         LocalDateTime newDateTime;
//         newDateTime = LocalDateTime.parse(dateTimeInput, formatter);

//         patientController.rescheduleAppointment(apptId, newDateTime);
//         System.out.println("Appointment rescheduled successfully.");
//     }

//     private void cancelAppointment() {
//         System.out.print("Enter appointment ID to cancel: ");
//         String apptId = scanner.nextLine();

//         patientController.cancelAppointment(apptId);
//         System.out.println("Appointment canceled successfully.");
//     }

//     private void viewScheduledAppointments() {
//         System.out.println("Scheduled Appointments:");
//         patientController.viewScheduledAppointments();
//     }

//     private void viewPastAppointmentOutcomeRecords() {
//         System.out.println("Past Appointment Outcome Records:");
//         patientController.viewPastAppointmentOutcomeRecords();
//     }
// }