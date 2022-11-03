package prr.app.client;

import prr.core.Notification;
import prr.core.Network;
import prr.core.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    Client clientObject = _receiver.getClient(stringField("key"));
    _display.addLine(_receiver.showClient(clientObject));
    for(Notification n: clientObject.getNotifications())
      _display.addLine(n.toString());
    _receiver.clearClientNotifications(clientObject);
    _display.display();
  }
}
