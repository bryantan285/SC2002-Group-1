public abstract class User {
    enum Role {PATIENT, DOCTOR, PHARMACIST, ADMINISTRATOR}

    private Role role;
    private String id;
    private String password = "password"; //default password
    private boolean gender; //true = male; false = female


    //Constructor function to create a new User obj
    public User(Role userRole, String userId, String userPassword, boolean gender){
        this.role = userRole;
        this.id = userId;
        this.password = userPassword;
        this.gender = gender;
    }

    //Getters
    public String getId() {
        return this.id;
    }
    public String getPassword() {
        return this.password;
    }
    public Role getRole() {
        return this.role;
    }
    public boolean getGender() {
        return this.gender;
    }

    
    //Setters
    public void setId(String id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }


    //function definitions
    public boolean login(String inputId, String inputPassword){
        if (inputId == this.getId() && inputPassword == this.getPassword()){
            //login successful
            return true;
        }
        
        System.out.println("Wrong ID and/or Password. Try again");
        return false;
    }

    public void changePassword(String input){
        setPassword(input);
    }
    
}
