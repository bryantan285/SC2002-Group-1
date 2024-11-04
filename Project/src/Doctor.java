import java.util.*;
import java.time.*;

public class Doctor extends HospitalStaff{
    private ArrayList<Appointment> upcomingAppointments;
    private ArrayList<UnavailableDates> unavailableDates;

    public Doctor(boolean isPatient, String userId, String name, boolean gender, int age){
        super(isPatient, userId, name, gender, HospitalStaff.Role.DOCTOR, age);
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
            appt.setStatus(Appointment.Status.REJECTED);
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
