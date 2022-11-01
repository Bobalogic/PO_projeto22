package prr.app.main;

import prr.app.exception.FileOpenFailedException;
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
  protected final void execute() throws FileOpenFailedException {
    Form fileName = new Form();
    if (_receiver.get_filename() == null) {
      fileName.addStringField("saveAs", Message.newSaveAs());
      fileName.parse();
      try {
        _receiver.saveAs(fileName.stringField("saveAs"));
      } catch (MissingFileAssociationException e) {
        throw new FileOpenFailedException(e);
      } catch (IOException e) {
        throw new FileOpenFailedException(e);
      }
    }else {
      try {
        _receiver.save();
      } catch (MissingFileAssociationException e) {
        throw new FileOpenFailedException(e);
      } catch (IOException e) {
        throw new FileOpenFailedException(e);
      }
    }
  }
}
