package control.user;

// import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.medicine.PrescriptionController;
import control.medicine.PrescriptionItemController;
import control.request.MedicineRequestController;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.medicine.PrescriptionItem;
import entity.user.HospitalStaff;
import entity.user.Pharmacist;
import java.util.List;

public class PharmacistController {
    // private final AppointmentController appointmentController;
    private final PrescriptionController prescriptionController;
    private final HospitalStaffController hospitalStaffController;
    private final PrescriptionItemController prescriptionItemController;
    private final MedicineController medicineController;
    private final MedicineRequestController medicineRequestController;
    private Pharmacist currentPH;

    public PharmacistController() {
        // this.appointmentController = new AppointmentController();
        this.prescriptionController = new PrescriptionController();
        this.hospitalStaffController = new HospitalStaffController();
        this.prescriptionItemController = new PrescriptionItemController();
        this.medicineController = new MedicineController();
        this.medicineRequestController = new MedicineRequestController();
        this.currentPH = null;
    }

    public void setCurrentPharmacist(String pharId) {
        HospitalStaff staff = hospitalStaffController.findById(pharId);
        if (staff instanceof Pharmacist pharmacist) {
            this.currentPH = pharmacist;
        } else {
            this.currentPH = null;
        }
    }

    public List<Prescription> getActivePrescriptions() {
        return prescriptionController.getActivePrescriptions();
    }

    public List<PrescriptionItem> getPrescriptionItems(Prescription prescription) {
        return prescriptionController.getPrescriptionItems(prescription);
    }

    public Boolean dispensePrescriptionItem(PrescriptionItem item) {
        if (prescriptionItemController.dispensePrescriptionItem(item)) {
            Prescription prescription = prescriptionController.getPrescriptionById(item.getPrescriptionId());
            prescriptionController.checkCompleted(prescription);
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
