// Superclass for all phonebook commands
// Commands can be executed and serialized
// Author: RWHasker, 2018

package pbook;

import java.io.Serializable;

public abstract class PhoneCommand implements Serializable {
  // phonebook to process; this is marked as transient because
  //   there's no value in serializing the phonebook itself multiple times
  transient protected PhoneBook pb;

  public PhoneCommand(PhoneBook _pb) {
    setBook(_pb);
  }

  public PhoneBook book() { return pb; }
   
  public void setBook(PhoneBook _pb) {
    pb = _pb;
  }

  public abstract void execute(); // executing the command

  public abstract void unexecute(); // undoing the command
}
