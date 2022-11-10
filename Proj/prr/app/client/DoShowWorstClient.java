package prr.app.client;

import prr.core.Network;
import prr.core.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoShowWorstClient extends Command<Network> {
    DoShowWorstClient(Network receiver) {
        super("Pior Cliente", receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        Client c = _receiver.getWorstClient();
        if(c!=null)
            _display.popup(c.getKey() + c.getNumTerminals());
    }
}
