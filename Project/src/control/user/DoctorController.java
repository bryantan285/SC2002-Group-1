package control.user;

import control.appointment.AppointmentController;
import entity.appointment.Appointment;
import entity.user.Doctor;
import entity.user.HospitalStaff;
import entity.user.Patient;
import entity.user.UnavailableDate;
import interfaces.control.IController;
import java.time.LocalDateTime;
import java.util.List;
import repository.user.StaffRepository;
import utility.DateFormat;

public class DoctorController implements IController {
    
    private final StaffRepository staffRepository;
    private final UnavailableDateController unavailableDateController;
    private final AppointmentController appointmentController;
    private Doctor currentDoc;

    public static void main(String[] args) {
        DoctorController dc = new DoctorController();
        dc.setCurrentDoctor("D001"); // Testing
        dc.viewSchedule();
    }

    public DoctorController() {
        this.staffRepository = StaffRepository.getInstance();
        this.unavailableDateController = new UnavailableDateController();
        this.appointmentController = new AppointmentController();
        this.currentDoc = null;
    }

    @Override
    public void save() {
        staffRepository.save();
    }

    public void setCurrentDoctor(String doctorId) {
        HospitalStaff staff = staffRepository.findByField("id", doctorId).get(0);
        if (staff instanceof Doctor) {
            this.currentDoc = (Doctor) staff;
            System.out.println("Admin set successfully: " + this.currentDoc.getId());
        } else {
            System.out.println("Staff member with ID " + doctorId + " is not a Doctor.");
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
        System.out.println("You have an appointment with:");
        for (Appointment appt : appointmentDates){
            System.out.printf("%s at %s%n", appt.getPatientId(), DateFormat.formatWithTime(appt.getApptDateTime()));
        }
        System.out.println();
    }

    //
    public void addUnavailability(LocalDateTime startDateTime, LocalDateTime endDateTime){
        unavailableDateController.addUnavailablity(currentDoc.getId(), startDateTime, endDateTime);
    }

    public void acceptDeclineApptRequest(String apptId, boolean isAccepted){
        appointmentController.apptRequestDecision(apptId, isAccepted);
    }

    public void recordApptOutcome(String apptId, String outcome){
        appointmentController.completeAppointment(apptId, outcome);
    }

    public void viewPatientMedicalRecord(Patient patient) {
        
    }

    // Update medical record by adding a new diagnosis, prescription, or treatment
    public void updatePatientMedicalRecord(Patient patient, String diagnosis, String prescription, String treatment) {
        
    }
}