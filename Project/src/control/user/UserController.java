package control.user;

import entity.user.User;
import interfaces.control.IController;
import repository.user.PatientRepository;
import repository.user.StaffRepository;
import utility.Password_hash;

public class UserController implements IController {
    
    private StaffRepository staffRepository;
    private PatientRepository patientRepository;
    private User currentUser;

    public static void main(String[] args) {
        UserController userController = new UserController();
        userController.login("D001","password"); // Sets currentUser as a Doctor
        userController.login("PH001","password"); // Sets currentUser as a Pharmacist
        userController.login("P123","password"); // Sets currentUser as a Patient
    }

    public UserController() {
        this.staffRepository = StaffRepository.getInstance();
        this.patientRepository = PatientRepository.getInstance();
        this.currentUser = null;
    }

    @Override
    public void save() {
        staffRepository.save();
        patientRepository.save();
    }
    
    public boolean login(String inputId, String inputPassword) {
        if (inputId.startsWith("D") || inputId.startsWith("PH") || inputId.startsWith("A")) {
            currentUser = staffRepository.findByField("id", inputId).stream().findFirst().orElse(null);
        } else {
            currentUser = patientRepository.findByField("id", inputId).stream().findFirst().orElse(null);
        }
        if (currentUser == null) {
            return false;
        }
        String hashedPassword = Password_hash.hashPassword(inputPassword);
        if (hashedPassword.equals(currentUser.getPassword())) {
            return true;
        }
        return false;
    }

    public void passwordChange(String userId, String newPassword) {
        currentUser.changePassword(newPassword);
        save();
    }
}
