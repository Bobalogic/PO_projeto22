package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

import java.io.IOException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);

  }
  
  @Override
  protected final void execute() {
    if (_receiver.get_filename() == null) {
      try {

        _receiver.saveAs(_receiver.get_filename());
      } catch (MissingFileAssociationException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }else {
      try {
        _receiver.save();
      } catch (MissingFileAssociationException e) {
        throw new RuntimeException(e);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
