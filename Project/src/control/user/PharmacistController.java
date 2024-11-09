package control.user;

import control.appointment.AppointmentController;
import control.medicine.PrescriptionController;
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
    private Pharmacist currentPH;

    public PharmacistController() {
        this.appointmentController = new AppointmentController();
        this.prescriptionController = new PrescriptionController();
        this.hospitalStaffController = new HospitalStaffController();
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

    
}
