package control.notification;

import entity.notification.Notification;
import entity.user.User;
import java.util.Comparator;
import java.util.List;
import repository.notification.NotificationRepository;

/**
 * Controller for managing notifications.
 */
public class NotificationController {

    private static final NotificationRepository notificationRepository = NotificationRepository.getInstance();

    /**
     * Creates a new notification for the specified user.
     *
     * @param userId  The ID of the user to receive the notification.
     * @param message The message content of the notification.
     */
    public static void createNotification(String userId, String message) {
        Notification newNotification = new Notification(notificationRepository.getNextClassId(), userId, message);
        notificationRepository.add(newNotification);
        notificationRepository.save();
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
                .filter(noti -> noti.getUserId().equals(user.getId()) || noti.getUserId().equals("A"))
                .sorted(Comparator.comparing(Notification::getDatetime))
                .toList();
        }

        return notificationRepository.findByField("userId", user.getId())
                .stream()
                .sorted(Comparator.comparing(Notification::getDatetime))
                .toList();
    }
}
