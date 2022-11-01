package prr.app.client;

public interface clientLevelState {
    int calculateTextCost(String message);

    int calculateVoiceCommCost(int duration);

    int calculateVideoCommCost(int duration);
}
