package entity.billing;

import entity.EntityObject;
import java.time.LocalDateTime;

public class Invoice extends EntityObject {
    public enum InvoiceStatus {
        PENDING, PAID, OVERDUE, CANCELED 
    }
    private String id;
    private String customerId;
    private String prescriptionId;
    private double totalAmount;
    private double taxAmount;
    private double totalPayable;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private InvoiceStatus status;

    public Invoice() {

    }
    
    public Invoice(String invoiceId, String customerId, String prescriptionId, double totalAmount, double taxAmount,
            double totalPayable, LocalDateTime issueDate, LocalDateTime dueDate) {
        this.id = invoiceId;
        this.customerId = customerId;
        this.prescriptionId = prescriptionId;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.totalPayable = totalPayable;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = InvoiceStatus.PENDING;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public double getTaxAmount() {
        return taxAmount;
    }
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
    public double getTotalPayable() {
        return totalPayable;
    }
    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    @Override
    public String getId() {
        return id;
    }
    public void setId(String invoiceId) {
        this.id = invoiceId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public LocalDateTime getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }
    public LocalDateTime getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    public InvoiceStatus getStatus() {
        return status;
    }
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
    
    public boolean checkDue() {
        return issueDate.isAfter(dueDate);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Customer ID: " + customerId + "\n" +
               "Prescription ID: " + prescriptionId + "\n" +
               "Total Amount: $" + totalAmount + "\n" +
               "Tax Amount: $" + taxAmount + "\n" +
               "Total Payable: $" + totalPayable + "\n" +
               "Issue Date: " + issueDate + "\n" +
               "Due Date: " + dueDate + "\n" +
               "Status: " + status;
    }
}
