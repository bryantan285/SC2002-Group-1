package control.appointment;

import entity.appointment.Appointment;
import interfaces.control.IController;
import java.time.LocalDateTime;
import java.util.List;
import repository.appointment.AppointmentRepository;

public class AppointmentController implements IController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController() {
        this.appointmentRepository = AppointmentRepository.getInstance();
    }

    @Override
    public void save() {
        appointmentRepository.save();
    }

    // Add a new appointment
    public void addAppointment(String doctorId, String patientId, Appointment.Service service) {
        Appointment newAppointment = new Appointment(appointmentRepository.getNextClassId(), patientId, doctorId, service, LocalDateTime.now());
        appointmentRepository.add(newAppointment);
        save();
        System.out.println("Appointment added successfully: " + newAppointment.getId());
    }

    // Get all appointments
    public List<Appointment> getAllAppts() {
        return appointmentRepository.toList();
    }

    // Get appointments for a specific doctor
    public List<Appointment> getDoctorAppts(String doctorId) {
        return appointmentRepository.findByField("doctorId", doctorId);
    }

    // Get an appointment by ID
    public Appointment getAppt(String apptId) {
        return appointmentRepository.findByField("id", apptId).stream().findFirst().orElse(null);
    }

    // Handle appointment request decision (accept or decline)
    public void apptRequestDecision(String apptId, boolean isAccepted) {
        Appointment appt = getAppt(apptId);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        if (isAccepted) {
            appt.setStatus(Appointment.Status.CONFIRMED);
            System.out.println("Appointment successfully confirmed.");
        } else {
            appt.setStatus(Appointment.Status.CANCELED);
            System.out.println("Appointment successfully declined.");
        }
        save();
    }

    // Complete an appointment and record the outcome
    public void completeAppointment(String apptId, String outcome) {
        Appointment appt = getAppt(apptId);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        appt.setApptOutcome(outcome);
        appt.setStatus(Appointment.Status.COMPLETED);
        save();
        System.out.println("Appointment marked as completed.");
    }

    // Cancel an appointment
    public void cancelAppointment(String apptId) {
        Appointment appt = getAppt(apptId);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        appt.setStatus(Appointment.Status.CANCELED);
        save();
        System.out.println("Appointment canceled successfully.");
    }

    // Reschedule an appointment
    public void rescheduleAppointment(String apptId, LocalDateTime newDateTime) {
        Appointment appt = getAppt(apptId);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        appt.setApptDate(newDateTime);
        appt.setStatus(Appointment.Status.PENDING);
        save();
        System.out.println("Appointment rescheduled to: " + newDateTime);
    }

    // Check the status of an appointment
    public Appointment.Status checkAppointmentStatus(String apptId) {
        Appointment appt = getAppt(apptId);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return null;
        }
        return appt.getStatus();
    }

    // List all pending appointments
    public List<Appointment> listPendingAppointments() {
        return appointmentRepository.findByField("status", Appointment.Status.PENDING.name());
    }

    // Check for overlapping appointments for a doctor
    public boolean checkOverlappingAppointments(String doctorId, LocalDateTime dateTime) {
        List<Appointment> doctorAppointments = getDoctorAppts(doctorId);
        for (Appointment appt : doctorAppointments) {
            if (appt.getApptDateTime().isEqual(dateTime)) {
                System.out.println("Overlapping appointment found: " + appt.getId());
                return true;
            }
        }
        return false;
    }
}
