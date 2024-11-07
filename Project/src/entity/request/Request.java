package entity.request;

import entity.EntityObject;
import java.util.Date;

public abstract class Request extends EntityObject {
    public enum STATUS {
        PENDING,
        REJECTED,
        APPROVED
    }

    private String id;
    private String requestorId;
    private String approverId;
    private STATUS status;
    private Date timeCreated;
    private Date timeModified;

    public Request() {

    }

    public Request(String id, String requestorId, String approverId, STATUS status, Date timeCreated, Date timeModified) {
        this.id = id;
        this.requestorId = requestorId;
        this.approverId = approverId;
        this.status = status;
        this.timeCreated = timeCreated;
        this.timeModified = timeModified;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimeModified() {
        return timeModified;
    }

    public void setTimeModified(Date timeModified) {
        this.timeModified = timeModified;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    
}
