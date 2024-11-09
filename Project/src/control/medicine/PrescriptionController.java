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

    public void createPrescription(String id, String apptId, boolean isActive) {
        Prescription newPrescription = new Prescription(id, apptId, isActive);
        prescriptionRepository.add(newPrescription);
        save();
    }

    public void updatePrescriptionStatus(String prescriptionId, boolean isActive) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        if (prescription != null) {
            prescription.setIsActive(isActive);
        } else {
        }
    }

    public void cancelPrescription(String prescriptionId) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        if (prescription != null && prescription.getIsActive()) {
            prescription.setIsActive(false);
        } else {
        }
    }

    public void displayPrescriptionDetails(String prescriptionId) {
        Prescription prescription = getPrescriptionById(prescriptionId);
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
        return prescriptionRepository.findByField("id", prescriptionId).stream().findFirst().orElse(null);
    }

    public void deletePrescription(String prescriptionId) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        if (prescription != null) {
            prescriptionRepository.remove(prescription);
            System.out.println("Prescription deleted successfully.");
        } else {
            System.out.println("Prescription not found.");
        }
    }

    public List<PrescriptionItem> getPrescriptionItems(String prescriptionId) {
        return prescriptionItemController.getPrescriptionItems(prescriptionId);
    }

    public List<Prescription> getActivePrescriptions() {
        return prescriptionRepository.findByField("isActive", true);
    }

    // Returns true if none of the item are Pending, active should be false after this
    public Boolean checkCompleted(String prescriptionId) {
        List<PrescriptionItem> itemList = getPrescriptionItems(prescriptionId);
        return itemList.stream().noneMatch(item -> item.getStatus() == ItemStatus.PENDING);
    }
}
