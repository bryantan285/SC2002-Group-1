public class Appointment {
    private String apptId;
    private String patientId;
    private String doctorId;
    private Date apptDate;

    // Below fields can only be changed by doctors
    private boolean status; // true = approved/scheduled
                            // false = canceled/rejected/pending approval
    private String apptOutcome;

    public Appointment(String patient, String doctor, Date date){
        this.patientId = patient;
        this.doctorId = doctor;
        this.apptDate = date;
        
        // TODO - apptID ??
        // TODO - update admin's appt list

        this.status = false; // initially set to false,
                             // can only be approved (changed to true) by Doctors
    }

    // Getters
    public String getApptId() {
        return apptId;
    }
    public String getPatientId() {
        return patientId;
    }
    public String getDoctorId() {
        return doctorId;
    }
    public Date getApptDate() {
        return apptDate;
    }
    public boolean getStatus() {
        return status;
    }
    public String getApptOutcome() {
        return apptOutcome;
    }


    //Setters
    public void setApptId(String apptId) {
        this.apptId = apptId;
    }
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    public void setApptDate(Date apptDate) {
        this.apptDate = apptDate;
    }
    public void setStatus(boolean set){
        this.status = set;
    }
    public void setApptOutcome(String apptOutcome) {
        this.apptOutcome = apptOutcome;
    }


}
