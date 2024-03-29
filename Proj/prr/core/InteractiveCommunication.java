package prr.core;

public class InteractiveCommunication extends Communication {
    private int _duration;
    private String _state;

    public InteractiveCommunication(int id, Terminal from, Terminal to, String type) {
        super(id, from, to, type);
        _duration = -1;     //duration set to -1 in order to check if its has been changed already
        _state = "ONGOING";
    }

    public int getDuration() {
        if(_duration == -1)
            return 0;
        else
            return _duration;
    }

    /**
     * add the communication duration after it has been concluded (the duration can be changed only once)
     * @param newDuration
     * @return true if duration is being altered for the first time, false otherwise
     */
    public boolean addDuration(int newDuration) {
        if(_duration == -1) {
            _duration = newDuration;
            return true;
        }
        else
            return false;
    }

    public boolean isOver() {
        return _state.equals("FINISHED");
    }

    public String toString() {
        if(_duration!=-1)
            _state = "FINISHED";
        return getType() + "|" + super.getId() + "|" + super.getTerminalFrom().getId()
                + "|" + super.getTerminalTo().getId() + "|" + getDuration() + "|"
                + super.getPrice() + "|" + _state;
    }
}
