package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    addStringField("key", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    _network.removeFriend(_receiver.getId(), stringField("key"));
  }
}
