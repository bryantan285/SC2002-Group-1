package control.prescription;

import control.medicine.MedicineController;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.util.List;
import repository.medicine.PrescriptionItemRepository;

public class PrescriptionItemController {

    private static final PrescriptionItemRepository prescriptionItemRepository = PrescriptionItemRepository.getInstance();

    public static void createPrescriptionItem(String prescriptionId, String medicineId, int quantity, String notes) throws InvalidInputException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new InvalidInputException("Prescription ID cannot be null or empty.");
        }
        if (medicineId == null || medicineId.isEmpty()) {
            throw new InvalidInputException("Medicine ID cannot be null or empty.");
        }
        if (quantity <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }

        PrescriptionItem item = new PrescriptionItem();
        item.setId(prescriptionItemRepository.getNextClassId());
        item.setPrescriptionId(prescriptionId);
        item.setMedicineId(medicineId);
        item.setQuantity(quantity);
        item.setStatus(PrescriptionItem.ItemStatus.PENDING);
        item.setNotes(notes);
        prescriptionItemRepository.add(item);
        prescriptionItemRepository.save();
    }

    public static PrescriptionItem getPrescriptionItemById(String itemId) throws EntityNotFoundException, InvalidInputException {
        if (itemId == null || itemId.isEmpty()) {
            throw new InvalidInputException("Item ID cannot be null or empty.");
        }

        PrescriptionItem item = prescriptionItemRepository.findByField("id", itemId).stream().findFirst().orElse(null);
        if (item == null) {
            throw new EntityNotFoundException("PrescriptionItem", itemId);
        }
        return item;
    }

    public static List<PrescriptionItem> getPrescriptionItems(Prescription prescription) throws InvalidInputException {
        if (prescription == null) {
            throw new InvalidInputException("Prescription cannot be null.");
        }
        return prescriptionItemRepository.findByField("prescriptionId", prescription.getId());
    }

    public static Boolean dispensePrescriptionItem(PrescriptionItem item) throws InvalidInputException, EntityNotFoundException {
        if (item == null) {
            throw new InvalidInputException("Prescription item cannot be null.");
        }

        Medicine med = MedicineController.getMedicineById(item.getMedicineId());
        if (med == null) {
            throw new EntityNotFoundException("Medicine", item.getMedicineId());
        }

        if (MedicineController.decMedStock(med, item.getQuantity())) {
            item.setStatus(PrescriptionItem.ItemStatus.DISPENSED);
            prescriptionItemRepository.save();
            return true;
        }
        return false;
    }

    public static List<PrescriptionItem> getPendingPrescriptionItems(String prescriptionId) throws InvalidInputException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new InvalidInputException("Prescription ID cannot be null or empty.");
        }

        List<PrescriptionItem> items = prescriptionItemRepository.findByField("prescriptionId", prescriptionId);
        return items.stream()
                .filter(item -> item.getStatus() == PrescriptionItem.ItemStatus.PENDING)
                .toList();
    }

    public static void deletePrescriptionItem(PrescriptionItem item) throws EntityNotFoundException {
        if (item == null) {
            throw new EntityNotFoundException("PrescriptionItem", "null");
        }
        prescriptionItemRepository.remove(item);
        prescriptionItemRepository.save();
    }
}
