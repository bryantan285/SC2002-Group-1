package control.billing;

import control.medicine.MedicineController;
import control.prescription.PrescriptionController;
import entity.billing.Invoice;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.time.LocalDateTime;
import java.util.List;
import repository.billing.InvoiceRepository;

/**
 * Controller for managing invoices.
 */
public class InvoiceController {

    private static final InvoiceRepository invoiceRepository = InvoiceRepository.getInstance();

    /**
     * Creates a new invoice based on the specified prescription.
     *
     * @param customerId     The ID of the customer.
     * @param prescriptionId The ID of the prescription.
     * @param taxRate        The tax rate to apply.
     * @throws InvalidInputException   If any input is invalid.
     * @throws EntityNotFoundException If the prescription or items are not found.
     */
    public static void createInvoice(String customerId, String prescriptionId, double taxRate) throws InvalidInputException, EntityNotFoundException {
        if (customerId == null || customerId.isEmpty()) {
            throw new InvalidInputException("Customer ID cannot be null or empty.");
        }
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new InvalidInputException("Prescription ID cannot be null or empty.");
        }
        if (taxRate < 0) {
            throw new InvalidInputException("Tax rate cannot be negative.");
        }

        Prescription prescription = PrescriptionController.getPrescriptionById(prescriptionId);
        List<PrescriptionItem> prescriptionItems = PrescriptionController.getPrescriptionItems(prescription);

        if (prescriptionItems.isEmpty()) {
            throw new InvalidInputException("No items found for prescription ID: " + prescriptionId);
        }

        double totalAmount = calculateTotalAmount(prescriptionItems);
        double taxAmount = totalAmount * taxRate;
        double totalPayable = totalAmount + taxAmount;

        Invoice newInvoice = new Invoice(
                invoiceRepository.getNextClassId(),
                customerId,
                prescriptionId,
                totalAmount,
                totalPayable,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30)
        );

        invoiceRepository.add(newInvoice);
        invoiceRepository.save();
    }

    private static double calculateTotalAmount(List<PrescriptionItem> items) throws EntityNotFoundException, InvalidInputException {
        double totalAmount = 0.0;

        for (PrescriptionItem item : items) {
            Medicine medicine = MedicineController.getMedicineById(item.getMedicineId());
            if (medicine == null) {
                throw new EntityNotFoundException("Medicine", item.getMedicineId());
            }
            totalAmount += medicine.getUnitCost() * item.getQuantity();
        }

        return totalAmount;
    }

    /**
     * Retrieves an Invoice by its ID.
     *
     * @param invoiceId The ID of the invoice.
     * @return The Invoice object.
     * @throws InvalidInputException   If the invoice ID is invalid.
     * @throws EntityNotFoundException If the invoice is not found.
     */
    public static Invoice getInvoiceById(String invoiceId) throws InvalidInputException, EntityNotFoundException {
        if (invoiceId == null || invoiceId.isEmpty()) {
            throw new InvalidInputException("Invoice ID cannot be null or empty.");
        }

        Invoice invoice = invoiceRepository.findByField("invoiceId", invoiceId).stream().findFirst().orElse(null);
        if (invoice == null) {
            throw new EntityNotFoundException("Invoice", invoiceId);
        }
        return invoice;
    }

    /**
     * Retrieves a list of all invoices.
     *
     * @return A list of all invoices.
     */
    public static List<Invoice> getAllInvoices() {
        return invoiceRepository.toList();
    }

    /**
     * Displays the details of an invoice.
     *
     * @param invoiceId The ID of the invoice to display.
     * @throws InvalidInputException   If the invoice ID is invalid.
     * @throws EntityNotFoundException If the invoice is not found.
     */
    public static void displayInvoiceDetails(String invoiceId) throws InvalidInputException, EntityNotFoundException {
        Invoice invoice = getInvoiceById(invoiceId);

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

    /**
     * Lists all overdue invoices.
     */
    public static void listOverdueInvoices() {
        List<Invoice> invoices = invoiceRepository.toList();

        boolean foundOverdue = invoices.stream()
                .filter(invoice -> invoice.getStatus() != Invoice.InvoiceStatus.PAID && invoice.getDueDate().isBefore(LocalDateTime.now()))
                .peek(System.out::println)
                .count() > 0;

        if (!foundOverdue) {
            System.out.println("No overdue invoices found.");
        }
    }

    /**
     * Marks the specified invoice as paid.
     *
     * @param invoice The invoice to mark as paid.
     * @return True if the operation is successful.
     * @throws InvalidInputException If the invoice is null.
     */
    public static Boolean markAsPaid(Invoice invoice) throws InvalidInputException {
        if (invoice == null) {
            throw new InvalidInputException("Invoice cannot be null.");
        }
        if (invoice.getStatus() == Invoice.InvoiceStatus.PAID) {
            return true;
        }

        invoice.setStatus(Invoice.InvoiceStatus.PAID);
        invoiceRepository.save();
        return true;
    }

    /**
     * Cancels the specified invoice.
     *
     * @param invoiceId The ID of the invoice to cancel.
     * @return True if the operation is successful.
     * @throws InvalidInputException   If the invoice ID is invalid.
     * @throws EntityNotFoundException If the invoice is not found.
     */
    public static Boolean cancelInvoice(String invoiceId) throws InvalidInputException, EntityNotFoundException {
        Invoice invoice = getInvoiceById(invoiceId);

        if (invoice.getStatus() == Invoice.InvoiceStatus.CANCELED) {
            return true;
        }

        invoice.setStatus(Invoice.InvoiceStatus.CANCELED);
        invoiceRepository.save();
        return true;
    }

    /**
     * Updates the due date of the specified invoice.
     *
     * @param invoice    The invoice to update.
     * @param newDueDate The new due date.
     * @return True if the update is successful.
     * @throws InvalidInputException If the input is invalid.
     */
    public static Boolean updateDueDate(Invoice invoice, LocalDateTime newDueDate) throws InvalidInputException {
        if (invoice == null) {
            throw new InvalidInputException("Invoice cannot be null.");
        }
        if (newDueDate == null || newDueDate.isBefore(LocalDateTime.now())) {
            throw new InvalidInputException("Due date must be in the future.");
        }

        invoice.setDueDate(newDueDate);
        invoiceRepository.save();
        return true;
    }

    /**
     * Deletes the specified invoice.
     *
     * @param invoiceId The ID of the invoice to delete.
     * @throws InvalidInputException   If the invoice ID is invalid.
     * @throws EntityNotFoundException If the invoice is not found.
     */
    public static void deleteInvoice(String invoiceId) throws InvalidInputException, EntityNotFoundException {
        Invoice invoice = getInvoiceById(invoiceId);
        invoiceRepository.remove(invoice);
        invoiceRepository.save();
    }

    /**
     * Checks if the specified invoice is overdue.
     *
     * @param invoice The invoice to check.
     * @return True if the invoice is overdue, false otherwise.
     * @throws InvalidInputException If the invoice is null.
     */
    public static Boolean isInvoiceOverdue(Invoice invoice) throws InvalidInputException {
        if (invoice == null) {
            throw new InvalidInputException("Invoice cannot be null.");
        }

        return invoice.getDueDate().isBefore(LocalDateTime.now()) && invoice.getStatus() != Invoice.InvoiceStatus.PAID;
    }
}
