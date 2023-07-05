// History: phone book command history; allows undoing and redoing operations
// The phonebook is saved as the history of commands to create it.
// Author: RWHasker, 2018

package pbook;

import java.util.Stack;
import java.io.Serializable;

public class History implements Serializable {
  // done_cmds:   those commands that are currently executed
  // undone_cmds: commands which were undone and could be re-executed
  protected Stack<PhoneCommand> done_cmds = new Stack<>();
  protected Stack<PhoneCommand> undone_cmds = new Stack<>();

  // apply history to given phonebook, effectively attaching it to
  //    this phonebook
  void applyTo(PhoneBook targetBook) {
    // point undone_cmds at the phonebook
    for(PhoneCommand c : undone_cmds)
      c.setBook(targetBook);

    // process all done commands in reverse order, at the same time
    //   pointing them at the phonebook
    for(int i = 0; i < done_cmds.size(); ++i) {
      PhoneCommand todo = done_cmds.elementAt(i);
      todo.setBook(targetBook);
      todo.execute();
    }
  }

  // returns null if no command to undo, the command otherwise
  public PhoneCommand nextToUndo()
  {
    if ( done_cmds.isEmpty() )
      return null;
    else
      return done_cmds.peek();
  }

  // returns null if no command to redo, the command otherwise
  public PhoneCommand nextToRedo()
  {
    if ( undone_cmds.isEmpty() )
      return null;
    else
      return undone_cmds.peek();
  }

  // execute command and store it on the done commands list;
  //   old undone commands are permanently deleted
  public void doCommand(PhoneCommand new_cmd)
  {
    new_cmd.execute();
    undone_cmds.clear();
    done_cmds.push(new_cmd);
  }

  // undoes last executed command; precondition: at least one command to undo
  public void undo()
  {
    PhoneCommand to_undo = done_cmds.pop();
    to_undo.unexecute();
    undone_cmds.push(to_undo);
  }

  // redoes last undone command; precondition: at least one command to redo
  public void redo()
  {
    PhoneCommand todo = undone_cmds.pop();
    todo.execute();
    done_cmds.push(todo);
  }
}
