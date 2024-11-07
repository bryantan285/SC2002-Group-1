package entity.billing;
public class Invoice {
    private int invoiceNo;
    private BillingRecord billingRecord;
    // Modify UML here, no need patient ID as it exists in billing record
    
    public int getInvoiceNo() {
        return invoiceNo;
    }

    public BillingRecord getBillingRecord() {
        return billingRecord;
    }
}
