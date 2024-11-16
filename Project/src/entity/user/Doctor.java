package entity.user;
/**
 * The Doctor class represents a specific type of hospital staff who is a medical professional.
 * It extends the HospitalStaff class and inherits its properties and methods, while specifying the role as DOCTOR.
 */
public class Doctor extends HospitalStaff {
    /**
     * Default constructor for the Doctor class.
     * This calls the superclass constructor with default values.
     */
    public Doctor() {
        super();
    }

    /**
     * Constructor to initialize a Doctor with specified details.
     *
     * @param isPatient A boolean indicating whether the user is a patient (false for doctor).
     * @param userId The unique identifier of the user.
     * @param name The name of the doctor.
     * @param gender The gender of the doctor.
     * @param age The age of the doctor.
     */

    public Doctor(boolean isPatient, String userId, String name, String gender, int age) {
        super(isPatient, userId, name, gender, Role.DOCTOR, age);
    }

}
