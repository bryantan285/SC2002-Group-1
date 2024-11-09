package entity.user;

public class Doctor extends HospitalStaff {
    public Doctor() {
        super();
    }

    public Doctor(boolean isPatient, String userId, String name, String gender, int age) {
        super(isPatient, userId, name, gender, Role.DOCTOR, age);
    }

}
