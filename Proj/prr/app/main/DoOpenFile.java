package prr.app.main;

import prr.core.NetworkManager;
import prr.app.exception.FileOpenFailedException;
import prr.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Field;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.*;

import javax.imageio.IIOException;
import java.io.IOException;
//Add more imports if needed

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

    private Field<String> filename;
  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
    addStringField("fileName", Message.openFile());
  }
  
  @Override
  protected final void execute() throws CommandException {
      try {
      _receiver.load(stringField("fileName"));
      } catch (UnavailableFileException | IOException | ClassNotFoundException fofe) {
        throw new FileOpenFailedException(fofe);
      }
  }
}
