package control.appointment;

import control.user.UnavailableDateController;
import entity.appointment.Appointment;
import entity.user.UnavailableDate;
import interfaces.control.ISavable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import repository.appointment.AppointmentRepository;

public class AppointmentController implements ISavable {
    private final AppointmentRepository appointmentRepository;
    private final UnavailableDateController unavailableDateController;
    private final AppointmentController appointmentController;

    public AppointmentController() {
        this.appointmentRepository = AppointmentRepository.getInstance();
        this.unavailableDateController = new UnavailableDateController();
        this.appointmentController = new AppointmentController();
    }

    @Override
    public void save() {
        appointmentRepository.save();
    }

    public List<LocalDateTime> getAvailableSlots(String doctorId) {
        // Get all unavailable slots for the doctor
        List<UnavailableDate> unavailableSlots = unavailableDateController.getUnavailableDates(doctorId);

        // Get all existing appointments for the doctor
        List<Appointment> doctorAppointments = appointmentController.getDoctorAppts(doctorId);

        // Create a list of all slots within a given range (e.g., doctor's working hours, for simplicity we assume a 9 AM to 5 PM working day)
        List<LocalDateTime> allSlots = generateAllSlotsForDay(); // You can define this function to generate all slots based on working hours
        
        // Filter out the slots that are either in the unavailable slots list or already taken by an appointment
        return allSlots.stream()
            .filter(slot -> !unavailableSlots.contains(slot) && doctorAppointments.stream().noneMatch(appt -> appt.getApptDateTime().equals(slot)))
            .collect(Collectors.toList());
    }

    // Helper method to generate all available slots for a day (can be customized based on working hours)
    private List<LocalDateTime> generateAllSlotsForDay() {
        List<LocalDateTime> slots = new ArrayList<>();
        
        // Example: generating hourly slots from 9 AM to 5 PM
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0));
        
        while (startOfDay.isBefore(endOfDay)) {
            slots.add(startOfDay);
            startOfDay = startOfDay.plusHours(1); // Add 1 hour per slot
        }

        return slots;
    }
    
    public void scheduleAppointment(String doctorId, String patientId, Appointment.Service service, LocalDateTime selectedSlot) {
        Appointment newAppointment = new Appointment(appointmentRepository.getNextClassId(), patientId, doctorId, service, selectedSlot);
        appointmentRepository.add(newAppointment);
        save();
        System.out.println("Appointment added successfully: " + newAppointment.getId());
    }

    public void rescheduleAppointment(Appointment appt, LocalDateTime newDateTime) {
        if (appt == null) {
            System.out.println("Appointment not found.");
            return;
        }

        // Check if the new time slot is available
        if (checkOverlappingAppointments(appt.getDoctorId(), newDateTime)) {
            System.out.println("The new time slot is not available.");
            return;
        }

        appt.setApptDate(newDateTime);
        appt.setStatus(Appointment.Status.PENDING);
        save();
        System.out.println("Appointment rescheduled to: " + newDateTime);
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

    public Appointment.Status checkAppointmentStatus(String apptId) {
        Appointment appt = getAppt(apptId);
        if (appt == null) {
            System.out.println("Appointment not found.");
            return null;
        }
        return appt.getStatus();
    }

    public List<Appointment> getPendingAppointments() {
        return appointmentRepository.findByField("status", Appointment.Status.PENDING);
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

    public List<Appointment> getScheduledAppointments(String patientId) {
        return appointmentRepository.findByField("patientId", patientId).stream()
            .filter(appt -> appt.getApptDateTime().isAfter(LocalDateTime.now()) &&
                            appt.getStatus() == Appointment.Status.PENDING)
            .collect(Collectors.toList());
    }

    public List<Appointment> getPastAppointments(String patientId) {
        return appointmentRepository.findByField("patientId", patientId).stream()
            .filter(appt -> appt.getApptDateTime().isBefore(LocalDateTime.now()) &&
                            (appt.getStatus() == Appointment.Status.COMPLETED || 
                             appt.getStatus() == Appointment.Status.CANCELED))
            .collect(Collectors.toList());
    }

    public List<String> getAppointmentOutcomes(String patientId) {
        List<Appointment> pastAppointments = getPastAppointments(patientId);
        return pastAppointments.stream()
            .map(appt -> "Appointment ID: " + appt.getId() + " - Outcome: " + appt.getApptOutcome())
            .collect(Collectors.toList());
    }   
}
