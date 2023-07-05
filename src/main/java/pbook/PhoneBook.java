// PhoneBook: store phone entries by name with add, remove, lookup, iterate
// The iterator is guaranteed to process items in alphabetical order
// Phonebooks are *not* serializable on purpose; we save/restore history
// Author: RWHasker, 2018

package pbook;

import java.util.*;

public class PhoneBook implements Iterable<Entry> {
  protected Map<String, Entry> book = new TreeMap<String, Entry>();

  // add entry to phone book; precondition: item not present already
  public void add(Entry e) {
    book.put(e.name(), e);
  }

  // remove entry from phone book; does nothing if name is not in book
  public void remove(String name) {
    book.remove(name);
  }

  // return entry for given name; precondition: name is in book
  public Entry lookup(String name) {
    return book.get(name);
  }

  // return true iff phonebook is empty
  public boolean isEmpty() {
    return book.isEmpty();
  }

  // return (alphabetical) iterator over values in map
  @Override
  public Iterator<Entry> iterator() {
    return book.values().iterator();
  }

  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    for (Entry e : this) {
      result.append(e.toString());
      result.append("\n");
    }
    return result.toString();
  }
}
