// A single phonebook entry
// Author: RWHasker, 2018

package pbook;

import java.io.Serializable;

public class Entry implements Comparable<Entry>, Serializable {
  protected String _name, _phone;

  public Entry(String n, String p) {
    _name = n;
    _phone = p;
  }

  public String name() {
    return _name;
  }

  public String phone() {
    return _phone;
  }

  @Override
  public String toString() {
    return name() + ": " + phone();
  }

  @Override
  public int compareTo(Entry o) {
    return name().compareTo(o.name());
  }
}
