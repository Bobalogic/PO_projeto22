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

  public Collection<Client> showAllClient() {
    ArrayList<Client> cl = new ArrayList<>(_clientSet.values());
    Collections.sort(cl, new ClientComparator());
    return cl;
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
    ArrayList<Terminal> tl = new ArrayList<>(_terminalSet.values());
    Collections.sort(tl, new TerminalComparator());
    return tl;
  }

  public Collection<Terminal> getAllUnusedTerminal() {
    ArrayList<Terminal> tl = new ArrayList<>(_terminalSet.values());
    for(Terminal t: tl) {
      if(!t.isUnused())
        tl.remove(t);
    }
    Collections.sort(tl, new TerminalComparator());
    return tl;
  }

  public Terminal registerTerminal(String id, String type, String clientID) throws
          DuplicateTerminalKeyException,UnknownClientKeyException{
    if(_terminalSet.containsKey(id)) {
      throw new DuplicateTerminalKeyException(id);
    }
    Client client = getClient(clientID);
    Terminal terminal = new Terminal(id, type, client);
    _terminalSet.put(id, terminal);
    client.associateTerminals(terminal);
    return terminal;
  }

  public void addFriend(String id1, String id2) throws UnknownTerminalKeyException {
    Terminal terminal = getTerminal(id1);
    Terminal friend = getTerminal(id2);

    if(!terminal.isFriendsWith(friend) && terminal != friend)
      terminal.addFriend(friend);
  }

  public void removeFriend(String id1, String id2) throws UnknownTerminalKeyException {
    Terminal terminal = getTerminal(id1);
    Terminal friend = getTerminal(id2);

    if(terminal.isFriendsWith(friend))
      terminal.removeFriend(friend);
  }

  public String showTerminal(Terminal t) {
    return t.toString();
  }

  public boolean turnOffTerminal(Terminal terminal){
    return terminal.turnOff();
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
  void importFile(String filename) throws UnrecognizedEntryException, IOException, UnknownClientKeyException   {
    Parser _parser = new Parser(this);
    _parser.parseFile(filename);
  }
}

