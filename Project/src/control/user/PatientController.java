package control.user;

import control.appointment.AppointmentController;
import entity.appointment.Appointment;
import entity.user.Patient;
import interfaces.control.ISavable;
import java.time.LocalDateTime;
import repository.user.PatientRepository;

public class PatientController implements ISavable {
    private final PatientRepository patientRepository;
    private final AppointmentController appointmentController;
    private Patient currentPatient;

    public PatientController() {
        this.patientRepository = PatientRepository.getInstance();
        this.appointmentController = new AppointmentController();
        this.currentPatient = null;
    }
    
    @Override
    public void save() {
        patientRepository.save();
    }

    public void setCurrentPatient(String patientId) {
        Patient patient = patientRepository.get(patientId);
        currentPatient = patient;
    }

    // Patient

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    // Do checking
    public void changeEmail(String email) {
        currentPatient.setEmail(email);
        save();
    }

    public void changeContactNumber(String contactNumber) {
        currentPatient.setContactNumber(contactNumber);
        save();
    }

    // Appointment

    public void scheduleAppointment(String doctorId, Appointment.Service service) {
        appointmentController.addAppointment(doctorId, currentPatient.getId(), service);
    }

    public void rescheduleAppointment(Appointment appt, LocalDateTime newDateTime) {
        appointmentController.rescheduleAppointment(appt, newDateTime);
    }

    public void cancelAppointment(Appointment appt) {
        appointmentController.cancelAppointment(appt);
    }

    public Appointment.Status checkAppointmentStatus(String apptId) {
        return appointmentController.checkAppointmentStatus(apptId);
    }
}
