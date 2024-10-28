public class Doctor extends User{
    private ArrayList<Appointment> upcomingAppointments;

    public Doctor(Role userRole, String userId, String userPassword){
        super(Role.DOCTOR, userId, userPassword);
    }


    public void viewSchedule(){
        for (Appointment appt : upcomingAppointments){
            System.out.printf("You have an appointment with %s at %tF %<tT%n", appt.getPatientId(), appt.getApptDate());
        }
    }

    public void setAvailabilityForAppts(Date )[

    ]

    public void acceptDeclineApptRequest(Appointment appt, boolean apptStatus){
        appt.setStatus(apptStatus);
    }
    public void recordApptOutcome(Appointment appt, String doctorsNotes){
        appt.setApptOutcome(doctorsNotes);
    }
}
