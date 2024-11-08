package entity.user;

public class Doctor extends HospitalStaff {
    public Doctor() {
        super();
    }

    public Doctor(boolean isPatient, String userId, String password, String name, String gender, int age) {
        super(isPatient, userId, password, name, gender, Role.DOCTOR, age);
    }

}
