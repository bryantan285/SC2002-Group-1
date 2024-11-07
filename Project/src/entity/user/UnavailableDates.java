package entity.user;
import java.time.LocalDateTime;

public class UnavailableDates {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public UnavailableDates(LocalDateTime start, LocalDateTime end) {
        this.startDateTime = start;
        this.endDateTime = end;
    }

    public boolean isValidDuration(){
        if (startDateTime.isAfter(endDateTime)) return false;
        return true;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
