package entity.user;

import entity.EntityObject;
import utility.Password_hash;

public abstract class User extends EntityObject {

    private boolean isPatient;
    private String id;
    private String password;
    private String name;
    private String gender;

    public User() {
        
    }

    public User(boolean isPatient, String id, String name, String gender){
        this.isPatient = isPatient;
        this.id = id;
        this.password = Password_hash.hashPassword("password");
        this.name = name;
        this.gender = gender;
    }

    //Getters
    public boolean getIsPatient() {
        return this.isPatient;
    }

    @Override
    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getPassword() {
        return this.password;
    }
    public String getGender() {
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
    private void setPassword(String password) {
        this.password = password;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void changePassword(String input){
        setPassword(Password_hash.hashPassword(input));
    }
    
}
