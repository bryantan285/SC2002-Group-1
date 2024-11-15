package control.user;

import entity.user.HospitalStaff;
import entity.user.StaffFactory;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.util.List;
import repository.user.StaffRepository;

public class HospitalStaffController {

    public static List<HospitalStaff> getAllStaff() {
        return StaffRepository.getInstance().toList();
    }

    public static Boolean addStaff(String name, String gender, HospitalStaff.Role role, int age) throws InvalidInputException {
        if (name == null || name.isEmpty()) {
            throw new InvalidInputException("Name cannot be null or empty.");
        }
        if (gender == null || (!gender.equalsIgnoreCase("male") && !gender.equalsIgnoreCase("female"))) {
            throw new InvalidInputException("Invalid gender. Must be 'male' or 'female'.");
        }
        if (role == null) {
            throw new InvalidInputException("Role cannot be null.");
        }
        if (age < 18 || age > 100) {
            throw new InvalidInputException("Age must be between 18 and 100.");
        }

        try {
            StaffRepository repo = StaffRepository.getInstance();
            HospitalStaff newStaff = StaffFactory.createStaffByRole(role);
            newStaff.setIsPatient(false);
            newStaff.changePassword("password");
            newStaff.setName(name);
            newStaff.setId(repo.getNextId(role));
            newStaff.setGender(gender);
            newStaff.setRole(role);
            newStaff.setAge(age);
            repo.add(newStaff);
            repo.save();
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static Boolean removeStaff(HospitalStaff staff) throws EntityNotFoundException {
        if (staff == null) {
            throw new EntityNotFoundException("Staff", "null");
        }
        StaffRepository repo = StaffRepository.getInstance();
        if (repo.get(staff.getId()) == null) {
            throw new EntityNotFoundException("Staff", staff.getId());
        }
        repo.remove(staff);
        repo.save();
        return true;
    }

    public static HospitalStaff findById(String id) throws EntityNotFoundException {
        HospitalStaff staff = StaffRepository.getInstance().get(id);
        if (staff == null) {
            throw new EntityNotFoundException("Staff", id);
        }
        return staff;
    }

    public static List<HospitalStaff> findByField(String field, String value) throws InvalidInputException {
        if (field == null || field.isEmpty() || value == null || value.isEmpty()) {
            throw new InvalidInputException("Field and value cannot be null or empty.");
        }
        return StaffRepository.getInstance().findByField(field, value);
    }
}
