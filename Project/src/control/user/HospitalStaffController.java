package control.user;

import entity.user.HospitalStaff;
import entity.user.StaffFactory;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.util.List;
import repository.user.StaffRepository;

/**
 * The HospitalStaffController class provides methods for managing hospital staff entities.
 * It allows for adding, removing, finding, and listing staff members. It interacts with 
 * the StaffRepository to perform data operations on hospital staff records.
 */
public class HospitalStaffController {

    /**
     * Retrieves all hospital staff from the repository.
     *
     * @return a list of all hospital staff
     */
    public static List<HospitalStaff> getAllStaff() {
        return StaffRepository.getInstance().toList();
    }

    /**
     * Adds a new staff member to the repository after validating the input parameters.
     * If the input is invalid, an InvalidInputException is thrown.
     *
     * @param name the name of the staff member
     * @param gender the gender of the staff member (male or female)
     * @param role the role of the staff member (Doctor, Pharmacist, etc.)
     * @param age the age of the staff member (must be between 18 and 100)
     * @return true if the staff member was successfully added, false otherwise
     * @throws InvalidInputException if any of the input parameters are invalid
     */
    public static Boolean addStaff(String name, String gender, HospitalStaff.Role role, int age) throws InvalidInputException {
        // Validate inputs
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
            // Create new staff member
            StaffRepository repo = StaffRepository.getInstance();
            HospitalStaff newStaff = StaffFactory.createStaffByRole(role);
            newStaff.setIsPatient(false); // Ensure the new staff is not marked as a patient
            newStaff.changePassword("password"); // Set a default password
            newStaff.setName(name);
            newStaff.setId(repo.getNextId(role));
            newStaff.setGender(gender);
            newStaff.setRole(role);
            newStaff.setAge(age);

            // Add staff to repository and save
            repo.add(newStaff);
            repo.save();
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Removes a staff member from the repository.
     * If the staff member does not exist in the repository, an EntityNotFoundException is thrown.
     *
     * @param staff the staff member to remove
     * @return true if the staff member was successfully removed, false otherwise
     * @throws EntityNotFoundException if the staff member cannot be found
     */
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

    /**
     * Finds a staff member by their ID.
     * If the staff member does not exist, an EntityNotFoundException is thrown.
     *
     * @param id the ID of the staff member
     * @return the staff member with the specified ID
     * @throws EntityNotFoundException if the staff member cannot be found
     */
    public static HospitalStaff findById(String id) throws EntityNotFoundException {
        HospitalStaff staff = StaffRepository.getInstance().get(id);
        if (staff == null) {
            throw new EntityNotFoundException("Staff", id);
        }
        return staff;
    }

    /**
     * Finds staff members by a specific field and its value.
     * If the field or value is invalid, an InvalidInputException is thrown.
     *
     * @param field the name of the field to search by
     * @param value the value to search for
     * @return a list of staff members matching the field and value
     * @throws InvalidInputException if the field or value is invalid
     */
    public static List<HospitalStaff> findByField(String field, String value) throws InvalidInputException {
        if (field == null || field.isEmpty() || value == null || value.isEmpty()) {
            throw new InvalidInputException("Field and value cannot be null or empty.");
        }
        return StaffRepository.getInstance().findByField(field, value);
    }
}
