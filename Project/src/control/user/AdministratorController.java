package control.user;

import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.request.MedicineRequestController;
import entity.appointment.Appointment;
import entity.medicine.Medicine;
import entity.user.Administrator;
import entity.user.HospitalStaff;
import interfaces.control.IController;
import java.util.List;
import utility.FieldHandler;

public class AdministratorController implements IController {
    public static void main(String[] args) {
        String adminId = "A001";
        String reqId = "MREQ001";
        AdministratorController ac = new AdministratorController();
        ac.setCurrentAdmin(adminId);
        ac.approveReplenishmentReq(reqId);
    }

    private final AppointmentController appointmentController;
    private final MedicineController medicineController;
    private final MedicineRequestController medicineRequestController;
    private final HospitalStaffController hospitalStaffController;
    private Administrator currentAdmin;

    public AdministratorController() {
        this.appointmentController = new AppointmentController();
        this.medicineController = new MedicineController();
        this.medicineRequestController = new MedicineRequestController();
        this.hospitalStaffController = new HospitalStaffController();
        this.currentAdmin = null;
    }

    public void setCurrentAdmin(String adminId) {
        HospitalStaff staff = hospitalStaffController.findById(adminId);
        if (staff instanceof Administrator) {
            this.currentAdmin = (Administrator) staff;
        } else {
            this.currentAdmin = null;
        }
    }

    @Override
    public void save() {
        // No implementation
    }

    // Staff
    
    public List<HospitalStaff> getHospitalStaff() {
        return hospitalStaffController.getAllStaff();
    }

    public boolean addStaff(String name, String gender, HospitalStaff.Role role, int age) {
        return hospitalStaffController.addStaff(name, gender, role, age);
    }

    public boolean removeStaff(String staffId) {
        return hospitalStaffController.removeStaff(staffId);
    }

    public List<HospitalStaff> filterStaffBy(String field, String value) {
        return hospitalStaffController.findByField(field, value);
    }

    public List<String> getFields() {
        return FieldHandler.getFieldNames(HospitalStaff.class);
    }

    // Appointment

    public List<Appointment> getAllAppointments() { 
        return appointmentController.getAllAppts();
    }

    public Appointment getAppointment(String apptId) {
        return appointmentController.getAppt(apptId);
    }

    // Inventory

    public List<Medicine> getMedInventory() { 
        return medicineController.getAllMedicines();
    }

    public Medicine getMed(String medicineId) {
        return medicineController.getMedicineById(medicineId);
    }

    public void modifyMedStockAlert(String medicineId, int newLevel) {
        medicineController.setLowStockThreshold(medicineId, newLevel);
    }

    public void addMedStock(String medicineId, int amount) {
        medicineController.incMedStock(medicineId, amount);
    }

    public void removeMedStock(String medicineId, int amount) {
        medicineController.decMedStock(medicineId, amount);
    }

    // Request

    public void approveReplenishmentReq(String requestId) {
        medicineRequestController.approveReplenishmentRequest(currentAdmin.getId(), requestId);
    }

    public void rejectReplenishmentReq(String requestId) {
        medicineRequestController.rejectReplenishmentRequest(currentAdmin.getId(), requestId);
    }
}
