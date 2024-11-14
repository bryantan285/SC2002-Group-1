package control.appointment;

import control.user.HospitalStaffController;
import control.user.UnavailableDateController;
import entity.appointment.Appointment;
import entity.user.Doctor;
import entity.user.Patient;
import entity.user.UnavailableDate;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import repository.appointment.AppointmentRepository;

public class AppointmentController {

    private static final AppointmentRepository appointmentRepository = AppointmentRepository.getInstance();

    public static void scheduleAppointment(Doctor doc, Patient patient, Appointment.Service service, LocalDateTime selectedSlot) throws InvalidInputException {
        if (doc == null || patient == null || service == null || selectedSlot == null) {
            throw new InvalidInputException("Invalid input: Doctor, patient, service, and selected slot must not be null.");
        }

        Appointment newAppointment = new Appointment(appointmentRepository.getNextClassId(), patient.getId(), doc.getId(), service, selectedSlot);
        appointmentRepository.add(newAppointment);
        appointmentRepository.save();
    }

    public static Appointment getAppt(String apptId) throws InvalidInputException, EntityNotFoundException {
        if (apptId == null || apptId.isEmpty()) {
            throw new InvalidInputException("Appointment ID cannot be null or empty.");
        }

        Appointment appt = appointmentRepository.findByField("id", apptId).stream().findFirst().orElse(null);
        if (appt == null) {
            throw new EntityNotFoundException("Appointment", apptId);
        }
        return appt;
    }

    public static List<Appointment> getAllAppts() {
        return appointmentRepository.toList();
    }

    public static List<Appointment> getDoctorAppts(Doctor doc) throws InvalidInputException {
        if (doc == null) {
            throw new InvalidInputException("Doctor cannot be null.");
        }
        return appointmentRepository.findByField("doctorId", doc.getId());
    }

    public static List<Appointment> getScheduledAppointments(Patient patient) throws InvalidInputException {
        if (patient == null) {
            throw new InvalidInputException("Patient cannot be null.");
        }

        return appointmentRepository.findByField("patientId", patient.getId()).stream()
                .filter(appt -> appt.getApptDateTime().isAfter(LocalDateTime.now()) && appt.getStatus() == Appointment.Status.PENDING)
                .collect(Collectors.toList());
    }

    public static List<Appointment> getPastAppointments(Patient patient) throws InvalidInputException {
        if (patient == null) {
            throw new InvalidInputException("Patient cannot be null.");
        }

        return appointmentRepository.findByField("patientId", patient.getId()).stream()
                .filter(appt -> appt.getApptDateTime().isBefore(LocalDateTime.now()) &&
                                (appt.getStatus() == Appointment.Status.COMPLETED || appt.getStatus() == Appointment.Status.CANCELED))
                .collect(Collectors.toList());
    }

    public static List<String> getAppointmentOutcomes(Patient patient) throws InvalidInputException {
        if (patient == null) {
            throw new InvalidInputException("Patient cannot be null.");
        }

        List<Appointment> pastAppointments = getPastAppointments(patient);
        return pastAppointments.stream()
                .map(appt -> "Appointment ID: " + appt.getId() + " - Outcome: " + appt.getApptOutcome())
                .collect(Collectors.toList());
    }

    public static List<LocalDateTime> getAvailableSlots(Doctor doc) throws InvalidInputException {
        if (doc == null) {
            throw new InvalidInputException("Doctor cannot be null.");
        }

        List<UnavailableDate> unavailableDates = UnavailableDateController.getUnavailableDates(doc);
        List<Appointment> doctorAppointments = getDoctorAppts(doc);
        List<LocalDateTime> allSlots = generateAllSlotsForDay();

        return allSlots.stream()
                .filter(slot -> unavailableDates.stream().noneMatch(unavailable -> unavailable.getDate().isEqual(slot)) &&
                                doctorAppointments.stream().noneMatch(appt -> appt.getApptDateTime().equals(slot)))
                .collect(Collectors.toList());
    }

    private static List<LocalDateTime> generateAllSlotsForDay() {
        List<LocalDateTime> slots = new ArrayList<>();
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0));

        while (startOfDay.isBefore(endOfDay)) {
            slots.add(startOfDay);
            startOfDay = startOfDay.plusHours(1);
        }

        return slots;
    }

    public static void rescheduleAppointment(Appointment appt, LocalDateTime newDateTime) throws InvalidInputException, EntityNotFoundException {
        if (appt == null || newDateTime == null) {
            throw new InvalidInputException("Appointment and new date/time cannot be null.");
        }

        Doctor doc = (Doctor) HospitalStaffController.findById(appt.getDoctorId());
        if (checkOverlappingAppointments(doc, newDateTime)) {
            throw new InvalidInputException("The new time slot is not available.");
        }

        appt.setApptDate(newDateTime);
        appt.setStatus(Appointment.Status.PENDING);
        appointmentRepository.save();
    }

    public static void apptRequestDecision(Appointment appt, boolean isAccepted) throws InvalidInputException {
        if (appt == null) {
            throw new InvalidInputException("Appointment cannot be null.");
        }
    
        if (appt.getStatus() == Appointment.Status.CONFIRMED) {
            throw new InvalidInputException("Appointment is already confirmed.");
        }
    
        if (appt.getStatus() == Appointment.Status.CANCELED) {
            throw new InvalidInputException("Appointment is already canceled.");
        }
    
        appt.setStatus(isAccepted ? Appointment.Status.CONFIRMED : Appointment.Status.CANCELED);
        appointmentRepository.save();
    }
    

    public static void completeAppointment(Appointment appt, String outcome) throws InvalidInputException {
        if (appt == null) {
            throw new InvalidInputException("Appointment cannot be null.");
        }
        if (outcome == null || outcome.isEmpty()) {
            throw new InvalidInputException("Outcome cannot be null or empty.");
        }

        appt.setApptOutcome(outcome);
        appt.setStatus(Appointment.Status.COMPLETED);
        appointmentRepository.save();
    }

    public static void cancelAppointment(Appointment appt) throws InvalidInputException {
        if (appt == null) {
            throw new InvalidInputException("Appointment cannot be null.");
        }

        appt.setStatus(Appointment.Status.CANCELED);
        appointmentRepository.save();
    }

    public static boolean checkOverlappingAppointments(Doctor doc, LocalDateTime dateTime) throws InvalidInputException {
        if (doc == null || dateTime == null) {
            throw new InvalidInputException("Doctor and date/time cannot be null.");
        }

        List<Appointment> doctorAppointments = getDoctorAppts(doc);
        return doctorAppointments.stream().anyMatch(appt -> appt.getApptDateTime().isEqual(dateTime));
    }
}
