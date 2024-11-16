package entity.billing;

import entity.EntityObject;
import java.time.LocalDateTime;

/**
 * Represents an invoice with details such as customer ID, prescription ID, total amount, tax,
 * issue date, due date, and status.
 */
public class Invoice extends EntityObject {

    /**
     * Enum representing the status of the invoice.
     */
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

    /**
     * Default constructor for Invoice.
     */
    public Invoice() {
    }

    /**
     * Constructs an Invoice with specified details.
     *
     * @param invoiceId       The ID of the invoice.
     * @param customerId      The ID of the customer.
     * @param prescriptionId  The ID of the prescription.
     * @param totalAmount     The total amount of the invoice.
     * @param totalPayable    The total payable amount after tax.
     * @param issueDate       The issue date of the invoice.
     * @param dueDate         The due date of the invoice.
     */
    public Invoice(String invoiceId, String customerId, String prescriptionId, double totalAmount,
                   double totalPayable, LocalDateTime issueDate, LocalDateTime dueDate) {
        this.id = invoiceId;
        this.customerId = customerId;
        this.prescriptionId = prescriptionId;
        this.totalAmount = totalAmount;
        this.taxAmount = 0.09 * totalAmount;
        this.totalPayable = totalPayable;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.status = InvoiceStatus.PENDING;
    }

    /**
     * Returns the total amount of the invoice.
     *
     * @return The total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the invoice.
     *
     * @param totalAmount The total amount to set.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Returns the tax amount of the invoice.
     *
     * @return The tax amount.
     */
    public double getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the tax amount of the invoice.
     *
     * @param taxAmount The tax amount to set.
     */
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * Returns the total payable amount of the invoice.
     *
     * @return The total payable amount.
     */
    public double getTotalPayable() {
        return totalPayable;
    }

    /**
     * Sets the total payable amount of the invoice.
     *
     * @param totalPayable The total payable amount to set.
     */
    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    /**
     * Returns the ID of the invoice.
     *
     * @return The invoice ID.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Sets the ID of the invoice.
     *
     * @param invoiceId The invoice ID to set.
     */
    public void setId(String invoiceId) {
        this.id = invoiceId;
    }

    /**
     * Returns the customer ID associated with the invoice.
     *
     * @return The customer ID.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID associated with the invoice.
     *
     * @param customerId The customer ID to set.
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the issue date of the invoice.
     *
     * @return The issue date.
     */
    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    /**
     * Sets the issue date of the invoice.
     *
     * @param issueDate The issue date to set.
     */
    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * Returns the due date of the invoice.
     *
     * @return The due date.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the invoice.
     *
     * @param dueDate The due date to set.
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Returns the current status of the invoice.
     *
     * @return The invoice status.
     */
    public InvoiceStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of the invoice.
     *
     * @param status The status to set.
     */
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    /**
     * Returns the prescription ID associated with the invoice.
     *
     * @return The prescription ID.
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * Sets the prescription ID associated with the invoice.
     *
     * @param prescriptionId The prescription ID to set.
     */
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    /**
     * Checks if the invoice is overdue by comparing the issue date and due date.
     *
     * @return True if the issue date is after the due date, false otherwise.
     */
    public boolean checkDue() {
        return issueDate.isAfter(dueDate);
    }

    /**
     * Returns a string representation of the invoice.
     *
     * @return A formatted string containing the invoice details.
     */
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
