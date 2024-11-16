package entity.user;

/**
 * The Administrator class represents a specific type of hospital staff who has administrative privileges.
 * It extends the HospitalStaff class and inherits its properties and methods, while specifying the role as ADMINISTRATOR.
 */
public class Administrator extends HospitalStaff {
    /**
     * Default constructor for the Administrator class.
     * This calls the superclass constructor with default values.
     */
    public Administrator() {
        super();
    }
    /**
     * Constructor to initialize an Administrator with specified details.
     *
     * @param isPatient A boolean indicating whether the user is a patient (false for administrator).
     * @param userId The unique identifier of the user.
     * @param name The name of the administrator.
     * @param gender The gender of the administrator.
     * @param age The age of the administrator.
     */
    public Administrator(boolean isPatient, String userId, String name, String gender, int age) {
        super(isPatient, userId, name, gender, Role.ADMINISTRATOR, age);
    }
}
