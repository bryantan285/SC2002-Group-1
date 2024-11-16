package repository.notification;


import entity.notification.Notification;
import java.io.IOException;
import repository.Repository;

public class NotificationRepository extends Repository<Notification> {
    
    private static NotificationRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\Notification_List.csv";
    private static final String PREFIX = "NOTI";


    private NotificationRepository() throws IOException {
        super();
        load();
    }
    
    public static NotificationRepository getInstance() {
        if (repo == null) {
            try {
                repo = new NotificationRepository();
            } catch (IOException e) {
                System.err.println("Failed to create NotificationRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize NotificationRepository", e);
            }
        }
        return repo;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<Notification> getEntityClass() {
        return Notification.class;
    }
}
