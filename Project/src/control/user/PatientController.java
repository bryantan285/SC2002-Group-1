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

    public void scheduleAppointment(String doctorId, Appointment.Service service, LocalDateTime selectedSlot) {
        appointmentController.scheduleAppointment(doctorId, currentPatient.getId(), service, selectedSlot);
    }

    public void rescheduleAppointment(String apptId, LocalDateTime newDateTime) {
        Appointment appt = appointmentController.getAppt(apptId);
        if (appt != null) {
            appointmentController.rescheduleAppointment(appt, newDateTime);
        } else {
            System.out.println("Appointment not found.");
        }
    }

    public void cancelAppointment(String apptId) {
        Appointment appt = appointmentController.getAppt(apptId);
        if (appt != null) {
            appointmentController.cancelAppointment(appt);
        } else {
            System.out.println("Appointment not found.");
        }
    }

    public Appointment.Status checkAppointmentStatus(String apptId) {
        return appointmentController.checkAppointmentStatus(apptId);
    }

    public void viewScheduledAppointments() {
        appointmentController.getScheduledAppointments(currentPatient.getId()).forEach(System.out::println);
    }

    public void viewPastAppointmentOutcomeRecords() {
        appointmentController.getPastAppointments(currentPatient.getId()).forEach(System.out::println);
    }
}
