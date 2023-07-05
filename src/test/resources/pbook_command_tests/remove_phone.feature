Feature: removing phone entries to a list

    Modify this to test adding and then removing entries, including undo

Scenario: add then remove a person
  When I reset the phonebook
  And I add Zoe with number 1804
  And I remove Zoe
  Then the number of entries is 0

  # The next step fails if no steps are added; the intent is that you
  # would add a step definition for a remove command and then
  # remove Zoe so the following Then clause passes.
  # You might try commenting out this line to confirm that Cucumber
  # will catch errors.
  #Then the number of entries is 0

Scenario: add 2 remove 1
  When I reset the phonebook
  And I add Duke with number 30
  And I add Mikhail with number 777
  And I remove Duke  # too bad, really, he was a nice guy
  Then the number of entries is 1 