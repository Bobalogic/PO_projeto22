package prr.core;

public class Notification {
    private NotificationType _type;
    private Terminal _to;

    public Notification(NotificationType type, Terminal to){
        _type = type;
        _to = to;
    }
}
