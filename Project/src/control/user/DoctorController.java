package control.user;

import control.appointment.AppointmentController;
import entity.appointment.Appointment;
import entity.user.Doctor;
import entity.user.HospitalStaff;
import entity.user.Patient;
import entity.user.UnavailableDate;
import java.time.LocalDateTime;
import java.util.List;
import repository.user.PatientRepository;
import utility.DateFormat;

public class DoctorController {
    
    private final UnavailableDateController unavailableDateController;
    private final AppointmentController appointmentController;
    private final HospitalStaffController hospitalStaffController;
    private final PatientRepository patientRepository;
    private Doctor currentDoc;

    public static void main(String[] args) {
        DoctorController dc = new DoctorController();
        dc.setCurrentDoctor("D001"); // Testing
        dc.viewSchedule();
    }

    public DoctorController() {
        this.unavailableDateController = new UnavailableDateController();
        this.appointmentController = new AppointmentController();
        this.hospitalStaffController = new HospitalStaffController();
        this.patientRepository = PatientRepository.getInstance();
        this.currentDoc = null;
    }

    public void setCurrentDoctor(String doctorId) {
        HospitalStaff staff = hospitalStaffController.findById(doctorId);
        if (staff instanceof Doctor) {
            this.currentDoc = (Doctor) staff;
        } else {
            this.currentDoc = null;
        }
    }

    public void viewSchedule() {
        viewUnavailableDates();
        viewUpcomingAppts();
    }

    public void viewUnavailableDates() {
        List<UnavailableDate> unavailableDates = unavailableDateController.getUnavailableDates(currentDoc.getId());
        System.out.println("You are unavailable from:");
        for (UnavailableDate date : unavailableDates) {
            System.out.printf("%s to %s%n", DateFormat.formatNoTime(date.getStartDate()), DateFormat.formatNoTime(date.getEndDate()));
        }
        System.out.println();
    }

    public void viewUpcomingAppts() {
        List<Appointment> appointmentDates = appointmentController.getDoctorAppts(currentDoc.getId());
        LocalDateTime now = LocalDateTime.now();
    
        System.out.println("You have upcoming appointments with:");
        for (Appointment appt : appointmentDates) {
            if (appt.getApptDateTime().isAfter(now)) {
                System.out.printf("%s at %s%n", appt.getPatientId(), DateFormat.formatWithTime(appt.getApptDateTime()));
            }
        }
        System.out.println();
    }

    //
    public void addUnavailability(LocalDateTime startDateTime, LocalDateTime endDateTime){
        unavailableDateController.addUnavailablity(currentDoc.getId(), startDateTime, endDateTime);
    }

    public void acceptDeclineApptRequest(Appointment appt, boolean isAccepted){
        appointmentController.apptRequestDecision(appt, isAccepted);
    }

    public void recordApptOutcome(Appointment appt, String outcome){
        appointmentController.completeAppointment(appt, outcome);
    }

    public Patient getPatient(String patientId) {
        return patientRepository.get(patientId); // Only place where controller usage only is violated
    }

    // Update medical record by adding a new diagnosis, prescription, or treatment
    public void updatePatientMedicalRecord(Patient patient, String diagnosis, String prescription, String treatment) {
        
    }
}