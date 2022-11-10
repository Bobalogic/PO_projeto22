package prr.app.client;

import prr.core.Client;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoRemoveClients extends Command<Network> {

    DoRemoveClients(Network receiver) {
        super("Remove Clientes", receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int numeroRemovidos = 0;
        for(Client c:_receiver.getAllClient()) {
            if (c.getKey().length() > 4) {
                numeroRemovidos += 1;
                _display.addLine(c.getKey());
                _receiver.removeClient(c.getKey());
            }
        }
        _display.addLine(numeroRemovidos);
        _display.display();
    }
}
