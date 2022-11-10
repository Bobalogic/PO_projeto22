package prr.core;

import java.io.Serial;
import java.io.Serializable;

public class Notification implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
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
