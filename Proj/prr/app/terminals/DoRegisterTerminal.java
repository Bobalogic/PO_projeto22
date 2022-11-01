package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.lang.reflect.Type;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
    addStringField("clientID", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException,UnknownClientKeyException {
    _receiver.registerTerminal(stringField("id"), stringField("type"), stringField("clientID"));
  }
}

