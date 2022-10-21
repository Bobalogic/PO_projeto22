package prr.app.terminals;

import prr.core.Network;
import prr.core.Terminal;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("key", Message.terminalKey());
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    // create an instance of prr.app.terminal.Menu with the
    // selected Terminal and open it
    Terminal terminal = null;
    try {
      terminal = _receiver.getTerminal(stringField("key"));
    }catch(UnknownTerminalKeyException exe) {
      exe = new UnknownTerminalKeyException(stringField("key"));
    }
    (new prr.app.terminals.Menu(_receiver, terminal)).open();
  }
}
