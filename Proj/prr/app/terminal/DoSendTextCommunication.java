package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import prr.core.TerminalMode;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("key", Message.terminalKey());
    addStringField("Message", Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException {
    Terminal to = _network.getTerminal(stringField("key"));
    if(to.getMode() == TerminalMode.OFF) {
      _display.popup(Message.destinationIsOff(stringField("key")));
      to.subscribeAttemptedTextComms(_receiver);
    }
    else if(_receiver.canStartCommunication()) {
      _network.sendTextCommunication(_receiver, to, stringField("Message"));
    }
    else
      _network.attemptedTextComm(_receiver, to);
  }
} 
