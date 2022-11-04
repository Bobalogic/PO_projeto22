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
  private Map<Integer, Communication> _communicationSet;
  private long _payments;
  private long _debts;
  
  public Network(){
    _clientSet = new HashMap<>();
    _terminalSet = new HashMap<>();
    _communicationSet = new HashMap<>();
  }

  public long getPayments() {
    return _payments;
  }

  public long getDebts() {
    return _debts;
  }

  public Client getClient(String key) throws UnknownClientKeyException{
    Client temp = _clientSet.get(key);
    if (temp == null)
      throw new UnknownClientKeyException(key);
    return temp;
  }

  public Collection<Client> getClientsWithDebts() {
    ArrayList<Client> clientList = new ArrayList<>(_clientSet.values());
    clientList.removeIf(c -> c.getDebts() > 0);
    Collections.sort(clientList, new ClientDebtComparator());
    return clientList;
  }

  public Collection<Client> getClientsWithoutDebts() {
    ArrayList<Client> clientList = new ArrayList<>(_clientSet.values());
    clientList.removeIf(c -> c.getDebts() == 0);
    Collections.sort(clientList, new ClientComparator());
    return clientList;
  }

  public Collection<Communication> getClientCommunicationsFrom(String id) throws UnknownClientKeyException{
    Client c = getClient(id);
    ArrayList<Communication> comms = new ArrayList<>(c.getCommunicationsFrom());
    Collections.sort(comms, new CommunicationComparator());
    return comms;
  }

  public Collection<Communication> getClientCommunicationsTo(String id) throws UnknownClientKeyException{
    Client c = getClient(id);
    ArrayList<Communication> comms = new ArrayList<>(c.getCommunicationsTo());
    Collections.sort(comms, new CommunicationComparator());
    return comms;
  }

  public Collection<Terminal> getTerminalsWithPositiveBalance() {
    ArrayList<Terminal> twpb = new ArrayList<>(_terminalSet.values());
    twpb.removeIf(t -> t.getBalance() > 0);
    Collections.sort(twpb, new TerminalComparator());
    return twpb;
  }

  public Communication getCommunication(int id) {
    return _communicationSet.get(id);
  }

  public Collection<Communication> getAllCommunication() {
    return _communicationSet.values();
  }

  public boolean isTerminalBusy(Terminal t) {
    return t.getMode() == TerminalMode.BUSY;
  }

  public String showTerminalCommunication(Terminal t) {
    return t.showOngoingCommunication();
  }

  public String showClient(Client client) {
    return client.toString();
  }

  public Collection<Client> getAllClient() {
    ArrayList<Client> cl = new ArrayList<>(_clientSet.values());
    Collections.sort(cl, new ClientComparator());
    return cl;
  }

  public boolean disableClientNotifications(String id) throws UnknownClientKeyException {
    Client c = getClient(id);
    if(c.receivesNotifications()) {
      c.disableNotifications();
      return true;
    }
    return false;
  }

  public boolean enableClientNotifications(String id) throws UnknownClientKeyException {
    Client c = getClient(id);
    if(c.receivesNotifications())
      return false;
    c.enableNotifications();
    return true;
  }

  public long getClientPayments(String id) throws UnknownClientKeyException {
    Client c = getClient(id);
    return c.getPayments();
  }

  public long getClientDebts(String id) throws UnknownClientKeyException {
    Client c = getClient(id);
    return c.getDebts();
  }

  public void registerClient(String key, String name, int taxNum) throws DuplicateClientKeyException {
    if(_clientSet.containsKey(key)){
      throw new DuplicateClientKeyException(key);
    }
    Client newClient = new Client(key, name, taxNum);
    _clientSet.put(key, newClient);
  }

  public void clearClientNotifications(Client c) {
    c.clearNotifications();
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
    tl.removeIf(t -> !t.isUnused());
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

  public boolean silenceTerminal(Terminal t) {
    return t.setOnSilent();
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
    return terminal.turnOn();
  }

  public void sendTextCommunication(Terminal from, Terminal to, String message) {
    if(to.getMode() == TerminalMode.OFF) {
      return;
    }
    else if(from.canStartCommunication()) {
      int commNum = _communicationSet.size() + 1;
      _communicationSet.put(commNum, from.sendTextCommunication(commNum, to, message));
      _debts += _communicationSet.get(commNum).getPrice();
    }

  }

  public TerminalMode getTerminalMode(Terminal t) {
    return t.getMode();
  }

  public boolean supportsVideoCommunication(Terminal t) {
    return t.getType().equals("FANCY");
  }

  public void startInteractiveCommunication(Terminal from, Terminal to, String type) {
    int commNum = _communicationSet.size() + 1;
    _communicationSet.put(commNum, from.makeInteractiveCommunication(commNum, to, type));

  }

  public long endInteractiveCommunication(Terminal t, int duration) {
    long cost = t.turnOffInteractiveCommunication(duration);
    _debts += cost;
    return cost;
  }

  public void attemptedTextComm(Terminal from, Terminal to) {
    if(from.getClient().receivesNotifications())
      to.subscribeAttemptedTextComms(from);
  }

  public void attemptedInteractiveComm(Terminal from, Terminal to) {
    if(from.getClient().receivesNotifications())
      to.subscribeAttemptedInteractiveComms(from);
  }

  public boolean isCommFromTerminal(Communication c, Terminal t) {
    return c.getTerminalFrom().equals(t);
  }

  public boolean performPayment(Integer commId, Terminal t) {
    if(!_communicationSet.containsKey(commId))
      return false;

    Communication c = _communicationSet.get(commId);
    if(isCommFromTerminal(c, t)) {
      long cost = c.pay();
      _debts -= cost;
      _payments += cost;
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

