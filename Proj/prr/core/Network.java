package prr.core;

import java.io.Serializable;
import java.io.IOException;

import prr.app.exception.DuplicateClientKeyException;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.exception.UnrecognizedEntryException;
import java.util.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private Set<Client> _clientSet;
  private Set<Terminal> _terminalSet;
  
  public Network(){
    _clientSet = new HashSet<>();
    _terminalSet = new HashSet<>();
  }

  public Client findClient(String id){
    Iterator<Client> it = _clientSet.iterator();
    Client client;
    while(it.hasNext()) {
      client = it.next();
      if(id == client.getName()) {
        return client;
      }
    }
    return null;
  }

  public Client getClient(String key) throws UnknownClientKeyException {
    Client c = findClient(key);
    if(c == null) {
      throw new UnknownClientKeyException(key);
    }
    return c;
  }

  public String showClient(Client client) {
    return client.toString();
  }

  public Collection<Client> getAllClient() {
        return new ArrayList<Client>(_clientSet);
  }

  public void registerClient(String key, String name, int taxNum) throws DuplicateClientKeyException {
    if(findClient(key) != null){
      throw new DuplicateClientKeyException(key);
    }
    Client newClient = new Client(key, name, taxNum);
    _clientSet.add(newClient);
  }

  public Terminal findTerminal(String id) {
    Iterator<Terminal> it = _terminalSet.iterator();
    Terminal terminal;
    while(it.hasNext()) {
      terminal = it.next();
      if(id == terminal.getId()) {
        return terminal;
      }
    }
    return null;
  }

  public Terminal getTerminal(String id) throws UnknownTerminalKeyException {
    Terminal t = findTerminal(id);
    if(t == null) {
      throw new UnknownTerminalKeyException(id);
    }
    return t;
  }

  public Terminal registerTerminal(String id, String type) throws DuplicateTerminalKeyException{
    if(type == "FANCY") {
      registerFancyTerminal(id);
    }
    registerBasicTerminal(id);
    Terminal t = null;
    try{
      t =  getTerminal(id);
    }catch(UnknownTerminalKeyException exe) {
      exe = new UnknownTerminalKeyException(id);
    }
    return t;
  }

  public void registerBasicTerminal(String id) throws DuplicateTerminalKeyException {
    if(findTerminal(id)!=null) {
      throw new DuplicateTerminalKeyException(id);
    }
    Terminal newTerminal = new BasicTerminal(id);
    _terminalSet.add(newTerminal);
  }

  public void registerFancyTerminal(String id) throws DuplicateTerminalKeyException {
    if(findTerminal(id)!=null) {
      throw new DuplicateTerminalKeyException(id);
    }
    Terminal newTerminal = new FancyTerminal(id);
    _terminalSet.add(newTerminal);
  }

  public void addFriend(String id1, String id2) throws UnknownTerminalKeyException {
    Terminal terminal = getTerminal(id1);
    Terminal friend = getTerminal(id2);
    if(!terminal.isFriendsWith(friend)){
      terminal.makeFriend(friend);
    }
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

