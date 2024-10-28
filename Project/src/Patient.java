import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Patient {
    private int patientId;
    private String patientName;
    private LocalDate patientDOB;
    private String gender;
    private String contactNumber;
    private String email;
    private String bloodType;
    private List<Appointment> appointments; 

    public Patient(String patientID, String patientName, String patientDOB, String gender, String contactNumber, String email, String bloodType) {
        this.patientID = patientID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.bloodType = bloodType;
        this.appointments = new ArrayList<>();
    }

}
