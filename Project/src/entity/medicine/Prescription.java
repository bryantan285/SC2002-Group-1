package entity.medicine;

import entity.EntityObject;

public class Prescription extends EntityObject {
    private String id;
    private String patientId;
    private String medicineId;
    private int totalDoses;
    private int dosesTaken;
    private int dosesPerDay;
    private boolean isActive;

    // Constructor
    public Prescription(String id, String patientId, String medicineId, int totalDoses, int dosesPerDay, boolean isActive) {
        this.id = id;
        this.patientId = patientId;
        this.medicineId = medicineId;
        this.totalDoses = totalDoses;
        this.dosesTaken = 0; // Initialized to 0 at start
        this.dosesPerDay = dosesPerDay;
        this.isActive = isActive;
    }

    // Main method to manage multiple prescriptions
    public static void main(String[] args) {
        
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getTotalDoses() {
        return totalDoses;
    }

    public void setTotalDoses(int totalDoses) {
        this.totalDoses = totalDoses;
    }

    public int getDosesTaken() {
        return dosesTaken;
    }

    public void setDosesTaken(int dosesTaken) {
        this.dosesTaken = dosesTaken;
    }

    public int getDosesPerDay() {
        return dosesPerDay;
    }

    public void setDosesPerDay(int dosesPerDay) {
        this.dosesPerDay = dosesPerDay;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    @Override
    public String toString() {
        return patientId + ", " + medicineId + ", " + totalDoses + ", " + dosesTaken + ", " + dosesPerDay + ", " + isActive;
    }
}
