package entity.request;

import java.time.LocalDateTime;
import utility.DateFormat;

public class MedicineRequest extends Request {
    private String medicineId;
    private int amount;

    public MedicineRequest() {
        super();
    }

    public MedicineRequest(String id, String requestorId, String approverId, STATUS status, LocalDateTime timeCreated, LocalDateTime timeModified, String medicineId, int amount) {
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
    
    @Override
    public String toString() {
        return super.getId() + ", " +
               super.getRequestorId() + ", " +
               super.getApproverId() + ", " +
               super.getStatus() + ", " +
               DateFormat.formatWithTime(super.getTimeCreated()) + ", " +
               DateFormat.formatWithTime(super.getTimeModified()) + ", " +
               medicineId + ", " +
               amount;
    }

    
}
