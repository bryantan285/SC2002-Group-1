package observer;

import control.notification.NotificationController;
import entity.notification.Notification;
import entity.user.User;
import interfaces.observer.IObserver;
import java.util.ArrayList;
import java.util.List;
import utility.DateFormat;

public class NotificationObserver implements IObserver {
    
    private final User user;
    private final List<List<String>> notificationHistory;

    public NotificationObserver(User user) {
        this.notificationHistory = new ArrayList<>();
        this.user = user;
    }

    @Override
    public void notify(List<String> message) {
        notificationHistory.add(message);
    }

    @Override
    public List<List<String>> getNotificationHistory() {
        return notificationHistory;
    }

    @Override
    public void setNotificationHistory() {
        List<Notification> notifications = NotificationController.getNotificationByUser(user);
        for (Notification noti : notifications) {
            List<String> newEntry = new ArrayList<>();
            newEntry.add(noti.getMessage());
            newEntry.add(DateFormat.formatWithTime(noti.getDatetime()));
            notify(newEntry);
        }
    }
}
