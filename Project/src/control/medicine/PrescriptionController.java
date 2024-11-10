package control.medicine;

import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import entity.medicine.PrescriptionItem.ItemStatus;
import java.util.List;
import repository.medicine.PrescriptionRepository;

public class PrescriptionController {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionItemController prescriptionItemController;

    public PrescriptionController() {
        this.prescriptionRepository = PrescriptionRepository.getInstance();
        this.prescriptionItemController = new PrescriptionItemController();
    }

    public void save() {
        prescriptionRepository.save();
    }

    public void createPrescription(String apptId, boolean isActive) {
        Prescription newPrescription = new Prescription(prescriptionRepository.getNextClassId(), apptId, isActive);
        prescriptionRepository.add(newPrescription);
        save();
    }

    public Boolean updatePrescriptionStatus(Prescription prescription, boolean isActive) {
        if (prescription != null) {
            prescription.setIsActive(isActive);
            return isActive;
        } else {
            return null;
        }
    }

    public void cancelPrescription(Prescription prescription) {
        if (prescription != null && prescription.getIsActive()) {
            prescription.setIsActive(false);
        } else {
        }
    }

    public void displayPrescriptionDetails(Prescription prescription) {
        if (prescription != null) {
            System.out.println("Prescription ID: " + prescription.getId());
            System.out.println("Appointment ID: " + prescription.getApptId());
            System.out.println("Active: " + prescription.getIsActive());
        } else {
            System.out.println("Prescription not found.");
        }
    }

    public void listAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.toList();
        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions found.");
        } else {
            System.out.println("Listing all prescriptions:");
            for (Prescription prescription : prescriptions) {
                System.out.println(prescription);
            }
        }
    }

    public Prescription getPrescriptionById(String prescriptionId) {
        return prescriptionRepository.get(prescriptionId);
    }

    public void deletePrescription(Prescription prescription) {
        if (prescription != null) {
            prescriptionRepository.remove(prescription);
            System.out.println("Prescription deleted successfully.");
        } else {
            System.out.println("Prescription not found.");
        }
    }

    public List<PrescriptionItem> getPrescriptionItems(Prescription prescription) {
        return prescriptionItemController.getPrescriptionItems(prescription);
    }

    public List<Prescription> getActivePrescriptions() {
        return prescriptionRepository.findByField("isActive", true);
    }

    // Returns true if none of the item are Pending, active should be false after this
    public Boolean checkCompleted(Prescription prescription) {
        List<PrescriptionItem> itemList = getPrescriptionItems(prescription);
        return itemList.stream().noneMatch(item -> item.getStatus() == ItemStatus.PENDING);
    }
}
