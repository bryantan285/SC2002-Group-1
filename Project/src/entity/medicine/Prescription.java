package entity.medicine;

import entity.EntityObject;

public class Prescription extends EntityObject {
    private String id;
    private String patientId;
    private String apptId;
    private boolean isActive;

    public Prescription() {
        
    }

    // Constructor
    public Prescription(String id, String patientId, String apptId, boolean isActive) {
        this.id = id;
        this.patientId = patientId;
        this.apptId = apptId;
        this.isActive = isActive;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getApptId() { return apptId; }
    public void setApptId(String apptId) { this.apptId = apptId; }

    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    @Override
    public String toString() {
        return id + ", " +
               patientId + ", " +
               apptId + ", " +
               isActive;
    }
}
