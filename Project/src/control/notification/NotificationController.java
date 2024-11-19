package control.notification;

import entity.notification.Notification;
import entity.user.HospitalStaff;
import entity.user.User;
import interfaces.observer.IObserver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repository.notification.NotificationRepository;
import repository.user.StaffRepository;
import utility.DateFormat;

/**
 * Controller for managing notifications.
 */
public class NotificationController {

    private static final NotificationRepository notificationRepository = NotificationRepository.getInstance();
    private static NotificationController instance;
    private final Map<String, IObserver> observers;

    public NotificationController() {
        this.observers = new HashMap<>();
    }

    public static NotificationController getInstance() {
        if (instance == null) {
            instance = new NotificationController();
        }
        return instance;
    }

    /**
     * Creates a new notification for the specified user.
     *
     * @param userId  The ID of the user to receive the notification.
     * @param message The message content of the notification.
     */
    public static Notification createNotification(String userId, String message) {
        Notification newNotification = new Notification(notificationRepository.getNextClassId(), userId, message);
        notificationRepository.add(newNotification);
        notificationRepository.save();
        return newNotification;
    }

    /**
     * Retrieves a list of notifications for the specified user, sorted by datetime.
     * Admin users (ID starting with "A") receive both general and user-specific notifications.
     *
     * @param user The user whose notifications are to be retrieved.
     * @return A sorted list of notifications for the user.
     */
    public static List<Notification> getNotificationByUser(User user) {
        if (user.getId().startsWith("A")) {
            return notificationRepository.toList()
                .stream()
                .filter(noti -> noti.getUserId().equals(user.getId()) || noti.getUserId().equals("ADMIN")
                && Boolean.FALSE.equals(noti.getReadStatus())) // Filter unread notifications
                .sorted(Comparator.comparing(Notification::getDatetime).reversed())
                .toList();
        }

        return notificationRepository.findByField("userId", user.getId())
                .stream()
                .filter(noti -> Boolean.FALSE.equals(noti.getReadStatus())) // Filter unread notifications
                .sorted(Comparator.comparing(Notification::getDatetime).reversed())
                .toList();
    }

    public void markNotificationsRead(String userId) {
        List<Notification> userNotifications = notificationRepository.findByField("userId", userId);
        userNotifications.forEach(notification -> notification.setReadStatus(true));
        notificationRepository.save();
    }

    /**
     * Notifies all admin users about a specific message.
     *
     * @param message The message content to notify the admins.
     */
    public void notifyAdmins(String message) {
        List<HospitalStaff> admins = getAllAdmins(); // Assumes a method exists to retrieve all admin users
        for (User admin : admins) {
            Notification newNoti = createNotification(admin.getId(), message);
            IObserver observer = observers.get(admin.getId());
            if (observer != null) {
                List<String> newEntry = new ArrayList<>();
                newEntry.add(newNoti.getMessage());
                newEntry.add(DateFormat.formatWithTime(newNoti.getDatetime()));
                observer.notify(newEntry);
            }
        }
    }

    /**
     * Retrieves all admin users.
     *
     * @return A list of users whose IDs start with "A".
     */
    private List<HospitalStaff> getAllAdmins() {
        // Replace with appropriate repository or user management logic
        // Assuming `UserRepository` is a singleton handling all users
        return StaffRepository.getInstance().toList()
                .stream()
                .filter(user -> user.getId().startsWith("A"))
                .toList();
    }

    public void registerObserver(String id, IObserver observer) {
        observers.put(id, observer);
    }

    public void removeObserver(String id) {
        observers.remove(id);
    }

    public void notifyObserver(String id, String message) {
        Notification newNoti = createNotification(id, message);
        IObserver observer = observers.get(id);
        if (observer != null) {
            List<String> newEntry = new ArrayList<>();
            newEntry.add(newNoti.getMessage());
            newEntry.add(DateFormat.formatWithTime(newNoti.getDatetime()));
            observer.notify(newEntry);
        }
    }

}
