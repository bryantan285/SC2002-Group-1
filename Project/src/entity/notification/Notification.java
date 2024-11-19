package entity.notification;

import entity.EntityObject;
import java.time.LocalDateTime;

public class Notification extends EntityObject {
    private String id;
    private String userId;
    private String message;
    private LocalDateTime datetime;
    private Boolean read;

    public Notification() {

    }

    public Notification(String id, String userId, String message) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.datetime = LocalDateTime.now();
        this.read = false;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Boolean getReadStatus() {
        return read;
    }

    public void setReadStatus(Boolean read) {
        this.read = read;
    }
}
