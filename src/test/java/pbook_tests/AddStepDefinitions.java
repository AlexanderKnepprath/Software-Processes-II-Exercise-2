package pbook_tests;

/*
 * Step definitions for adding numbers and other core operations.
 *
 */

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import pbook.*;

public class AddStepDefinitions {

  @Given("^I reset the phonebook$")
  public void do_reset() {
    String[] ignored = PhoneCommandCapture.instance().outputLines(new Phones(), "reset", "");
  }
  
  @Given("^an? empty phonebook$") // same as a reset operation; allows a or an
  public void make_empty_phonebook() {
    do_reset();
  }
  
  @When("^I add ([a-zA-Z]+) with number (\\d+)$")
  public void do_add(String name, Integer number) {
    String input = name + "\n" + number.toString();
    String[] result = PhoneCommandCapture.instance().outputLines(new Phones(), "add", input);
  }

  @When("^I remove ([a-zA-Z]+)")
  public void do_remove(String name) {
    String input = name;
    String[] result = PhoneCommandCapture.instance().outputLines(new Phones(), "remove", input);
  }
  
  @When("^I undo$")
  public void do_undo() {
    String[] ignored = PhoneCommandCapture.instance().outputLines(new Phones(), "undo", "");
  }
  
  @When("^I redo$")
  public void do_redo() {
    String[] ignored = PhoneCommandCapture.instance().outputLines(new Phones(), "redo", "");
  }
  
  @Then("^([a-zA-Z]+)'s number is (\\d+)$")
  public void check_phone_number(String name, Integer number) {
    String input = name;
    String[] results = PhoneCommandCapture.instance().outputLines(new Phones(), "get", input);
    //PhoneCommandCapture.instance().reportOutput(results);
    assertTrue(results.length > 1);
    assertEquals("Phone # for " + name + ": " + number, results[1]);
  }
  
  @Then("^the number of entries is (\\d+)$")
  public void check_entry_count(Integer count) {
    String[] results = PhoneCommandCapture.instance().outputLines(new Phones(), "list", "");
    //System.out.println("Expecting " + count + " results:");
    //PhoneCommandCapture.instance().reportOutput(results);
    if ( count.intValue() == 0 ) {
      assertEquals(1, results.length);
      assertEquals("", results[0]);
    } else {
      assertEquals(results.length, count.intValue());
    }
  }
}
