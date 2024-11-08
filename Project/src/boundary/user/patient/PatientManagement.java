package boundary.user.patient;
import entity.user.Patient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PatientManagement {
    private List<Patient> patients;
    private Scanner scanner;

    public PatientManagement() {
        this.patients = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        loadPatientsFromCSV("data/Patient_List.csv");
    }

    private void loadPatientsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Check if row has the correct number of fields before creating Patient
                if (values.length >= 6) {
                    Patient patient = new Patient(
                            values[0].trim(), // Patient ID
                            values[1].trim(), // Name
                            values[2].trim(), // Date of Birth
                            values[3].trim(), // Gender
                            values[4].trim(), // Blood Type
                            values[5].trim()  // Contact Information (Email)
                    );
                    patients.add(patient);
                } else {
                    System.out.println("Invalid row format in CSV file: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public void patientMenu() {
        while (true) {
            System.out.println("Patient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. Schedule an Appointment");
            System.out.println("4. Reschedule an Appointment");
            System.out.println("5. Cancel an Appointment");
            System.out.println("6. View Scheduled Appointments");
            System.out.println("7. View Past Appointment Outcome Records");
            System.out.println("8. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    // updatePersonalInformation();
                    break;
                case 3:
                    // scheduleAppointment();
                    break;
                case 4:
                    // rescheduleAppointment();
                    break;
                case 5:
                    // cancelAppointment();
                    break;
                case 6:
                    // viewScheduledAppointments();
                    break;
                case 7:
                    // viewPastAppointmentOutcomeRecords();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Select a patient based on ID
    private Patient selectPatient() {
        System.out.println("Enter patient ID: ");
        String patientID = scanner.nextLine();
        for (Patient patient : patients) {
            if (patient.getId().equalsIgnoreCase(patientID)) {
                return patient;
            }
        }
        System.out.println("Patient not found.");
        return null;
    }

    // 1. View medical record
    private void viewMedicalRecord() {
        Patient patient = selectPatient();
        if (patient != null) {
            patient.viewMedicalRecord();
        }
    }
}
