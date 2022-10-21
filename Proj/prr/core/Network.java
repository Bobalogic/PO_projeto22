package prr.core;

import java.io.Serializable;
import java.io.IOException;

import prr.app.exception.DuplicateClientKeyException;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.UnrecognizedEntryException;
import java.util.*;
import java.util.HashMap;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private Map<String, Client> _clientSet;
  private Map<String, Terminal> _terminalSet;
  
  public Network(){
    _clientSet = new HashMap<String, Client>();
    _terminalSet = new HashMap<String, Terminal>();
  }

  public Client getClient(String key) throws UnknownClientKeyException{
    Client temp = _clientSet.get(key);
    if (temp == null)
      throw new UnknownClientKeyException(key);
    return temp;
  }

  public String showClient(Client client) {
    return client.toString();
  }

  public Collection<Client> getAllClient() {
        return new ArrayList<Client>(_clientSet.values());
  }

  public void registerClient(String key, String name, int taxNum) throws DuplicateClientKeyException {
    if(_clientSet.containsKey(key)){
      throw new DuplicateClientKeyException(key);
    }
    Client newClient = new Client(key, name, taxNum);
    _clientSet.put(key, newClient);
  }

  public Terminal getTerminal(String id) throws UnknownTerminalKeyException {
    Terminal temp = _terminalSet.get(id);
    if (temp == null)
      throw new UnknownTerminalKeyException(id);
    return temp;
  }

  public Collection<Terminal> getAllTerminal() {
    return new ArrayList<Terminal>(_terminalSet.values());
  }

  public Terminal registerTerminal(String id, String type) throws DuplicateTerminalKeyException{
    if(_terminalSet.containsKey(id)) {
      throw new DuplicateTerminalKeyException(id);
    }
    Terminal terminal = new Terminal(id, type);
    _terminalSet.put(id, terminal);
    return terminal;
  }

  public void addFriend(String id1, String id2) throws UnknownTerminalKeyException {
    Terminal terminal = getTerminal(id1);
    Terminal friend = getTerminal(id2);
    if(!terminal.isFriendsWith(friend)){
      terminal.makeFriend(friend);
    }
  }

  public String showTerminal(Terminal t) {
    return t.toString();
  }

  public boolean turnOffTerminal(Terminal terminal){
    if(terminal.turnOff()){
      return true;
    }
    return false;
  }

  public boolean turnONTerminal(Terminal terminal){
    if(terminal.turnOn()){
      return true;
    }
    return false;
  }


  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
  }
}

