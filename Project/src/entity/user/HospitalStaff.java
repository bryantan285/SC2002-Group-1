package entity.user;
public class HospitalStaff extends User{
    protected enum Role {DOCTOR, PHARMACIST, ADMINISTRATOR}

    private Role role;
    private int age;

    public HospitalStaff() {
        super();
    }

    public HospitalStaff(boolean isPatient, String userId, String password, String name, String gender, Role role, int age) {
        super(isPatient, userId, password, name, gender);
        this.role = role;
        this.age = age;
    }
    
    //Getters
    public Role getRole() {
        return role;
    }
    public int getAge() {
        return this.age;
    }

    //Setters
    public void setRole(Role role) {
        this.role = role;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return super.getId() + ", " + super.getName() + ", " + super.getGender() + ", " + role + ", " + age;
    }
}
