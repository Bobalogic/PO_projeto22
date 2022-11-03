package prr.core;

public abstract class Communication{
    private int _id;
    private float _cost;
    private Terminal _from;
    private Terminal _to;
    private boolean _isOnGoing;

    public Communication(int id, Terminal from, Terminal to) {
        _id = id;
        _from = from;
        _to = to;
        _cost = -1;
    }

    public Terminal getTerminalFrom() {
        return _from;
    }

    public Terminal getTerminalTo() {
        return _to;
    }
    public void updateCost(int newCost) {
        if(_cost == -1) {
            if(_from.isFriendsWith(_to))
                newCost /= 2;
            _cost = newCost;
        }
    }
}