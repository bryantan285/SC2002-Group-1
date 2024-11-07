package control.user;

import entity.appointment.Appointment;
import entity.medicine.Medicine;
import entity.user.HospitalStaff;
import java.util.ArrayList;
import java.util.List;
import repository.appointment.AppointmentRepository;
import repository.medicine.MedicineRepository;
import repository.user.StaffRepository;

public class AdministratorController {
    public static void main(String[] args) {
        AdministratorController ac = new AdministratorController();
        List<HospitalStaff> list = new ArrayList<>();
        list = ac.getHospitalStaff();
        
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }

        List<Appointment> alist = new ArrayList<>();
        alist = ac.getAppointments();

        System.out.println(alist.size());
        for (int i = 0; i < alist.size(); i++) {
            System.out.println(alist.get(i).toString());
        }

        List<Medicine> mlist = new ArrayList<>();
        mlist = ac.getMedInventory();

        System.out.println(mlist.size());
        for (int o = 0; o < mlist.size(); o++) {
            System.out.println(mlist.get(o).toString());
        }

    }

    private final StaffRepository staffRepository; 
    private final AppointmentRepository appointmentRepository;
    private final MedicineRepository medicineRepository;

    public AdministratorController() {
        this.staffRepository = StaffRepository.getInstance();
        this.appointmentRepository = AppointmentRepository.getInstance();
        this.medicineRepository = MedicineRepository.getInstance();
    }

    public List<HospitalStaff> getHospitalStaff() {
        List<HospitalStaff> staffList = staffRepository.toList();
        
        return staffList;
    }

    public List<Appointment> getAppointments() { 
        List<Appointment> apptList = appointmentRepository.toList();
        
        return apptList;
    }

    public List<Medicine> getMedInventory() { 
        List<Medicine> medicineList = medicineRepository.toList();
        
        return medicineList;
    }

    // Add into UML (Modify)
    public void approveReplenishmentReq() {

    }

    // Add into UML
    public void declineReplenishmentReq() {

    }
}
