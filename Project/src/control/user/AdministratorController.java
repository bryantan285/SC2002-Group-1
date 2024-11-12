package control.user;

import control.appointment.AppointmentController;
import control.medicine.MedicineController;
import control.request.MedicineRequestController;
import entity.appointment.Appointment;
import entity.medicine.Medicine;
import entity.request.MedicineRequest;
import entity.user.Administrator;
import entity.user.HospitalStaff;
import java.util.List;
import utility.FieldHandler;

public class AdministratorController {
    public static void main(String[] args) {
        String adminId = "A001";
        String reqId = "MREQ001";
        AdministratorController ac = new AdministratorController();
        ac.setCurrentAdmin(adminId);
        ac.approveReplenishmentReq(ac.getRequest(reqId));
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

    // Staff
    
    public List<HospitalStaff> getHospitalStaff() {
        return hospitalStaffController.getAllStaff();
    }

    public boolean addStaff(String name, String gender, HospitalStaff.Role role, int age) {
        return hospitalStaffController.addStaff(name, gender, role, age);
    }

    public boolean removeStaff(HospitalStaff staff) {
        return hospitalStaffController.removeStaff(staff);
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

    public Medicine getMedicine(String medicineId) {
        return medicineController.getMedicineById(medicineId);
    }

    public Boolean modifyMedStockAlert(Medicine medicine, int newLevel) {
        return medicineController.setLowStockThreshold(medicine, newLevel);
    }

    public Boolean addMedStock(Medicine medicine, int amount) {
        return medicineController.incMedStock(medicine, amount);
    }

    public Boolean decMedStock(Medicine medicine, int amount) {
        return medicineController.decMedStock(medicine, amount);
    }

    public Boolean updateMedStock(Medicine medicine, int amount) {
        return medicineController.updateMedStock(medicine, amount);
    }

    // Request

    public MedicineRequest getRequest(String id) {
        return medicineRequestController.getRequestById(id);
    }

    public List<MedicineRequest> getPendingReplenishingRequests() {
        return medicineRequestController.getPendingRequests();
    }

    public void approveReplenishmentReq(MedicineRequest req) {
        medicineRequestController.approveReplenishmentRequest(currentAdmin.getId(), req);
    }

    public void rejectReplenishmentReq(MedicineRequest req) {
        medicineRequestController.rejectReplenishmentRequest(currentAdmin.getId(), req);
    }
}
