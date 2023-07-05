// simple command-line tool for adding and looking up names in a phone book
// to run: see postUsage
// Author: RWHasker, 2018
//

package pbook;

import java.io.*;
import java.util.Scanner;

public class Phones implements AbstractMain
{
  private static final String DATAFILE = "pbook.dat";
  
  private History history;
  private PhoneBook book;       // initial book is null; set in load history
  
  protected static void postUsage(String msg)
  {
    System.out.println("Usage: java Phones add|get|update|remove|list|undo|redo|reset");
    System.out.println(msg);
    System.exit(1);
  }

  public void abstractMain(String[] args) {
    loadHistory();
    
    Scanner input = new Scanner(System.in);

    if ( args.length < 1 )
      postUsage("Missing command");
    String cmd = args[0].toLowerCase();

    switch ( cmd ) {
    case "add":
      doAdd(input);
      break;
    case "update":
      doUpdate(input);
      break;
    case "get":
      doGet(input);
      break;
    case "list":
      doList();
      break;
    case "remove":
      doRemove(input);
      break;
    case "undo":
      doUndo();
      break;
    case "redo":
      doRedo();
      break;
    case "reset":
      doReset();
      break;
    default:
      postUsage("Unrecognized command '" + cmd + "'");
      break;
    }
  }

  public static void main(String[] args) {
    (new Phones()).abstractMain(args);
  }

  // add entry to phonebook, printing error if already found
  protected void doAdd(Scanner input) {
    System.out.print("Enter name to add: ");
    String name = input.nextLine();
    System.out.println();
    if ( book.lookup(name) != null )
      System.out.println(name + " already in phone book");
    else {
      System.out.print("Enter phone number without spaces: ");
      String number = input.next();
      System.out.println();
      history.doCommand(new AddCommand(book, new Entry(name, number)));
      saveHistory();
    }
  }

  // update entry to phonebook, printing error if not found
  protected void doUpdate(Scanner input) {
    System.out.print("Enter name to update: ");
    String name = input.nextLine();
    System.out.println();
    if ( book.lookup(name) == null )
      System.out.println(name + " not in phone book");
    else {
      System.out.print("Enter new number without spaces: ");
      String number = input.next();
      System.out.println();
      // note adding also updates existing entries
      history.doCommand(new AddCommand(book, new Entry(name, number)));
      saveHistory();
    }
  }

  // look up phone number by name
  protected void doGet(Scanner input) {
    System.out.print("Enter name: ");
    String name = input.nextLine();
    System.out.println();
    Entry entry = book.lookup(name);
    if ( entry == null )
      System.out.println("No phone entry for " + name);
    else
      System.out.println("Phone # for " + name + ": " + entry.phone());
  }

  // remove entry by name
  protected void doRemove(Scanner input) {
    System.out.print("Enter name: ");
    String name = input.nextLine();
    System.out.println();
    Entry entry = book.lookup(name);
    if ( entry == null )
      System.out.println("No phone entry for " + name);
    else {
      history.doCommand(new RemoveCommand(book, name));
      saveHistory();
    }
  }

  protected void doList() {
    for(Entry e : book) {
      System.out.println(e);
    }
  }

  protected void doUndo() {
    if ( history.nextToUndo() == null )
      System.out.println("No commands to undo.");
    else {
      history.undo();
      saveHistory();
    }
  }
  
  protected void doRedo() {
    if ( history.nextToRedo() == null )
      System.out.println("No commands to redo.");
    else {
      history.redo();
      saveHistory();
    }
  }
  
  // reset data; there is no undo after a reset
  protected void doReset() {
    history = new History();
    book = new PhoneBook();
    history.applyTo(book);
    saveHistory();
  }

  // load phonebook by loading (serialized) history and applying it
  protected void loadHistory()
  {
    history = null;             // ensure nothing carried over
    try ( ObjectInputStream is = 
          new ObjectInputStream(new FileInputStream(DATAFILE)) ) {
      history = (History)is.readObject();
    }
    catch ( IOException e )
    {
      // no file found, so just create an empty history
      history = new History();
    }
    catch ( ClassNotFoundException e )
    {
      System.err.println("Data file " + DATAFILE + " contains the wrong class.");
      System.exit(1);
    }
    book = new PhoneBook();
    history.applyTo(book);
  }

  // write the serialized history
  protected void saveHistory()
  {
    try( ObjectOutputStream p 
         = new ObjectOutputStream(new FileOutputStream(DATAFILE)) ) {
      p.writeObject(history);
    }
    catch ( IOException e )
    {
      System.err.println("Could not save phone book to " + DATAFILE);
    }
  }
}
