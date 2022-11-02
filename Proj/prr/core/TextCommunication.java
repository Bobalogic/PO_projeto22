package prr.core;

public class TextCommunication extends Communication {
    private String _message;

    public TextCommunication(int id, Terminal from, Terminal to, String message) {
        super(id, from, to);
        _message = message;
    }

    public String getMessage() {
        return _message;
    }
}
