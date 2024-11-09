package entity.request;

import java.time.LocalDateTime;
import utility.DateFormat;

public class MedicineRequest extends Request {
    private String medicineId;
    private int quantity;

    public MedicineRequest() {
        super();
    }

    public MedicineRequest(String id, String requestorId, String approverId, STATUS status, LocalDateTime timeCreated, LocalDateTime timeModified, String medicineId, int quantity) {
        super(id, requestorId, approverId, status, timeCreated, timeModified);
        this.medicineId = medicineId;
        this.quantity = quantity;
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
    
    @Override
    public String toString() {
        return super.getId() + ", " +
               super.getRequestorId() + ", " +
               super.getApproverId() + ", " +
               super.getStatus() + ", " +
               DateFormat.formatWithTime(super.getTimeCreated()) + ", " +
               DateFormat.formatWithTime(super.getTimeModified()) + ", " +
               medicineId + ", " +
               quantity;
    }

    
}
