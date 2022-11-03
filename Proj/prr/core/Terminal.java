package prr.core;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.*;
import java.lang.Math;

//import prr.core.TerminalMode;
//import prr.core.Communication;
//import prr.core.Client;
//import prr.core.Notification;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)
public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private String _id;
  private String _type;
  private double _debt;
  private double _payments;
  private boolean _isSilent;
  private boolean _recievesNotifications;
  private TerminalMode _mode;
  private Collection<Communication> _from;
  private Collection<Communication> _to;
  private InteractiveCommunication _ongoingCommunication;
  private Client _client;
  private Collection<Terminal> _friends;
  private Collection<Communication> _commMade;
  private Collection<Terminal> _attemptedTextCommunications;
  private Collection<Terminal> _attemptedInteractiveCommunications;

  //construtor
  public Terminal(String id, String type, Client client){
    _id = id;
    _type = type;
    _debt = 0;
    _payments = 0;
    _recievesNotifications = true;
    _isSilent = false;
    _mode = TerminalMode.IDLE;
    _from = new ArrayList<>();
    _to = new ArrayList<>();
    _client = client;
    _friends = new ArrayList<>();
    _commMade = new ArrayList<>();
    _attemptedTextCommunications = new ArrayList<>();
    _attemptedInteractiveCommunications = new ArrayList<>();
  }

  public TerminalMode getMode(){
    return _mode;
  }

  public String getId() {
    return _id;
  }

  public String getType() {
    return _type;
  }

  public Client getClient() {
    return _client;
  }

  public boolean isUnused() {
    return _commMade.size()==0;
  }

  public boolean recievesNotifications() {
    return _recievesNotifications;
  }
  public boolean isSilent() {
    return _isSilent;
  }

  @Override
  public String toString() {
    return _type + "|" + _id + "|" + _client.getKey() + "|" + _mode + "|" + (int)Math.round(_payments) + "|"
            + (int)Math.round(_debt) + showFriends();
  }

  //not completed yet
  public String showFriends() {
    ArrayList<Terminal> fl = new ArrayList<>(_friends); //create a copy
    Collections.sort(fl, new TerminalComparator());     //sort the copy by id


    boolean flag = false; //flag to check if it is first friend in list in order to add either a "|" or a ","
    String s = ""; //string to return;

    for(Terminal t: fl) {
      if(flag) {  //if its not the first element there will be added a "," to separate friends
        s = s + ",";
        s = s + t.getId();
      }
      else {      //if it is the first element there will be added a "|" to seperate friends
        s = s + "|";
        s = s + t.getId();
        flag = true;
      }
    }
    return s;
  }

  public void associateClient(Client c){
    _client = c;
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    return _ongoingCommunication != null && _ongoingCommunication.getTerminalFrom().equals(this);
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    return _mode != TerminalMode.OFF && _mode != TerminalMode.BUSY;
  }

  public boolean canReceiveCommunications() {
    return _mode == TerminalMode.IDLE;
  }

  public TextCommunication sendTextCommunication(int id, Terminal to, String message) {
    TextCommunication tc = new TextCommunication(id, this, to, message);
    _from.add(tc);
    to.recieveTextCommunication(tc);
    tc.updateCost(_client.getTextCommCost(message));
    return tc;
  }

  public void recieveTextCommunication(TextCommunication tc) {
    _to.add(tc);
  }
  public InteractiveCommunication makeInteractiveCommunication(int id, Terminal to, String type) {
    _mode = TerminalMode.BUSY;
    InteractiveCommunication ic = new InteractiveCommunication(id, this, to, type);
    _ongoingCommunication = ic;
    _from.add(ic);
    to.recieveInteractiveCommunication(ic);
    return ic;
  }

  public void recieveInteractiveCommunication(InteractiveCommunication ic) {
    _mode = TerminalMode.BUSY;
    _ongoingCommunication = ic;
    _to.add(ic);
  }

  public void turnOffInteractiveCommunication(int duration) {
    if(_isSilent) {
      notifyObservers(getNotificationType(_mode, TerminalMode.SILENCE));
      _mode = TerminalMode.SILENCE;
    }
    else {
      notifyObservers(getNotificationType(_mode, TerminalMode.IDLE));
      _mode = TerminalMode.IDLE;
    }
    switch (_type) {
      case "VOICE" -> _ongoingCommunication.updateCost(_client.getVoiceCommCost(duration));
      case "VIDEO" -> _ongoingCommunication.updateCost(_client.getVideoCommCost(duration));
    }


    _ongoingCommunication.addDuration(duration);
    _ongoingCommunication.getTerminalTo().endInteractiveCommunication();
    _ongoingCommunication = null;
  }
  public void endInteractiveCommunication() {
    notifyObservers(getNotificationType(_mode, TerminalMode.IDLE));
    _mode = TerminalMode.IDLE;
    _ongoingCommunication = null;
  }

  public boolean setOnIdle(){//ADD quando vai para idle ou silent deve resolver as notificacoes
    if(_mode==TerminalMode.IDLE){
      return false;
    }
    notifyObservers(getNotificationType(_mode, TerminalMode.IDLE));
    _mode = TerminalMode.IDLE;
    return true;
  }

  public boolean setOnSilent(){
    if(_mode==TerminalMode.BUSY || _mode==TerminalMode.IDLE){
      TerminalMode modeBefore = _mode;
      _mode = TerminalMode.SILENCE;
      notifyObservers(getNotificationType(modeBefore, TerminalMode.SILENCE));
      return true;
    }
    return false;
  }

  public boolean turnOff(){
    if(_mode==TerminalMode.IDLE || _mode==TerminalMode.SILENCE){
      notifyObservers(getNotificationType(_mode, TerminalMode.OFF));
      _mode = TerminalMode.OFF;
      return true;
    }
    else if(_mode==TerminalMode.BUSY){
      return false;
    }
    return false;
  }

  public boolean turnOn(){
    if(_mode != TerminalMode.OFF){
      return false;
    }
    return setOnIdle();
  }

  public void addFriend(Terminal other){
    _friends.add(other);
  }

  public void removeFriend(Terminal other){
    _friends.remove(other);
  }

  public boolean isFriendsWith(Terminal t) {
    return _friends.contains(t);
  }

  /**
   * subscribes an observer to the attempted communications
  * */
  public void subscribeAttemptedInteractiveComms (Terminal t) {
    _attemptedInteractiveCommunications.add(t);
  }

  public void subscribeAttemptedTextComms (Terminal t) {
    _attemptedTextCommunications.add(t);
  }

  public Notification getNotificationType(TerminalMode before, TerminalMode after) {
    if(before == TerminalMode.OFF && after == TerminalMode.IDLE)
      return new Notification(NotificationType.O2I, this);
    else if(before == TerminalMode.OFF && after == TerminalMode.SILENCE)
      return new Notification(NotificationType.O2S, this);
    else if(before == TerminalMode.BUSY && after == TerminalMode.IDLE)
      return new Notification(NotificationType.B2I, this);
    else//(before == TerminalMode.SILENT && after == TerminalMode.IDLE)
      return new Notification(NotificationType.S2I, this);
  }

  /**
   * Notificates all the subscriber in the list and resets it after
  * */
  public void notifyObservers(Notification n) {
    for(Terminal t: _attemptedInteractiveCommunications) {
      t.getClient().update(n);
    }
    _attemptedInteractiveCommunications.clear();
    notifyObserversTextComms(n);
  }

  public void notifyObserversTextComms(Notification n) {
    for(Terminal t: _attemptedTextCommunications) {
      t.getClient().update(n);
    }
  }
}