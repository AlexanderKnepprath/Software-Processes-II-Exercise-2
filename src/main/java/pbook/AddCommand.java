// AddCommand: add an entry to a phone book, or update an existing entry
// Author: RWHasker, 2018

package pbook;

public class AddCommand extends PhoneCommand {
  protected Entry entry_to_add;

  public AddCommand(PhoneBook pbk, Entry e) {
    super(pbk);
    entry_to_add = e;
  }

  @Override
  public void execute() {
    book().add(entry_to_add);
  }

  @Override
  public void unexecute() {
    book().remove(entry_to_add.name());
  }
}
