package entity.medicine;

import entity.EntityObject;

public class PrescriptionItem extends EntityObject {
    private String id;
    private String prescriptionId;
    private String medicineId;
    private int quantity;
    public enum ItemStatus {PENDING, CANCELLED, DISPENSED};
    private ItemStatus status;
    private String notes;

    public PrescriptionItem() {}

    // Constructor
    public PrescriptionItem(String id, String prescriptionId, String medicineId, int quantity, ItemStatus status, String notes) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.status = status;
        this.notes = notes;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return id + ", " +
               prescriptionId + ", " +
               medicineId + ", " +
               quantity + ", " +
               status;
    }
}
