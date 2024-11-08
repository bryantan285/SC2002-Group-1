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
import repository.user.StaffRepository;

public class AdministratorController implements IController {
    public static void main(String[] args) {
        String adminId = "A001";
        String reqId = "MREQ001";
        AdministratorController ac = new AdministratorController();
        ac.setCurrentAdmin(adminId);
        ac.approveReplenishmentReq(reqId);
    }

    private final StaffRepository staffRepository; 
    private final AppointmentController appointmentController;
    private final MedicineController medicineController;
    private final MedicineRequestController medicineRequestController;
    private Administrator currentAdmin;

    public AdministratorController() {
        this.staffRepository = StaffRepository.getInstance();
        this.appointmentController = new AppointmentController();
        this.medicineController = new MedicineController();
        this.medicineRequestController = new MedicineRequestController();
        this.currentAdmin = null;
    }

    public void setCurrentAdmin(String adminId) {
        HospitalStaff staff = staffRepository.findByField("id", adminId).get(0);
        if (staff instanceof Administrator) {
            this.currentAdmin = (Administrator) staff;
            System.out.println("Administrator set successfully: " + this.currentAdmin.getId());
        } else {
            System.out.println("Staff member with ID " + adminId + " is not a Administrator.");
            this.currentAdmin = null;
        }
    }

    @Override
    public void save() {
        staffRepository.save();
    }
    
    public List<HospitalStaff> getHospitalStaff() {
        return staffRepository.toList();
    }

    public List<Appointment> getAppointments() { 
        return appointmentController.getAllAppts();
    }

    public List<Medicine> getMedInventory() { 
        return medicineController.getAllMedicines();
    }

    // Add into UML (Modify)
    public void approveReplenishmentReq(String requestId) {
        medicineRequestController.approveReplenishmentRequest(currentAdmin.getId(), requestId);
    }

    // Add into UML
    public void rejectReplenishmentReq(String requestId) {
        medicineRequestController.rejectReplenishmentRequest(currentAdmin.getId(), requestId);
    }
}
