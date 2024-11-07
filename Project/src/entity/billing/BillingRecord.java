package entity.billing;
import java.util.Date;

public class BillingRecord {

    protected enum billingStatus {
        PAID,
        PARTIAL,
        UNPAID
    }
    private String patientId;
    private double balance;
    private Date date;
    private billingStatus status;

    public String getPatientId() {
        return patientId;
    }

    public double getBalance() {
        return balance;
    }

    public Date getDate() {
        return date;
    }

    public billingStatus getStatus() {
        return status;
    }

    public void updateStatus(billingStatus update) {
        status = update;
    }    
}