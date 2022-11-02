package prr.core;

/**
 * implementation of design pattern Singleton
 */
public class ClientLevelGoldState implements ClientLevelState{
    private static ClientLevelGoldState _cls;

    private ClientLevelGoldState() {
        _cls = this;
    }

    public static ClientLevelGoldState getClientLevelState() {
        if(_cls == null) {
            new ClientLevelGoldState();
            return _cls;
        }
        else return _cls;
    }

    public int calculateMessageCost(String message) {
        int MessageLenght = message.length();
        if(MessageLenght < 10)
            return 10;
        else if(MessageLenght >= 100)
            return MessageLenght*2;
        else
            return 10;
    }

    public int calculateVoiceCommCost(int duration) {
        return 10*duration;
    }

    public int calculateVideoCommCost(int duration) {
        return 20*duration;
    }

    public String toString() {
        return "GOLD";
    }
}
