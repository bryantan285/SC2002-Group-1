package entity.user;

public class StaffFactory {

    // Method to create the appropriate HospitalStaff subclass based on the prefix
    public static HospitalStaff createStaffByPrefix(String id) {
        if (id.startsWith("D")) {
            return new Doctor();
        } else if (id.startsWith("P")) {
            return new Pharmacist();
        } else if (id.startsWith("A")) {
            return new Administrator();
        } else {
            throw new IllegalArgumentException("Invalid prefix in ID: " + id);
        }
    }

    public static HospitalStaff createStaffByRole(HospitalStaff.Role role) {
        if (null == role) {
            throw new IllegalArgumentException("Invalid role");
        } else switch (role) {
            case DOCTOR:
                return new Doctor();
            case PHARMACIST:
                return new Pharmacist();
            case ADMINISTRATOR:
                return new Administrator();
            default:
                throw new IllegalArgumentException("Invalid role");
        }
    }
}