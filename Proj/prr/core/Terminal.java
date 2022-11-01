package prr.core;

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
  private TerminalMode _mode;
  private Communication _from;
  private Communication _to;
  private Client _client;
  private Collection<Terminal> _friends;
  private Collection<Communication> _commMade;
  private Collection<Terminal> _attemptedCommunications;

  //construtor
  public Terminal(String id, String type, Client client){
    _id = id;
    _type = type;
    _debt = 0;
    _payments = 0;
    _mode = TerminalMode.IDLE;
    _from = null;
    _to = null;
    _client = client;
    _friends = new ArrayList<>();
    _commMade = new ArrayList<>();
    _attemptedCommunications = new ArrayList<>();
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

  public boolean isUnused() {
    return _commMade.size()==0;
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
    if(_mode == TerminalMode.BUSY){
      return true;
    }
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    if(_mode==TerminalMode.OFF || _mode==TerminalMode.BUSY){
      return false;
    }
    return true;
  }

  public void makeSMS(Terminal to, String message){
    //FIXME completar a funcao
  }
  public void makeVoiceCall(Terminal to){
    //FIXME completar a funcao
  }

  public void makeVideoCall() {
    return;
  }
  public void endOnGoingCommunication(){
    if(canEndCurrentCommunication()){
      //FIXME acabar a funcao de modo que termine a comunicação e execute as funções associadas ao fim de uma comunicação (tal como determinar o preco por exemplo)
    }
  }

  public boolean setOnIdle(){//ADD quando vai para idle ou silent deve resolver as notificacoes
    if(_mode==TerminalMode.IDLE){
      return false;
    }
    _mode = TerminalMode.IDLE;
    return true;
  }

  public boolean setOnSilent(){
    if(_mode==TerminalMode.BUSY || _mode==TerminalMode.IDLE){
      _mode = TerminalMode.SILENCE;
      return true;
    }
    return false;
  }

  public boolean turnOff(){
    if(_mode==TerminalMode.IDLE || _mode==TerminalMode.SILENCE){
      _mode = TerminalMode.OFF;
      return true;
    }
    else if(_mode==TerminalMode.BUSY){
      return true;
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

  /*
   * sends a notification to the terminal's currespondent client
   */
  public void notificateClient(Notification n){
    //FIXME later complete notifications
  }

  /*
   * Notificates every Terminal that which attempted a communication
   * and clears the _attemptedCommunications collection afterwards
   */
  public void manageNotifications(NotificationType nType){
    Iterator<Terminal> it = _attemptedCommunications.iterator();

    while(it.hasNext()){
      it.next();
      //it.notificateClient();
    }
    _attemptedCommunications.clear();
  }

  /*
   * regists the terminal which tried to start a communication with this Terminal

  public void registAttemptedComm(Terminal from){
    if(from.canNotificateClient()){
      _attemptedCommunications.add(from);
    }
  }


   * checks if client has enabled Notifications

  public boolean canNotificateClient(){
    return _client.booleanNotifications();
  }*/
}