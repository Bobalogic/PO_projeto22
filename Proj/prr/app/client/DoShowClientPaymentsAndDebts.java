package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    _display.popup(Message.clientPaymentsAndDebts(stringField("key"), _receiver.getClientPayments
            (stringField("key")), _receiver.getClientDebts(stringField("key"))));
  }
}
