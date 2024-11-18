package entity.billing;

import entity.EntityObject;
import java.time.LocalDateTime;
import utility.DateFormat;

/**
 * Represents an invoice with details such as customer ID, prescription ID, total amount, tax,
 * issue date, and status.
 */
public class Invoice extends EntityObject {

    /**
     * Enum representing the status of the invoice.
     */
    public enum InvoiceStatus {
        PENDING, PAID, PARTIAL, CANCELED
    }


    private String id;
    private String customerId;
    private String apptId;
    private double serviceFee;
    private double totalAmount;
    private double taxRate;
    private double balance;
    private double currentPaid;
    private double totalPayable;
    private LocalDateTime issueDate;
    private InvoiceStatus status;

    /**
     * Default constructor for Invoice.
     */
    public Invoice() {
    }

    /**
     * Constructs an Invoice with specified details.
     *
     * @param invoiceId      The ID of the invoice.
     * @param customerId     The ID of the customer.
     * @param apptId         The ID of the appointment.
     * @param serviceFee     The service fee of the invoice.
     * @param totalAmount    The total amount of the invoice.
     * @param taxRate        The tax rate applied to the total amount.
     * @param issueDate      The issue date of the invoice.
     */
    public Invoice(String invoiceId, String customerId, String apptId, double serviceFee, double totalAmount, double taxRate, LocalDateTime issueDate) {
        this.id = invoiceId;
        this.customerId = customerId;
        this.apptId = apptId;
        this.serviceFee = serviceFee;
        this.totalAmount = totalAmount + serviceFee;
        this.taxRate = taxRate;
        this.totalPayable = this.totalAmount * (1 + taxRate);
        this.balance = serviceFee * (1+taxRate);
        this.currentPaid = 0.0;
        this.issueDate = issueDate;
        this.status = InvoiceStatus.PENDING;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getCurrentPaid() {
        return currentPaid;
    }

    public void setCurrentPaid(double paid) {
        this.currentPaid = paid;
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

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public String getapptIdId() {
        return apptId;
    }

    public void setapptIdId(String apptId) {
        this.apptId = apptId;
    }

    /**
     * Returns a string representation of the invoice.
     *
     * @return A formatted string containing the invoice details.
     */
    @Override
    public String toString() {
        return "ID: " + id + "\n" +
               "Appointment ID: " + apptId + "\n" +
               "Service Fee: $" + serviceFee + "\n" +
               "Total Amount: $" + totalAmount + "\n" +
               "Total Payable: $" + totalPayable + "\n" +
               "Current Balance: $" + balance + "\n" +
               "Current Paid: $" + currentPaid + "\n" +
               "Issue Date: " + DateFormat.format(issueDate) + "\n" +
               "Status: " + status;
    }
}
