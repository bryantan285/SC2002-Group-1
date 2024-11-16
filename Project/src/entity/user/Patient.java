package entity.user;

import java.time.LocalDateTime;
import utility.DateFormat;
/**
 * The Patient class represents a patient in the healthcare system. It extends the User class
 * and includes additional details specific to the patient, such as date of birth, blood type,
 * contact information, and medical records (outcomes from appointments).
 */
public class Patient extends User {
    private LocalDateTime dob;
    private String bloodType;
    private String contactNumber;
    private String email;

    /**
     * Default constructor for the Patient class.
     * Calls the default constructor of the superclass (User).
     */
    public Patient() {
        super();
    }

    /**
     * Constructor to initialize a Patient object with specific details.
     *
     * @param id The unique identifier for the patient.
     * @param name The name of the patient.
     * @param gender The gender of the patient.
     * @param dob The date of birth of the patient.
     * @param bloodType The blood type of the patient.
     * @param contactNumber The contact number of the patient.
     * @param email The email address of the patient.
     * @param medicalRecords A list of appointment outcomes (medical records) for the patient.
     */
    public Patient(String id, String name, String gender, LocalDateTime dob, String bloodType, String contactNumber, String email) {
        super(true, id, name, gender);
        this.dob = dob;
        this.bloodType = bloodType;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getters

    /**
     * Gets the date of birth of the patient.
     *
     * @return The patient's date of birth.
     */
    public LocalDateTime getDob() {
        return dob;
    }

    /**
     * Gets the blood type of the patient.
     *
     * @return The patient's blood type.
     */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Gets the email address of the patient.
     *
     * @return The patient's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the contact number of the patient.
     *
     * @return The patient's contact number.
     */
    public String getContactNumber() {
        return contactNumber;
    }

    // Setters

    /**
     * Sets the date of birth of the patient.
     *
     * @param dob The date of birth to set for the patient.
     */
    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    /**
     * Sets the blood type of the patient.
     *
     * @param bloodType The blood type to set for the patient.
     */
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param email The email address to set for the patient.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the contact number of the patient.
     *
     * @param contactNumber The contact number to set for the patient.
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    
    /**
     * Returns a string representation of the Patient object.
     *
     * @return A formatted string containing the patient's details:
     *         Patient ID, Name, Date of Birth, Gender, Blood Type, Email, and Contact Number.
     */
    @Override
    public String toString() {
        return "Patient ID: " + getId() + "\n" +
               "Name: " + getName() + "\n" +
               "Date of Birth: " + DateFormat.formatNoTime(dob) + "\n" +
               "Gender: " + getGender() + "\n" +
               "Blood Type: " + bloodType + "\n" +
               "Email: " + email + "\n" +
               "Contact Number: " + contactNumber;
    }
}
