package control.medicine;

import entity.medicine.PrescriptionItem;
import entity.medicine.PrescriptionItem.ItemStatus;
import interfaces.control.IController;
import java.util.List;
import repository.medicine.PrescriptionItemRepository;

public class PrescriptionItemController implements IController {
    private final PrescriptionItemRepository prescriptionItemRepository;
    private final MedicineController medicineController;

    public PrescriptionItemController() {
        this.prescriptionItemRepository = PrescriptionItemRepository.getInstance();
        this.medicineController = new MedicineController();
    }

    @Override
    public void save() {
        prescriptionItemRepository.save();
    }

    public void displayPrescriptionItemDetails(String itemId) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        System.out.println("Item ID: " + item.getId());
        System.out.println("Prescription ID: " + item.getPrescriptionId());
        System.out.println("Medicine ID: " + item.getMedicineId());
        System.out.println("Status: " + item.getStatus());
    }

    public PrescriptionItem getPrescriptionItemById(String itemId) {
        return prescriptionItemRepository.findByField("id", itemId).stream().findFirst().orElse(null);
    }

    public List<PrescriptionItem> getPrescriptionItems(String prescriptionId) {
        return prescriptionItemRepository.findByField("prescriptionId", prescriptionId);
    }    

    public void deletePrescriptionItem(String itemId) {
       PrescriptionItem item = getPrescriptionItemById(itemId);
       if (item == null) {
           System.out.println("Prescription item not found.");
           return;
       }

       prescriptionItemRepository.remove(item);
       save();
    }

    public void createPrescriptionItem(String prescriptionId, String medicineId, int quantity, String notes) {
        PrescriptionItem item = new PrescriptionItem();
        item.setId(prescriptionItemRepository.getNextClassId());
        item.setPrescriptionId(prescriptionId);
        item.setMedicineId(medicineId);
        item.setQuantity(quantity);
        item.setStatus(ItemStatus.PENDING);
        item.setNotes(notes);
        prescriptionItemRepository.add(item);
        save();
    }

    public Boolean dispensePrescriptionItem(String itemId) {
        PrescriptionItem item = getPrescriptionItemById(itemId);
        if (item == null) return null;
        if (medicineController.decMedStock(itemId, item.getQuantity())) {
            item.setStatus(ItemStatus.DISPENSED);
            save();
            return true;
        } else {
            return false;
        }
    }
}
