Feature: adding phone entries to a list

    This tests adding phone entries and undoing that operation.

Scenario: add a person and list
  Given an empty phonebook
  When I add Xander with number 123
  Then Xander's number is 123
  And the number of entries is 1

Scenario: add a person and reset
  Given an empty phonebook
  When I add Xander with number 123
  And I reset the phonebook
  Then the number of entries is 0

Scenario: add a person and undo
  Given an empty phonebook
  When I add Xander with number 123
  And I undo
  Then the number of entries is 0

Scenario: add a person and undo then redo
  Given an empty phonebook
  When I add Xander with number 123
  And I undo
  And I redo
  Then the number of entries is 1
