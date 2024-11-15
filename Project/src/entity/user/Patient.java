package entity.user;

import entity.appointment.Appointment.AppointmentOutcome;
import java.util.List;

public class Patient extends User {
    private String dob;
    private String bloodType;
    private String contactNumber;
    private String email;
    private List<AppointmentOutcome> medicalRecords;

    public Patient() {
        super();
    }

    public Patient(String id, String name, String gender, String dob, String bloodType, String contactNumber, String email, List<AppointmentOutcome> medicalRecords) {
        super(true, id, name, gender);
        this.dob = dob;
        this.bloodType = bloodType;
        this.contactNumber = contactNumber;
        this.email = email;
        this.medicalRecords = medicalRecords;
    }

    // Getters
    public String getDob() {
        return dob;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public List<AppointmentOutcome> getAppointmentOutcomeRecords() {
        return medicalRecords;
    }

    // Setters
    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAppointmentOutcomeRecords(List<AppointmentOutcome> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Patient ID: ").append(getId()).append("\n")
          .append("Name: ").append(getName()).append("\n")
          .append("Date of Birth: ").append(dob).append("\n")
          .append("Gender: ").append(getGender()).append("\n")
          .append("Blood Type: ").append(bloodType).append("\n")
          .append("Email: ").append(email).append("\n")
          .append("Contact Number: ").append(contactNumber).append("\n")
          .append("Medical Records: \n");
        
        if (medicalRecords != null && !medicalRecords.isEmpty()) {
            for (AppointmentOutcome record : medicalRecords) {
                sb.append(record.toString()).append("\n");
            }
        } else {
            sb.append("No past medical records available.");
        }

        return sb.toString();
    }
}
