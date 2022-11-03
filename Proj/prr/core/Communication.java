package prr.core;

public abstract class Communication{
    private int _id;
    private long _cost;
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

    public int getId() {
        return _id;
    }

    public long getPrice() {
        if(_cost == -1)
            return 0;
        return _cost;
    }

    /**
     *
     * @param newCost
     * @return the new cost or the old if it has been already changed
     */
    public long updateCost(int newCost) {
        if(_cost == -1) {
            if(_from.isFriendsWith(_to))
                newCost /= 2;
            _cost = newCost;
        }
        return _cost;
    }
}