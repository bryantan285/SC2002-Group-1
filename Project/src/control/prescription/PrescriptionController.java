package control.prescription;

import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.util.List;
import repository.medicine.PrescriptionRepository;

public class PrescriptionController {

    private static final PrescriptionRepository prescriptionRepository = PrescriptionRepository.getInstance();

    public static void createPrescription(String apptId, boolean isActive) throws InvalidInputException {
        if (apptId == null || apptId.isEmpty()) {
            throw new InvalidInputException("Appointment ID cannot be null or empty.");
        }

        Prescription newPrescription = new Prescription(prescriptionRepository.getNextClassId(), apptId, isActive);
        prescriptionRepository.add(newPrescription);
        prescriptionRepository.save();
    }

    public static Prescription getPrescriptionById(String prescriptionId) throws EntityNotFoundException, InvalidInputException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new InvalidInputException("Prescription ID cannot be null or empty.");
        }

        Prescription prescription = prescriptionRepository.get(prescriptionId);
        if (prescription == null) {
            throw new EntityNotFoundException("Prescription", prescriptionId);
        }
        return prescription;
    }

    public static List<Prescription> getActivePrescriptions() {
        return prescriptionRepository.findByField("isActive", true);
    }

    public static List<PrescriptionItem> getPrescriptionItems(Prescription prescription) throws InvalidInputException {
        if (prescription == null) {
            throw new InvalidInputException("Prescription cannot be null.");
        }
        return PrescriptionItemController.getPrescriptionItems(prescription);
    }

    public static Boolean updatePrescriptionStatus(Prescription prescription, boolean isActive) throws InvalidInputException {
        if (prescription == null) {
            throw new InvalidInputException("Prescription cannot be null.");
        }
        prescription.setIsActive(isActive);
        prescriptionRepository.save();
        return isActive;
    }

    public static void updatePrescriptionStatusIfCompleted(String prescriptionId) throws InvalidInputException, EntityNotFoundException {
        if (prescriptionId == null || prescriptionId.isEmpty()) {
            throw new InvalidInputException("Prescription ID cannot be null or empty.");
        }
    
        // Retrieve the prescription
        Prescription prescription = getPrescriptionById(prescriptionId);
    
        // Get all prescription items associated with the prescription
        List<PrescriptionItem> itemList = getPrescriptionItems(prescription);
    
        // Check if all items have been dispensed
        boolean allDispensed = itemList.stream().allMatch(item -> item.getStatus() == PrescriptionItem.ItemStatus.DISPENSED);
    
        // If all items are dispensed, set isActive to false
        if (allDispensed) {
            prescription.setIsActive(false);
            prescriptionRepository.save(); // Save the updated prescription to the repository
        }
    }
    

    public static void cancelPrescription(Prescription prescription) throws InvalidInputException {
        if (prescription == null) {
            throw new InvalidInputException("Prescription cannot be null.");
        }
        if (!prescription.getIsActive()) {
            throw new InvalidInputException("Prescription is already inactive.");
        }
        prescription.setIsActive(false);
        prescriptionRepository.save();
    }

    public static Boolean checkCompleted(Prescription prescription) throws InvalidInputException {
        if (prescription == null) {
            throw new InvalidInputException("Prescription cannot be null.");
        }
        List<PrescriptionItem> itemList = PrescriptionItemController.getPrescriptionItems(prescription);
        return itemList.stream().noneMatch(item -> item.getStatus() == PrescriptionItem.ItemStatus.PENDING);
    }

    public static void deletePrescription(Prescription prescription) throws EntityNotFoundException {
        if (prescription == null) {
            throw new EntityNotFoundException("Prescription", "null");
        }
        prescriptionRepository.remove(prescription);
        prescriptionRepository.save();
    }
}
