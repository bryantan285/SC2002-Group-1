package entity.user;

public class Patient extends User {
    private String dob;
    private String bloodType;
    private String contactNumber;
    private String email;

    public Patient() {
        
    }

    public Patient(String id, String name, String gender, String dob, String bloodType, String contactNumber, String email) {
        super(true, id, name, gender);
        this.dob = dob;
        this.bloodType = bloodType;
        this.contactNumber = contactNumber;
        this.email = email;
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

    public String viewMedicalRecord() {
        return "Patient ID: " + getId() + "\n" +
               "Name: " + getName() + "\n" +
               "Date of Birth: " + dob + "\n" +
               "Gender: " + getGender() + "\n" +
               "Blood Type: " + bloodType + "\n" +
               "Email: " + email + "\n" +
               "Contact Number: " + contactNumber;
    }

    @Override
    public String toString() {
        return getId() + ", " + getName() + ", " + getGender() + ", " + dob + ", " + bloodType + ", " + email + ", " + contactNumber;
    }
}
