package prr.core;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private static final long serialVersionUID = 202208091753L;
    private String _key;
    private String _name;
    private int _taxNumber;
    private ClientLevel _level;
    private boolean _recieveNotifications;
    private Collection<Terminal> _associatedTerminals;
    private int _payments;
    private int _debts;
    //private TariffPlan

    public Client(String key, String name, int taxNumber){
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _level = ClientLevel.NORMAL;
        _recieveNotifications = true;
        _associatedTerminals = new ArrayList<>();
    }

    public void associateTerminals(Terminal t){
        _associatedTerminals.add(t);
    }

    public boolean booleanNotifications(){
        return _recieveNotifications;
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

    public void disableNotifications(){
        _recieveNotifications = false;
    }

    public String getKey(){return _key;}

    public String getName(){
        return _name;
    }

    public int getTaxNumber(){
        return _taxNumber;
    }

    public ClientLevel getClientLevel(){
        return _level;
    }

    public boolean getReceiveNotifications(){
        return _recieveNotifications;
    }


    @Override
    public String toString(){
        return "CLIENT|" + _key + "|" + _name + "|" + _taxNumber + "|"
        + _level + "|" + stringNotifications() + "|" + _associatedTerminals.size() + "|"
        + _payments + "|" + _debts;
    }
}
