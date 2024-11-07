package entity.appointment;
import entity.EntityObject;
import java.util.Date;

public class Appointment extends EntityObject {
    private String id;
    private String patientId;
    private String doctorId;
    private Date apptDateTime;

    
    // Below fields can only be changed by doctors
    public enum Status {PENDING, CONFIRMED, COMPLETED, CANCELED}
    private Status status;
    private String apptOutcome;

    public Appointment() {
        
    }

    public Appointment(String id, String patient, String doctor, Date dateTime){
        this.id = id;
        this.patientId = patient;
        this.doctorId = doctor;
        this.apptDateTime = dateTime;
        
        // TODO - update admin's appt list

        this.status = Status.PENDING; // initially set to false,
                             // can only be approved (changed to true) by Doctors
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
    public Date getApptDateTime() {
        return apptDateTime;
    }
    public Status getStatus() {
        return status;
    }
    public String getApptOutcome() {
        return apptOutcome;
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
    public void setApptDate(Date apptDateTime) {
        this.apptDateTime = apptDateTime;
    }
    public void setStatus(Status set){
        this.status = set;
    }
    public void setApptOutcome(String apptOutcome) {
        this.apptOutcome = apptOutcome;
    }

    public static void main(String[] args) {
        // Date date = Date.of(2024, 10,30,11, 40);

        // Appointment a = new Appointment("P001", "D001", date);
        // Appointment b = new Appointment("P002", "D002", date);
        // Appointment c = new Appointment("P003", "D003", date);

        // System.out.println(a.getid());
        // System.out.println(a.getApptOutcome());
        // System.out.println(a.getDoctorId());
        // System.out.println(a.getPatientId());
        // System.out.println(a.getApptDateTime());
        // System.out.println(a.getStatus());

        // System.out.println(b.getid());
        // System.out.println(b.getApptOutcome());
        // System.out.println(b.getDoctorId());
        // System.out.println(b.getPatientId());
        // System.out.println(b.getApptDateTime());
        // System.out.println(b.getStatus());

        // System.out.println(c.getid());
        // System.out.println(c.getApptOutcome());
        // System.out.println(c.getDoctorId());
        // System.out.println(c.getPatientId());
        // System.out.println(c.getApptDateTime());
        // System.out.println(c.getStatus());
        
    }
}
