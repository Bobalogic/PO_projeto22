package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(!_receiver.disableClientNotifications(stringField("key")))
      _display.popup(Message.clientNotificationsAlreadyDisabled());
  }
}
