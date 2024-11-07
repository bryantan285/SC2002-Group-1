package entity.request;

import java.util.Date;

public class MedicineRequest extends Request {
    private String medicineId;
    private int amount;

    public MedicineRequest() {
        super();
    }

    public MedicineRequest(String id, String requestorId, String approverId, STATUS status, Date timeCreated, Date timeModified, String medicineId, int amount) {
        super(id, requestorId, approverId, status, timeCreated, timeModified);
        this.medicineId = medicineId;
        this.amount = amount;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    
}
