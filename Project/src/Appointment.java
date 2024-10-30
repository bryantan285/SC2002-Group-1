import java.util.*;
import java.time.*;;
public class Appointment {
    private static int idCounter = 1; // Counter to generate unique IDs
    private int apptId;
    private String patientId;
    private String doctorId;
    private LocalDateTime apptDateTime;

    
    // Below fields can only be changed by doctors
    enum Status {PENDING, CONFIRMED, COMPLETED, CANCELED}
    private Status status;
    private String apptOutcome;

    public Appointment(String patient, String doctor, LocalDateTime dateTime){
        this.apptId = Appointment.idCounter;
        idCounter++;
        this.patientId = patient;
        this.doctorId = doctor;
        this.apptDateTime = dateTime;
        
        // TODO - update admin's appt list

        this.status = Status.PENDING; // initially set to false,
                             // can only be approved (changed to true) by Doctors
    }

    // Getters
    public int getApptId() {
        return this.apptId;
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
    public String getApptOutcome() {
        return apptOutcome;
    }


    //Setters
    public void setApptId(int apptId) {
        this.apptId = apptId;
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
    public void setApptOutcome(String apptOutcome) {
        this.apptOutcome = apptOutcome;
    }

    public static void main(String[] args) {
        LocalDateTime date = LocalDateTime.of(2024, 10,30,11, 40);

        Appointment a = new Appointment("P001", "D001", date);
        Appointment b = new Appointment("P002", "D002", date);
        Appointment c = new Appointment("P003", "D003", date);

        System.out.println(a.getApptId());
        System.out.println(a.getApptOutcome());
        System.out.println(a.getDoctorId());
        System.out.println(a.getPatientId());
        System.out.println(a.getApptDateTime());
        System.out.println(a.getStatus());

        System.out.println(b.getApptId());
        System.out.println(b.getApptOutcome());
        System.out.println(b.getDoctorId());
        System.out.println(b.getPatientId());
        System.out.println(b.getApptDateTime());
        System.out.println(b.getStatus());

        System.out.println(c.getApptId());
        System.out.println(c.getApptOutcome());
        System.out.println(c.getDoctorId());
        System.out.println(c.getPatientId());
        System.out.println(c.getApptDateTime());
        System.out.println(c.getStatus());
        
    }
}
