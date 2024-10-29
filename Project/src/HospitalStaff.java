public class HospitalStaff extends User{
    enum Role {DOCTOR, PHARMACIST, ADMINISTRATOR}

    private Role role;
    private int age;

    public HospitalStaff(boolean isPatient, String userId, String name, boolean gender, Role role, int age){
        super(isPatient, userId, name, gender);
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

}
