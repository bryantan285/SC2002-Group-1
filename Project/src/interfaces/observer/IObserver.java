package interfaces.observer;

import java.util.List;

public interface IObserver {
    public void notify(List<String> message);
    public List<List<String>> getNotificationHistory();
    public void setNotificationHistory();
}
