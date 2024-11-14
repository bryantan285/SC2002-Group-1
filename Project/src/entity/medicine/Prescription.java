package entity.medicine;

import entity.EntityObject;

public class Prescription extends EntityObject {
    private String id;
    private String apptId;
    private boolean isActive;

    public Prescription() {
        
    }

    // Constructor
    public Prescription(String id, String apptId, boolean isActive) {
        this.id = id;
        this.apptId = apptId;
        this.isActive = isActive;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getApptId() { 
        return apptId; 
    }
    public void setApptId(String apptId) { 
        this.apptId = apptId; 
    }

    public boolean getIsActive() { 
        return isActive; 
    }
    public void setIsActive(boolean isActive) { 
        this.isActive = isActive; 
    }

    @Override
    public String toString() {
        return "Prescription ID: " + id + "\n" +
               "Appointment ID: " + apptId + "\n" +
               "Active: " + isActive;
    }
    
}
