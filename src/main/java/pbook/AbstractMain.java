// AbstractMain: interface for classes with a main method where the
//   main has been refactored to call abstractMain on an object
// Author: RWHasker, 2018

package pbook;

public interface AbstractMain {

  // main method to be executed
  void abstractMain(String[] args);

  // main method to execute when there are no command-line arguments
  default void abstractMain() {
    String[] empty = {};
    abstractMain(empty);
  }
}
