package entity.user;

public class Administrator extends HospitalStaff {

    public Administrator() {
        super();
    }

    public Administrator(boolean isPatient, String userId, String password, String name, String gender, int age) {
        super(isPatient, userId, password, name, gender, Role.ADMINISTRATOR, age);
    }
}
