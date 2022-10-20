package prr.app.client;

import prr.core.Network;
import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.*;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("taxNum", Message.taxId());
  }
  
  @Override
  protected final void execute() throws CommandException {
    try {
      _receiver.registerClient(stringField("key"), stringField("name"), integerField("taxNum"));
    }catch (DuplicateClientKeyException exe){
      exe = new DuplicateClientKeyException(stringField("key"));
    }
  }
}
