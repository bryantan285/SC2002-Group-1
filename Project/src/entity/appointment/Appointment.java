package entity.appointment;
import entity.EntityObject;
import java.time.LocalDateTime;

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
    private String apptOutcome;

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
    public String getApptOutcome() {
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
    public void setApptOutcome(String apptOutcome) {
        this.apptOutcome = apptOutcome;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Patient ID: " + patientId + "\n" +
               "Doctor ID: " + doctorId + "\n" +
               "Appointment Date & Time: " + apptDateTime + "\n" +
               "Service: " + service + "\n" +
               "Status: " + status + "\n" +
               "Appointment Outcome: " + apptOutcome;
    }

}
