package entity.medicine;

import entity.EntityObject;

public class PrescriptionItem extends EntityObject {
    private String id;
    private String prescriptionId;
    private String medicineId;
    private String medicineName;
    private int totalDoses;
    private int dosesTaken;
    private int dosesPerDay;
    private int quantity;
    private boolean isActive;

    public PrescriptionItem() {

    }

    // Constructor
    public PrescriptionItem(String id, String prescriptionId, String medicineId, String medicineName, int totalDoses, int dosesPerDay, int quantity) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.totalDoses = totalDoses;
        this.dosesTaken = 0; // Initialized to 0
        this.dosesPerDay = dosesPerDay;
        this.quantity = quantity;
        this.isActive = true; // Active by default
    }

    // Getter and Setter methods
    @Override
    public String getId() { 
        return id; 
    }
    public void setId(String id) {
        this.id = id; 
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    // toString() method
    @Override
    public String toString() {
        return id + ", " +
               prescriptionId + ", " +
               medicineId + ", " +
               medicineName + ", " +
               totalDoses + ", " +
               dosesTaken + ", " +
               dosesPerDay + ", " +
               quantity + ", " +
               isActive;
    }
}
