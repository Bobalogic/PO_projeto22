package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addIntegerField("id", Message.terminalKey());
    addStringField("type", Message.terminalType());
    while(stringField("type") != "FANCY" || stringField("type") != "NORMAL") {
      addStringField("type", Message.terminalType());
    }
  }

  @Override
  protected final void execute() throws CommandException {
    if(stringField("type") == "FANCY") {
      try {
        _receiver.registerFancyTerminal(stringField("id"));
      }catch (DuplicateTerminalKeyException exe) {
        exe = new DuplicateTerminalKeyException(Integer.toString(integerField("id")));
      }
    }
    else if(stringField("type") == "NORMAL") {
      try {
        _receiver.registerBasicTerminal(stringField("id"));
      }catch (DuplicateTerminalKeyException exe) {
        exe = new DuplicateTerminalKeyException(Integer.toString(integerField("id")));
      }
    }
  }
}
