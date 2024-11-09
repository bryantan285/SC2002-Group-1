package entity.user;

public class Pharmacist extends HospitalStaff {
    public Pharmacist() {
        super();
    }

    public Pharmacist(boolean isPatient, String userId, String name, String gender, int age) {
        super(isPatient, userId, name, gender, Role.PHARMACIST, age);
    }
}
