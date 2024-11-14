package control.user;

import entity.user.Patient;
import entity.user.User;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import repository.user.PatientRepository;

public class PatientController {

    public static Patient getById(String patientId) throws EntityNotFoundException {
        Patient patient = PatientRepository.getInstance().get(patientId);
        if (patient == null) {
            throw new EntityNotFoundException("Patient", patientId);
        }
        return patient;
    }

    public static void changeEmail(User user, String email) throws InvalidInputException, EntityNotFoundException {
        if (email == null || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new InvalidInputException("Invalid email format.");
        }
        PatientRepository repo = PatientRepository.getInstance();
        Patient patient = repo.findByField("id", user.getId()).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Patient", user.getId()));
        patient.setEmail(email);
        repo.save();
    }

    public static void changeContactNumber(User user, String contactNumber) throws InvalidInputException, EntityNotFoundException {
        if (contactNumber == null || !contactNumber.matches("^\\+?[0-9]{10,15}$")) {
            throw new InvalidInputException("Invalid contact number format.");
        }
        PatientRepository repo = PatientRepository.getInstance();
        Patient patient = repo.findByField("id", user.getId()).stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Patient", user.getId()));
        patient.setContactNumber(contactNumber);
        repo.save();
    }
}
