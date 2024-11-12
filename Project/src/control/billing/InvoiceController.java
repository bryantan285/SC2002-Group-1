package control.billing;

import control.medicine.MedicineController;
import control.medicine.PrescriptionController;
import entity.billing.Invoice;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import interfaces.control.ISavable;
import java.time.LocalDateTime;
import java.util.List;
import repository.billing.InvoiceRepository;

public class InvoiceController implements ISavable {

    private final InvoiceRepository invoiceRepository;
    private final PrescriptionController prescriptionController;
    private final MedicineController medicineController;

    public InvoiceController() {
        this.invoiceRepository = InvoiceRepository.getInstance();
        this.prescriptionController = new PrescriptionController();
        this.medicineController = new MedicineController();
    }

    @Override
    public void save() {
        invoiceRepository.save();
    }

    public void createInvoice(String customerId, String prescriptionId, double taxRate) {
        Prescription prescription = prescriptionController.getPrescriptionById(prescriptionId);
        List<PrescriptionItem> prescriptionItems = prescriptionController.getPrescriptionItems(prescription);
        if (prescriptionItems.isEmpty()) {
            System.out.println("No items found for prescription ID: " + prescriptionId);
            return;
        }

        double totalAmount = calculateTotalAmount(prescriptionItems);
        double taxAmount = totalAmount * taxRate;
        double totalPayable = totalAmount + taxAmount;

        Invoice newInvoice = new Invoice(
                invoiceRepository.getNextClassId(),
                customerId,
                prescriptionId,
                totalAmount,
                taxAmount,
                totalPayable,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30)
        );

        invoiceRepository.add(newInvoice);
        save();
        System.out.println("Invoice created successfully: " + newInvoice.getId());
    }

    private double calculateTotalAmount(List<PrescriptionItem> items) {
        double totalAmount = 0.0;

        for (PrescriptionItem item : items) {
            Medicine medicine = medicineController.getMedicineById(item.getMedicineId());
            if (medicine != null) {
                double unitCost = medicine.getUnitCost();
                totalAmount += unitCost * item.getQuantity();
            } else {
                System.out.println("Medicine not found for ID: " + item.getMedicineId());
            }
        }

        return totalAmount;
    }

    public Invoice getInvoiceById(String invoiceId) {
        return invoiceRepository.findByField("invoiceId", invoiceId).stream().findFirst().orElse(null);
    }

    public Boolean markAsPaid(Invoice invoice) {
        if (invoice == null) {
            return null;
        }

        if (invoice.getStatus() == Invoice.InvoiceStatus.PAID) {
            System.out.println("Invoice is already marked as paid.");
            return true;
        }

        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        save();
        return true;
    }

    public Boolean cancelInvoice(String invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice == null) {
            return null;
        }

        if (invoice.getStatus() == Invoice.InvoiceStatus.CANCELED) {
            return true;
        }

        invoice.setStatus(Invoice.InvoiceStatus.CANCELED);
        save();
        return true;
    }

    public void listOverdueInvoices() {
        List<Invoice> invoices = invoiceRepository.toList();
        boolean foundOverdue = false;

        for (Invoice invoice : invoices) {
            if (invoice.getStatus() != Invoice.InvoiceStatus.PAID && invoice.getDueDate().isBefore(LocalDateTime.now())) {
                System.out.println(invoice);
                foundOverdue = true;
            }
        }

        if (!foundOverdue) {
            System.out.println("No overdue invoices found.");
        }
    }

    public Boolean updateDueDate(Invoice invoice, LocalDateTime newDueDate) {
        if (invoice == null) {
            return null;
        }

        invoice.setDueDate(newDueDate);
        save();
        return true;
    }

    public void displayInvoiceDetails(String invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice == null) {
            System.out.println("Invoice not found: " + invoiceId);
            return;
        }

        System.out.println("Invoice ID: " + invoice.getId());
        System.out.println("Customer ID: " + invoice.getCustomerId());
        System.out.println("Prescription ID: " + invoice.getPrescriptionId());
        System.out.println("Total Amount: " + invoice.getTotalAmount());
        System.out.println("Tax Amount: " + invoice.getTaxAmount());
        System.out.println("Total Payable: " + invoice.getTotalPayable());
        System.out.println("Issue Date: " + invoice.getIssueDate());
        System.out.println("Due Date: " + invoice.getDueDate());
        System.out.println("Status: " + invoice.getStatus());
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.toList();
    }

    public Boolean isInvoiceOverdue(Invoice invoice) {
        if (invoice == null) {
            return null;
        }

        return invoice.getDueDate().isBefore(LocalDateTime.now()) && invoice.getStatus() != Invoice.InvoiceStatus.PAID;
    }
}
