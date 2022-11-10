package prr.core;

import java.io.Serializable;

/**
 * implementation of design pattern Singleton
 */
public class ClientLevelPlatinumState implements ClientLevelState, Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    private static ClientLevelPlatinumState _cls;

    private ClientLevelPlatinumState() {
        _cls = this;
    }

    public static ClientLevelPlatinumState getClientLevelState() {
        if(_cls == null) {
            new ClientLevelPlatinumState();
            return _cls;
        }
        else return _cls;
    }

    public int calculateMessageCost(String message) {
        int MessageLenght = message.length();
        if(MessageLenght < 50)
            return 0;
        else if(MessageLenght >= 100)
            return 4;
        else
            return 4;
    }

    public int calculateVoiceCommCost(int duration) {
        return 10*duration;
    }

    public int calculateVideoCommCost(int duration) {
        return 10*duration;
    }

    public ClientLevel getClientLevel() {
        return ClientLevel.PLATINUM;
    }

    public String toString() {
        return "PLATINUM";
    }
}
