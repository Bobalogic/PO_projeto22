package prr.core;

public class Notification {
    private NotificationType _type;
    private Terminal _from;
    private Terminal _to;

    public Notification(NotificationType type, Terminal from, Terminal to){
        _type = type;
        _from = from;
        _to = to;
    }
}
