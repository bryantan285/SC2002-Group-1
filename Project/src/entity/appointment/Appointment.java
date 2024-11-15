package entity.appointment;
import entity.EntityObject;
import entity.medicine.Prescription;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Appointment extends EntityObject {
    public enum Service {
        CONSULTATION,
        XRAY,
        LABTEST
    }

    private String id;
    private String patientId;
    private String doctorId;
    private LocalDateTime apptDateTime;
    private Service service;
    
    // Below fields can only be changed by doctors
    public enum Status {PENDING, CONFIRMED, COMPLETED, CANCELED}
    private Status status;
    
    // List to store multiple appointment outcomes
    private List<AppointmentOutcome> apptOutcome;

    public Appointment() {
        
    }

    public Appointment(String id, String patient, String doctor, Service service, LocalDateTime dateTime){
        this.id = id;
        this.patientId = patient;
        this.doctorId = doctor;
        this.service = service;
        this.apptDateTime = dateTime;
        
        // TODO - update admin's appt list

        this.status = Status.PENDING; // initially set to false,
                             // can only be approved (changed to true) by Doctors
        this.apptOutcome = new ArrayList<>();
    }

    // Getters

    @Override
    public String getId() {
        return this.id;
    }
    public String getPatientId() {
        return patientId;
    }
    public String getDoctorId() {
        return doctorId;
    }
    public LocalDateTime getApptDateTime() {
        return apptDateTime;
    }
    public Status getStatus() {
        return status;
    }

    public List<AppointmentOutcome> getApptOutcome() {
        return apptOutcome;
    }

    public Service getService() {
        return service;
    }


    //Setters
    public void setid(String id) {
        this.id = id;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    public void setApptDate(LocalDateTime apptDateTime) {
        this.apptDateTime = apptDateTime;
    }
    public void setStatus(Status set){
        this.status = set;
    }
    
    public void setApptOutcome(List<AppointmentOutcome> apptOutcome) {
        this.apptOutcome = apptOutcome;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n")
          .append("Patient ID: ").append(patientId).append("\n")
          .append("Doctor ID: ").append(doctorId).append("\n")
          .append("Appointment Date & Time: ").append(apptDateTime).append("\n")
          .append("Service: ").append(service).append("\n")
          .append("Status: ").append(status).append("\n");

        // Loop through the outcomes and append them
        for (int i = 0; i < apptOutcome.size(); i++) {
            AppointmentOutcome outcome = apptOutcome.get(i);
            sb.append("Outcome ").append(i + 1).append(": \n")
              .append("Diagnosis: ").append(outcome.getDiagnosis()).append("\n")
              .append("Prescriptions: ").append(outcome.getPrescriptions().toString()).append("\n")
              .append("Consultation Notes: ").append(outcome.getConsultationNotes()).append("\n");
        }

        return sb.toString();
    }

    public static class AppointmentOutcome {
        private String diagnosis;
        private String consultationNotes;
        private List<Prescription> prescriptions;

        // Constructor
        public AppointmentOutcome(String diagnosis, String consultationNotes, List<Prescription> prescriptions) {
            this.diagnosis = diagnosis;
            this.consultationNotes = consultationNotes;
            this.prescriptions = prescriptions;
        }

        // Getters and Setters
        public String getDiagnosis() {
            return diagnosis;
        }

        public void setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
        }

        public String getConsultationNotes() {
            return consultationNotes;
        }

        public void setConsultationNotes(String consultationNotes) {
            this.consultationNotes = consultationNotes;
        }

        public List<Prescription> getPrescriptions() {
            return prescriptions;
        }

        public void setPrescriptions(List<Prescription> prescriptions) {
            this.prescriptions = prescriptions;
        }
    }

    // Serialize apptOutcome into a CSV-friendly format
    public String serializeApptOutcome() {
        if (apptOutcome == null || apptOutcome.isEmpty()) {
            return ""; // No outcomes
        }
        List<String> outcomeStrings = new ArrayList<>();
        for (AppointmentOutcome outcome : apptOutcome) {
            String outcomeString = String.join("~",
                outcome.getDiagnosis(),
                outcome.getConsultationNotes(),
                outcome.getPrescriptions().toString() // Simplify or customize this based on Prescription
            );
            outcomeStrings.add(outcomeString);
        }
        return String.join("|", outcomeStrings); // Use '|' as the separator for multiple outcomes
    }

    // Deserialize apptOutcome from a CSV string
    public void deserializeApptOutcome(String serializedData) {
        if (serializedData == null || serializedData.isEmpty()) {
            apptOutcome = new ArrayList<>();
            return;
        }
        apptOutcome = new ArrayList<>();
        String[] outcomes = serializedData.split("\\|"); // Split by '|'
        for (String outcomeString : outcomes) {
            String[] parts = outcomeString.split("~"); // Split each outcome by '~'
            if (parts.length >= 3) {
                String diagnosis = parts[0];
                String consultationNotes = parts[1];
                List<Prescription> prescriptions = parsePrescriptions(parts[2]); // Implement this method
                apptOutcome.add(new AppointmentOutcome(diagnosis, consultationNotes, prescriptions));
            }
        }
    }

    // Helper method to parse prescriptions
    private List<Prescription> parsePrescriptions(String prescriptionsString) {
        // Parse prescriptions from the string (implement as per your Prescription class)
        return new ArrayList<>(); // Stub implementation
    }

}