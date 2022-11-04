package prr.core;

import java.io.Serializable;

/**
 * implementation of design patter Singleton
 */
public class ClientLevelNormalState implements ClientLevelState, Serializable  {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;
    private static ClientLevelNormalState _cls;

    private ClientLevelNormalState() {
        _cls = this;
    }

    public static ClientLevelNormalState getClientLevelState() {
        if (_cls == null) {
            new ClientLevelNormalState();
            return _cls;
        } else
            return _cls;
    }

    public int calculateMessageCost(String message) {
        int MessageLenght = message.length();
        if(MessageLenght < 50)
            return 10;
        else if(MessageLenght >= 100)
            return MessageLenght*2;
        else
            return 16;
    }

    public int calculateVoiceCommCost(int duration) {
        return 20*duration;
    }

    public int calculateVideoCommCost(int duration) {
        return 30*duration;
    }

    public String toString() {
        return "NORMAL";
    }
}
