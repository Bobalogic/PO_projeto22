package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {
  private Terminal _terminal;
  private Network _context;

  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
    _context = context;
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(!_context.turnONTerminal(_terminal)){
      _display.popup(Message.alreadyOn());
    }
  }
}
