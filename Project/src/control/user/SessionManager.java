package control.user;

import entity.user.Administrator;
import entity.user.Doctor;
import entity.user.Patient;
import entity.user.Pharmacist;
import entity.user.User;
import exception.user.InvalidUserTypeException;
import exception.user.NoUserLoggedInException;

public class SessionManager {

    private User currentUser;

    public SessionManager() {
        this.currentUser = null;
    }

    public void setCurrentUser(User user) throws InvalidUserTypeException {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (user instanceof Doctor) {
            currentUser = (Doctor) user;
        } else if (user instanceof Pharmacist) {
            currentUser = (Pharmacist) user;
        } else if (user instanceof Administrator) {
            currentUser = (Administrator) user;
        } else if (user instanceof Patient) {
            currentUser = (Patient) user;
        } else {
            throw new InvalidUserTypeException();
        }
    }

    public User getCurrentUser() throws NoUserLoggedInException {
        if (currentUser == null) {
            throw new NoUserLoggedInException();
        }
        return currentUser;
    }

    public void clearCurrentUser() {
        currentUser = null;
    }
}
