package control.appointment;

import control.medicine.MedicineController;
import control.prescription.PrescriptionController;
import control.prescription.PrescriptionItemController;
import control.user.HospitalStaffController;
import control.user.UnavailableDateController;
import entity.appointment.Appointment;
import entity.appointment.Appointment.AppointmentOutcome;
import entity.medicine.Medicine;
import entity.medicine.Prescription;
import entity.user.Doctor;
import entity.user.Patient;
import entity.user.UnavailableDate;
import exception.EntityNotFoundException;
import exception.InvalidInputException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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

    public static List<Appointment> getPendingAppts(Doctor doc) throws InvalidInputException {
        if (doc == null) {
            throw new InvalidInputException("Doctor cannot be null.");
        }
        return appointmentRepository.findByField("doctorId", doc.getId()).stream()
                .filter(appt -> appt.getStatus() == Appointment.Status.PENDING)
                .collect(Collectors.toList());
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
    

    public static void completeAppointment(Appointment appt, String diagnosis, String consultationNotes, List<Prescription> prescriptions, String outcome) throws InvalidInputException, EntityNotFoundException {
        if (appt == null) {
            throw new InvalidInputException("Appointment cannot be null.");
        }
        if (outcome == null || outcome.isEmpty()) {
            throw new InvalidInputException("Outcome cannot be null or empty.");
        }

        // Create an AppointmentOutcome object using the provided details
        Appointment.AppointmentOutcome apptOutcome = new AppointmentOutcome(diagnosis, consultationNotes, prescriptions);

        // Set the appointment outcome as a list
        appt.setApptOutcome(Collections.singletonList(apptOutcome));

        // Set the appointment status to COMPLETED
        appt.setStatus(Appointment.Status.COMPLETED);

        // Save the appointment (assuming appointmentRepository.save() is defined elsewhere)
        appointmentRepository.save();

        // Check if prescription needs to be created based on appointment outcome
        if (outcome.equalsIgnoreCase("Medication Prescribed")) {
            createPrescriptionForAppointment(appt);
        }
    }

    // Helper method to create a prescription from the appointment
    private static void createPrescriptionForAppointment(Appointment appt) throws InvalidInputException, EntityNotFoundException {
        // Assuming prescription is created when medication is prescribed
        String apptId = appt.getId();
        PrescriptionController.createPrescription(apptId, true);  // Active prescription

        Prescription prescription = PrescriptionController.getPrescriptionById(apptId);
        
        // Add medications to prescription (example logic, you may modify based on your prescription items)
        List<Medicine> medicines = getMedicinesForPrescription(appt); // Assuming a method to fetch medicines for the prescription
        for (Medicine med : medicines) {
            // Add each medicine to the prescription as a prescription item
            PrescriptionItemController.createPrescriptionItem(prescription.getId(), med.getId(), 1, "Medication prescribed for treatment.");
        }
    }

    private static List<Medicine> getMedicinesForPrescription(Appointment appt) {
        return MedicineController.getAllMedicines(); // Example, you might have logic to filter by specific needs
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

    public static List<Appointment> getApptByDate(Doctor doc, LocalDate date) throws InvalidInputException {
        if (doc == null) {
            throw new InvalidInputException("Doctor cannot be null.");
        }
        if (date == null) {
            throw new InvalidInputException("Date cannot be null.");
        }
    
        // Fetch all appointments for the doctor
        List<Appointment> doctorAppointments = getDoctorAppts(doc);
    
        // Filter appointments matching the specified date
        return doctorAppointments.stream()
                .filter(appt -> appt.getApptDateTime().toLocalDate().isEqual(date))
                .collect(Collectors.toList());
    }
    
}
