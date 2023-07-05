package pbook_tests;

/* Singleton class used to capture input/output from phone commands */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import pbook.AbstractMain;

public class PhoneCommandCapture {
  private static PhoneCommandCapture _instance = null;
  private String inputHeight = null;
  private String[] resultLines = null;

  private PhoneCommandCapture() { }

  public static PhoneCommandCapture instance() {
    if ( _instance == null )
      _instance = new PhoneCommandCapture();
    return _instance;
  }

  // return output from executing main given this call
  // commandArg is optional; if null, no argument is specified
  public String[] outputLines(AbstractMain mainObject,
                              String commandArg,
                              String inputText) {
    ByteArrayOutputStream capture = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(capture);
    PrintStream savedSystemOutput = System.out;
    System.setOut(ps);
    
    InputStream is = null;
    try {
      is  = new ByteArrayInputStream(inputText.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      System.err.print("Fatal error: " + e);
      System.exit(1);
    }
    InputStream savedSystemInput = System.in;
    System.setIn(is);
    
    if ( commandArg == null )
      mainObject.abstractMain();
    else {
      String[] args = new String[1];
      args[0] = commandArg;
      mainObject.abstractMain(args);
    }

    // Put things back
    System.out.flush();
    System.setOut(savedSystemOutput);
    System.setIn(savedSystemInput);

    String lines[] = capture.toString().split("[\r\n]+");

    return lines;
  }

  // report capture output; useful for debugging
  public void reportOutput(String[] lines) {
    System.out.println("Results:");
    for(String aLine : lines) {
      System.out.println("'" + aLine + "'");
    }
  }
}
