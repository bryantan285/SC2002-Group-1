package control.user;

import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.medicine.PrescriptionController;
import control.medicine.PrescriptionItemController;
import control.request.MedicineRequestController;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import entity.user.HospitalStaff;
import entity.user.Pharmacist;
import interfaces.control.IController;
import java.util.List;

public class PharmacistController implements IController {
    private final AppointmentController appointmentController;
    private final PrescriptionController prescriptionController;
    private final HospitalStaffController hospitalStaffController;
    private final PrescriptionItemController prescriptionItemController;
    private final MedicineController medicineController;
    private final MedicineRequestController medicineRequestController;
    private Pharmacist currentPH;

    public PharmacistController() {
        this.appointmentController = new AppointmentController();
        this.prescriptionController = new PrescriptionController();
        this.hospitalStaffController = new HospitalStaffController();
        this.prescriptionItemController = new PrescriptionItemController();
        this.medicineController = new MedicineController();
        this.medicineRequestController = new MedicineRequestController();
        this.currentPH = null;
    }

    @Override
    public void save() {
        // No implementation
    }

    public void setCurrentPharmacist(String pharId) {
        HospitalStaff staff = hospitalStaffController.findById(pharId);
        if (staff instanceof Pharmacist) {
            this.currentPH = (Pharmacist) staff;
        } else {
            this.currentPH = null;
        }
    }

    public List<Prescription> getActivePrescriptions() {
        return prescriptionController.getActivePrescriptions();
    }

    public List<PrescriptionItem> getPrescriptionItems(String prescriptionId) {
        return prescriptionController.getPrescriptionItems(prescriptionId);
    }

    public Boolean dispensePrescriptionItem(String itemId) {
        if (prescriptionItemController.dispensePrescriptionItem(itemId)) {
            PrescriptionItem item = prescriptionItemController.getPrescriptionItemById(itemId);
            prescriptionController.checkCompleted(item.getPrescriptionId());
            return true;
        } else {
            return false;
        }
    }

    public List<Medicine> getAllMedicine() {
        return medicineController.getAllMedicines();
    }

    public String createReplenishmentRequest(String medicineId, int amount) {
        return medicineRequestController.createReplenishmentRequest(currentPH.getId(), medicineId, amount);
    }
}
