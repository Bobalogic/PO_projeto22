package prr.core;

public class Notification {
    private NotificationType _type;
    private Terminal _to;

    public Terminal getTo() {
        return _to;
    }

    public Notification(NotificationType type, Terminal to){
        _type = type;
        _to = to;
    }

    public String toString() {
        return _type + "|" + _to.getId();
    }
}
