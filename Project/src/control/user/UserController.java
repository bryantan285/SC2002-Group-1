package control.user;

import entity.user.User;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import repository.user.PatientRepository;
import repository.user.StaffRepository;
import utility.Password_hash;

public class UserController {

    public static User login(String inputId, String inputPassword) throws InvalidInputException, EntityNotFoundException {
        if (inputId == null || inputId.isEmpty()) {
            throw new InvalidInputException("User ID cannot be null or empty.");
        }
        if (inputPassword == null || inputPassword.isEmpty()) {
            throw new InvalidInputException("Password cannot be null or empty.");
        }

        User currentUser;
        if (inputId.startsWith("D") || inputId.startsWith("PH") || inputId.startsWith("A")) {
            currentUser = StaffRepository.getInstance().findByField("id", inputId).stream().findFirst().orElse(null);
        } else {
            currentUser = PatientRepository.getInstance().findByField("id", inputId).stream().findFirst().orElse(null);
        }

        if (currentUser == null) {
            throw new EntityNotFoundException("Invalid username or password");
        }

        String hashedPassword = Password_hash.hashPassword(inputPassword);
        if (!hashedPassword.equals(currentUser.getPassword())) {
            throw new InvalidInputException("Invalid username or password.");
        }

        return currentUser;
    }

    public static void passwordChange(User user, String newPassword) throws InvalidInputException {
        if (user == null) {
            throw new InvalidInputException("User cannot be null.");
        }
        if (newPassword == null || newPassword.isEmpty() || newPassword.length() < 8) {
            throw new InvalidInputException("Password must be at least 8 characters long.");
        }

        user.changePassword(newPassword);
        StaffRepository.getInstance().save();
        PatientRepository.getInstance().save();
    }
}
