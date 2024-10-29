// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

public class Patient extends User{
    private String dob;
    // private String contactNumber;
    private String email;
    private String bloodType;
    // private List<Appointment> appointments; 

    public Patient(String id, String name, String gender, String dob, String bloodType, String email) {
        super(true, id, name, gender);
        this.dob = dob;
        this.bloodType = bloodType;
        this.email = email;
        // this.appointments = new ArrayList<>();
    }

    // Getters
    public String getDOB() {
        return this.dob;
    }

    public String getBloodType() {
        return this.bloodType;
    }

    public String getEmail() {
        return this.email;
    }

    //Setters
    public void setEmail(String newEmail) {
        this.email = newEmail;
    } 

    //Patient Menu opt 1
    public void viewMedicalRecord() {
        System.out.println("Patient ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Date of Birth: " + dob);
        System.out.println("Gender: " + getGender());
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Email: " + email);
    }
}
