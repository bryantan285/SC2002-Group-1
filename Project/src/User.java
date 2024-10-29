import java.util.*;


public abstract class User {

    private boolean isPatient;
    private String id;
    private String name;
    private String password = "password"; //default password
    private boolean gender; //true = male; false = female

    //password = "password" initially
    public User(boolean isPatient, String userId, String name, boolean gender){
        this.isPatient = isPatient;
        this.id = userId;
        this.name = name;
        this.gender = gender;
    }

    //Getters
    public boolean getIsPatient() {
        return this.isPatient;
    }
    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getPassword() {
        return this.password;
    }
    public boolean getGender() {
        return this.gender;
    }

    
    //Setters
    public void setIsPatient(boolean isPatient) {
        this.isPatient = isPatient;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
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
