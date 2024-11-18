package control.billing;

import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.prescription.PrescriptionController;
import control.prescription.PrescriptionItemController;
import entity.appointment.Appointment;
import entity.billing.Invoice;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
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
    public static void createInvoice(String customerId, String apptId, double taxRate) throws InvalidInputException, EntityNotFoundException {
        if (customerId == null || customerId.isEmpty()) {
            throw new InvalidInputException("Customer ID cannot be null or empty.");
        }
        if (taxRate < 0) {
            throw new InvalidInputException("Tax rate cannot be negative.");
        }
        Appointment appt = AppointmentController.getAppt(apptId);
        Prescription prescription = null;
        try {
            prescription = PrescriptionController.getPrescriptionByAppt(appt);
        } catch (NoSuchElementException e) {
            prescription = null;
        }
        double totalAmount;
        if (prescription == null) {
            totalAmount = 0.0;
        } else {
            List<PrescriptionItem> prescriptionItems = PrescriptionController.getPrescriptionItems(prescription);
            totalAmount = calculateTotalAmount(prescriptionItems);
        }

        Invoice newInvoice = new Invoice(
                invoiceRepository.getNextClassId(),
                customerId,
                apptId,
                30.0,
                totalAmount,
                taxRate,
                LocalDateTime.now()
        );

        invoiceRepository.add(newInvoice);
        invoiceRepository.save();
    }
    
    public static void recalculateInvoiceCost(String apptId) throws InvalidInputException, EntityNotFoundException {
    if (apptId == null || apptId.isEmpty()) {
        throw new InvalidInputException("Prescription ID cannot be null or empty.");
    }

    Invoice invoice = InvoiceController.getInvoiceByAppt(apptId);
    if (invoice == null) {
        throw new EntityNotFoundException("Invoice of", apptId);
    }
    Appointment appt = AppointmentController.getAppt(apptId);
    try {
        Prescription prescription = PrescriptionController.getPrescriptionByAppt(appt);
        try {
            List<PrescriptionItem> items = PrescriptionItemController.getPrescriptionItems(prescription);
            
            double totalCost = 0.0;
            for (PrescriptionItem item : items) {
                Medicine med = MedicineController.getMedicineById(item.getMedicineId());
                if (med != null) {
                    totalCost += item.getQuantity() * med.getUnitCost();
                }
            }
            invoice.setTotalAmount(totalCost + invoice.getServiceFee());
            invoice.setTotalPayable(invoice.getTotalAmount()*(1+invoice.getTaxRate()));
            if (invoice.getTotalPayable() - invoice.getCurrentPaid() < 0) {
                invoice.setCurrentPaid(invoice.getCurrentPaid() - invoice.getTotalPayable());
                invoice.setBalance(0);
            } else {
                invoice.setBalance(invoice.getTotalPayable() - invoice.getCurrentPaid());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    } catch (EntityNotFoundException e) {
        PrescriptionController.createPrescription(appt.getId());
    }
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
    
    public static void incBalance(Invoice inv, double cost) throws InvalidInputException {
        if (cost < 0) {
            throw new InvalidInputException("Cost cannot be negative.");
        }
        double newBalance = inv.getBalance() + cost;
        inv.setBalance(newBalance);
        invoiceRepository.save();
    }
    
    public static void payBalance(Invoice inv, double payment) throws InvalidInputException {
        if (payment <= 0) {
            throw new InvalidInputException("Payment must be greater than zero.");
        }
        double currentBalance = inv.getBalance();
        if (payment > currentBalance) {
            throw new InvalidInputException("Payment exceeds the current balance.");
        }
        inv.setBalance(currentBalance - payment);
        inv.setCurrentPaid(inv.getCurrentPaid() + payment);
        if (inv.getCurrentPaid() == inv.getTotalPayable()) inv.setStatus(Invoice.InvoiceStatus.PAID);
        else inv.setStatus(Invoice.InvoiceStatus.PARTIAL);
        invoiceRepository.save();
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

    public static List<Invoice> getInvoiceByCustomer(String customerId) {
        List<Invoice> list = invoiceRepository.findByField("customerId", customerId);
        list.sort(Comparator.comparing(Invoice::getId));
        return list;
    }

    /**
     * Retrieves a list of all invoices.
     *
     * @return A list of all invoices.
     */
    public static List<Invoice> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.toList();
        invoices.sort(Comparator.comparing(Invoice::getId));
        return invoices;
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

    public static Invoice getInvoiceByAppt(String apptId) {
        Invoice inv = invoiceRepository.findByField("apptId", apptId).getFirst();
        return inv;
    }
}
