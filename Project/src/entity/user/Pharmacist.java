package entity.user;

public class Pharmacist extends HospitalStaff {
    public Pharmacist(boolean isPatient, String userId, String password, String name, String gender, int age) {
        super(isPatient, userId, password, name, gender, Role.PHARMACIST, age);
    }
}
