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

    public void addAppointment(String doctorId, String patientId, Appointment.Service service) {
        Appointment newAppointment = new Appointment(appointmentRepository.getNextClassId(), patientId, doctorId, service, LocalDateTime.now());
        appointmentRepository.add(newAppointment);
        save();
        System.out.println("Appointment added successfully: " + newAppointment.getId());
    }

    public List<Appointment> getAllAppts() {
        return appointmentRepository.toList();
    }

    public List<Appointment> getDoctorAppts(String doctorId) {
        return appointmentRepository.findByField("doctorId", doctorId);
    }

    public Appointment getAppt(String apptId) {
        return appointmentRepository.findByField("id", apptId).stream().findFirst().orElse(null);
    }

    public void apptRequestDecision(Appointment appt, boolean isAccepted) {
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

    public void completeAppointment(Appointment appt, String outcome) {
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        appt.setApptOutcome(outcome);
        appt.setStatus(Appointment.Status.COMPLETED);
        save();
        System.out.println("Appointment marked as completed.");
    }

    public void cancelAppointment(Appointment appt) {
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        appt.setStatus(Appointment.Status.CANCELED);
        save();
        System.out.println("Appointment canceled successfully.");
    }

    public void rescheduleAppointment(Appointment appt, LocalDateTime newDateTime) {
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        appt.setApptDate(newDateTime);
        appt.setStatus(Appointment.Status.PENDING);
        save();
        System.out.println("Appointment rescheduled to: " + newDateTime);
    }

    public Appointment.Status checkAppointmentStatus(String apptId) {
        Appointment appt = getAppt(apptId);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return null;
        }
        return appt.getStatus();
    }

    public List<Appointment> listPendingAppointments() {
        return appointmentRepository.findByField("status", Appointment.Status.PENDING.name());
    }

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
