package entity.user;
public class HospitalStaff extends User{
    public enum Role {DOCTOR, PHARMACIST, ADMINISTRATOR}

    private Role role;
    private int age;

    public HospitalStaff() {
        super();
    }

    public HospitalStaff(boolean isPatient, String userId, String name, String gender, Role role, int age) {
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
    public void setRole(Role Role) {
        this.role = Role;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User ID: " + super.getId() + "\n" +
               "Name: " + super.getName() + "\n" +
               "Gender: " + super.getGender() + "\n" +
               "Role: " + role + "\n" +
               "Age: " + age;
    }
    
    
}
