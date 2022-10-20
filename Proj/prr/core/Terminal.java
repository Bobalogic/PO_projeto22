package prr.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

//import prr.core.TerminalMode;
//import prr.core.Communication;
//import prr.core.Client;
//import prr.core.Notification;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)
/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private int _id;
  //private String _mode;
  private double _debt;
  private double _payments;
  private TerminalMode _mode;
  private Communication _from;
  private Communication _to;
  private Client _client;
  private Collection<Terminal> _friends;
  private Collection<Terminal> _attemptedCommunications;
  //adicionar atributo client?
  //private Terminal[] _friends;
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
  public Terminal(int id){
    _id = id;
    _debt = 0;
    _payments = 0;
    _mode = TerminalMode.IDLE;
    _from = null;
    _to = null;
    _friends = new HashSet<>();
    _attemptedCommunications = new LinkedList<>();
  }

  public TerminalMode getMode(){
    return _mode;
  }

  public int getId() {
    return _id;
  }

  public boolean associateClient(Client c){
    //fazer exeptions para quando tem la um null, ou quando recebe um null
    if(_client != null || c == null){
      return false;
    }
    _client = c;
    return true;
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
    // FIXME add implementation code
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

  public boolean makeFriend(Terminal other){//ADD exeption para se receber algo que nao um terminal ou maybe nao e preciso
    if(_friends.contains(other)){
      _friends.add(other); //prolly funciona
      other.addFriend(this);
      return true;
    }
    return false;
  }

  public void addFriend(Terminal other){
    _friends.add(other);
  }

  public boolean disMakeFriend(Terminal other){
    if(_friends.contains(other)){
      _friends.remove(other);
      other.removeFriend(this);
      return true;
    }
    return false;
  }

  public void removeFriend(Terminal other){
    _friends.remove(other);
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
   */
  public void registAttemptedComm(Terminal from){
    if(from.canNotificateClient()){
      _attemptedCommunications.add(from);
    }
  }

  /*
   * checks if client has enabled Notifications
   */
  public boolean canNotificateClient(){
    return _client.booleanNotifications();
  }
}