package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn off the terminal.
 */
class DoTurnOffTerminal extends TerminalCommand {
  private Terminal _terminal;
  private Network _context;

  DoTurnOffTerminal(Network context, Terminal terminal) {
    super(Label.POWER_OFF, context, terminal);
    _context = context;
    _terminal = terminal;
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(!_context.turnOffTerminal(_terminal)){
      _display.addLine(Message.alreadyOff());
      _display.display();
    }
    //FIXME implement command
  }
}
