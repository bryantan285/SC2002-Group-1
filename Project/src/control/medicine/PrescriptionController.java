package control.medicine;

import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
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

    public void createPrescription(String id, String patientId, String apptId, boolean isActive) {
        Prescription newPrescription = new Prescription(id, patientId, apptId, isActive);
        prescriptionRepository.add(newPrescription);
        System.out.println("Prescription created successfully: " + id);
    }

    public void updatePrescriptionStatus(String prescriptionId, boolean isActive) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        if (prescription != null) {
            prescription.setIsActive(isActive);
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Prescription not found.");
        }
    }

    public void cancelPrescription(String prescriptionId) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        if (prescription != null && prescription.getIsActive()) {
            prescription.setIsActive(false);
            System.out.println("Prescription canceled successfully.");
        } else {
            System.out.println("Prescription is already inactive or not found.");
        }
    }

    public void displayPrescriptionDetails(String prescriptionId) {
        Prescription prescription = getPrescriptionById(prescriptionId);
        if (prescription != null) {
            System.out.println("Prescription ID: " + prescription.getId());
            System.out.println("Patient ID: " + prescription.getPatientId());
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
}
