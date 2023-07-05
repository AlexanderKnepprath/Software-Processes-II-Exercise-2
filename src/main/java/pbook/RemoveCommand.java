// RemoveCommand: remove an entry from the phonebook

package pbook;

public class RemoveCommand extends PhoneCommand {
  protected String name_to_remove;
  protected Entry old_entry;

  public RemoveCommand(PhoneBook pbk, String n)
  {
    super(pbk);
    name_to_remove = n;
  }

  @Override
  public void execute()
  {
    old_entry = book().lookup(name_to_remove);
    book().remove(name_to_remove);
  }

  @Override
  public void unexecute()
  {
    book().add(old_entry);
  }
}
