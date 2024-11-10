package control.user;

import entity.user.HospitalStaff;
import entity.user.StaffFactory;
import interfaces.control.IController;
import java.util.List;
import repository.user.StaffRepository;

public class HospitalStaffController implements IController {
    private final StaffRepository staffRepository;

    public HospitalStaffController() {
        this.staffRepository = StaffRepository.getInstance();
    }

    @Override
    public void save() {
        staffRepository.save();
    }

    public List<HospitalStaff> getAllStaff() {
        return staffRepository.toList();
    }

    public Boolean addStaff(String name, String gender, HospitalStaff.Role role, int age) {
        try {
            HospitalStaff newStaff = StaffFactory.createStaffByRole(role);
            newStaff.setIsPatient(false);
            newStaff.setName(name);
            newStaff.setId(staffRepository.getNextId(role));
            newStaff.setGender(gender);
            newStaff.setRole(role);
            newStaff.setAge(age);
            staffRepository.add(newStaff);
            save();
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public Boolean removeStaff(HospitalStaff staff) {
        if (staff == null) return false;
        staffRepository.remove(staff);
        return true;
    }

    public HospitalStaff findById(String id) {
        return staffRepository.get(id);
    }

    public List<HospitalStaff> findByField(String field, String value) {
        return staffRepository.findByField(field, value);
    }
}
