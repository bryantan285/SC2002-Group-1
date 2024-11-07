package control.user;

public class UserController {
    
    //function definitions
    public boolean login(String inputId, String inputPassword){
        if ((inputId.equals(this.getId())) && (inputPassword.equals(this.getPassword()))){
            //login successful
            return true;
        }
        
        System.out.println("Wrong ID and/or Password. Try again");
        return false;
    }
}
