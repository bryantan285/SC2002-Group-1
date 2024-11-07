package entity.user;
import entity.appointment.Appointment;
import java.time.*;
import java.util.*;

public class Doctor extends HospitalStaff {
    private ArrayList<Appointment> upcomingAppointments;
    private ArrayList<UnavailableDates> unavailableDates;

    public Doctor(boolean isPatient, String userId, String password, String name, String gender, int age) {
        super(isPatient, userId, password, name, gender, Role.DOCTOR, age);
    }

    public void viewSchedule(){
        for (UnavailableDates dates : unavailableDates){
            System.out.printf("You are unavailable from %s to %s%n", dates.getStartDateTime(), dates.getEndDateTime());
        }
        System.out.println();
        viewUpcomingAppts();
    }


    public void viewUpcomingAppts(){
        for (Appointment appt : upcomingAppointments){
            System.out.printf("You have an appointment with %s at %tF %<tT%n", appt.getPatientId(), appt.getApptDateTime());
        }
    }

    //
    public void setAvailabilityForAppts(LocalDateTime startTime, LocalDateTime endTime){
        UnavailableDates duration = new UnavailableDates(startTime, endTime);
        if (duration.isValidDuration()) {
            unavailableDates.add(duration);
            System.out.println("Unavailability successfully set");
        } else 
            System.out.println("Could not set unavability");
    }

    public void acceptDeclineApptRequest(Appointment appt, boolean isAccepted){
        if (isAccepted) {
            appt.setStatus(Appointment.Status.CONFIRMED);
            System.out.println("Appointment sucessfully confirmed.");
        }
        else {
            appt.setStatus(Appointment.Status.CANCELED);
            System.out.println("Appointment sucessfully declined.");
        }
    }

    public void recordApptOutcome(Appointment appt, String apptOutcome){
        appt.setApptOutcome(apptOutcome);
        appt.setStatus(Appointment.Status.COMPLETED);
    }

    public void viewPatientMedicalRecord(Patient patient) {
        
    }

    // Update medical record by adding a new diagnosis, prescription, or treatment
    public void updatePatientMedicalRecord(Patient patient, String diagnosis, String prescription, String treatment) {
        
    }
}
