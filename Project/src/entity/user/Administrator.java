package entity.user;

public class Administrator extends HospitalStaff {

    public Administrator() {
        super();
    }

    public Administrator(boolean isPatient, String userId, String name, String gender, int age) {
        super(isPatient, userId, name, gender, Role.ADMINISTRATOR, age);
    }
}
