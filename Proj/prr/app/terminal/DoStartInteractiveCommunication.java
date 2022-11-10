package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.TerminalMode;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("key", Message.terminalKey());
    addOptionField("type", Message.commType(), "VOICE", "VIDEO");
  }
  
  @Override
  protected final void execute() throws CommandException {
    Terminal to = _network.getTerminal(stringField("key"));
    if(optionField("type").equals("VIDEO") && !_network.supportsVideoCommunication(_receiver)) {
      _display.popup(Message.unsupportedAtOrigin(_receiver.getId(), stringField("type")));
      return;
    }
    else if(optionField("type").equals("VIDEO") && !_network.supportsVideoCommunication(to)) {
      _display.popup(Message.unsupportedAtDestination(stringField("key"), stringField("type")));
      return;
    }

    if(_receiver.canStartCommunication() && to.canReceiveCommunications()) {
      _network.startInteractiveCommunication(_receiver, to, optionField("type"));
      return;
    }
    else if(!_receiver.canStartCommunication())
      return;

    switch (to.getMode()) {
      case OFF -> _display.popup(Message.destinationIsOff(stringField("key")));
      case BUSY -> _display.popup(Message.destinationIsBusy(stringField("key")));
      case SILENCE -> _display.popup(Message.destinationIsSilent(stringField("key")));
    }
    _network.attemptedInteractiveComm(_receiver, to);
  }
}
