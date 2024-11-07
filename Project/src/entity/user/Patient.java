package entity.user;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

import java.util.Date;

public class Patient extends User{
    private Date dob;
    // private String contactNumber;
    private String bloodType;
    private String email;
    // private List<Appointment> appointments; 

    public Patient() {
        super();
    }

    public Patient(String id, String name, String gender, Date dob, String bloodType, String email, String password) {
        super(true, id, password, name, gender);
        this.dob = dob;
        this.bloodType = bloodType;
        this.email = email;
        // this.appointments = new ArrayList<>();
    }

    // Getters
    public Date getDOB() {
        return this.dob;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public String getEmail() {
        return this.email;
    }

    //Setters

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Patient Menu opt 1
    public void viewMedicalRecord() {
        System.out.println("Patient ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Date of Birth: " + dob);
        System.out.println("Gender: " + getGender());
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Email: " + getEmail());
    }

    @Override
    public String toString() {
        return super.getId() + ", " + super.getName() + ", " + super.getGender() + ", " + dob + ", " + bloodType + ", " + email;
    }

}
