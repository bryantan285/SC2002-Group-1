package entity.user;
import entity.EntityObject;
import java.time.LocalDateTime;

public class UnavailableDate extends EntityObject {
    private String id;
    private String doctorId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public UnavailableDate() {

    }

    public UnavailableDate(String id, String doctorId, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.doctorId = doctorId;
        this.startDate = start;
        this.endDate = end;
    }

    

    public static boolean isValidDuration(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) return false;
        return true;
    }

    public LocalDateTime getstartDate() {
        return startDate;
    }

    public LocalDateTime getendDate() {
        return endDate;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
