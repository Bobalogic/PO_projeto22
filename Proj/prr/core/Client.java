package prr.core;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable, ClientObserver {
    private static final long serialVersionUID = 202208091753L;
    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevelState _level;
    private boolean _recieveNotifications;
    private Collection<Terminal> _associatedTerminals;
    private Collection<Notification> _notificationList;
    private long _payments;
    private long _debts;
    private int _textCounter;
    private int _videoCounter;
    //private TariffPlan

    public Client(String key, String name, int taxNumber){
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _level = ClientLevelNormalState.getClientLevelState();
        _recieveNotifications = true;
        _associatedTerminals = new ArrayList<>();
        _notificationList = new ArrayList<>();
    }

    public void associateTerminals(Terminal t){
        _associatedTerminals.add(t);
    }

    public String stringNotifications(){
        if(_recieveNotifications){
            return "YES";
        }
        return "NO";
    }

    public void enableNotifications(){
        _recieveNotifications = true;
    }

    public void disableNotifications() {
        _recieveNotifications = false;
    }

    public boolean receivesNotifications() {
        return _recieveNotifications;
    }

    public String getKey(){return _key;}

    public long getPayments() {
        return _payments;
    }

    public long getDebts() {
        return _debts;
    }

    public String getName(){
        return _name;
    }

    public int getTaxNumber(){
        return _taxNumber;
    }

    public String getClientLevel(){
        return _level.toString();
    }

    public int getNumTerminals() {
        return _associatedTerminals.size();
    }

    public ArrayList<Communication> getCommunicationsFrom() {
        ArrayList<Communication> cf = new ArrayList<>();
        for(Terminal t: _associatedTerminals) {
            cf.addAll(t.getCommunicationsFrom());
        }
        return cf;
    }

    public ArrayList<Communication> getCommunicationsTo() {
        ArrayList<Communication> cf = new ArrayList<>();
        for(Terminal t: _associatedTerminals) {
            cf.addAll(t.getCommunicationsTo());
        }
        return cf;
    }

    public Collection<Notification> getNotifications() {
        return Collections.unmodifiableCollection(_notificationList);
    }

    public void clearNotifications() {
        _notificationList.clear();
    }

    public int getTextCommCost(String message) {
        return _level.calculateMessageCost(message);
    }

    public int getVoiceCommCost(int duration) {
        return _level.calculateVoiceCommCost(duration);
    }

    public int getVideoCommCost(int duration) {
        return _level.calculateVideoCommCost(duration);
    }

    public void updateDebt(long cost) {
        _debts += cost;
        if(_payments-_debts < 0)
            _level = ClientLevelNormalState.getClientLevelState();
    }

    public void pay(long cost) {
        _payments += cost;
        _debts -= cost;
        if(_payments-_debts > 500)
            _level = ClientLevelGoldState.getClientLevelState();
    }

    @Override
    public void update(Notification n1) {
        for(Notification n2: _notificationList) {
            if (n1.getTo().equals(n2.getTo()))
                return;
        }
        _notificationList.add(n1);
    }

    public void incTextCounter() {
        _textCounter += 1;
        _videoCounter = 0;
        if(_level.getClientLevel() == ClientLevel.PLATINUM && _textCounter >= 2)
            _level = ClientLevelGoldState.getClientLevelState();
    }

    public void incVoiceCounter() {
        _textCounter = 0;
        _videoCounter = 0;
    }

    public void incVideoCounter() {
        _textCounter = 0;
        _videoCounter += 1;
        if(_level.getClientLevel() == ClientLevel.GOLD && _videoCounter>=5)
            _level = ClientLevelPlatinumState.getClientLevelState();
    }

    public void verifyBalance() {
        if(_payments-_debts < 0)
            _level = ClientLevelNormalState.getClientLevelState();
    }

    @Override
    public String toString(){
        return "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|"
        + _level + "|" + stringNotifications() + "|" + _associatedTerminals.size() + "|"
        + _payments + "|" + _debts;
    }
}
