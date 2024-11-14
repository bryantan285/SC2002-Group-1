package entity.user;
import entity.EntityObject;
import java.time.LocalDateTime;

public class UnavailableDate extends EntityObject {
    private String id;
    private String staffId;
    private LocalDateTime date;

    public UnavailableDate() {

    }

    public UnavailableDate(String id, String staffId, LocalDateTime date) {
        this.id = id;
        this.staffId = staffId;
        this.date = date;
    }

    

    public static boolean isValidDuration(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) return false;
        return true;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getstaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
