Project integrating a command-line phonebook (which supports undo and redo using
the command pattern) with Docker and Cucumber.

## Phonebook code

The phonebook code is in `src/main/java/pbook`. This code will build as a
separate item:
1. `cd src/main/java`
2. `javac pbook/*.java`
3. `java pbook.Phones`

This prints a usage line. The program maintains a database in the file
`pbook.dat`. See below for a sample run.

## Acceptance Testing with Cucumber

The primary goal of this project is to illustrate writing tests that can be
read by an end user. See the [Cucumber site](https://cucumber.io/) for more
information. `src/test/resources/pbook_command_tests` contains "feature" files
that have tests for various scenarios with steps written using Given/When/Then
format. `add_phone.feature` tests a few simple cases around adding a number to
the phonebook, `remove_phone.feature` is a stub for adding tests around
the remove command. Note each step must start by emptying the phonebook since
that persists between runs; one could use the Cucumber before hook to ensure
the phonebook is always reset before each test.

The file `src/test/java/pbook_tests/AddStepDefinitions.java` contains the Java
code that interprets the steps in the feature files, known as step
definitions. These use `PhoneCommandCapture.java` in the same folder to
communicate with a main through standard input and output. For a real system,
one would rewrite the classes to allow more testing at the unit and
integration level; see [Martin Fowler's Test
Pyramid](https://martinfowler.com/bliki/TestPyramid.html)
discussion for an excellent explanation.

## Sample Run

The following captures a sample run. It assumes you compiled the code as
directed above. Note that the program creates a `phones.dat` that is
manipulated by the difference commands. The `$` is a standin for the
system prompt. This code should run in any Java environment using Java 1.7 or
more recent. The extra blank lines in the output are a hold-over from
pre-Cucumber days when testing had to be done by redirecting input from a text
file.
<pre>
$ <b>java pbook.Phones add</b>
Enter name to add: <b>Tia</b>

Enter phone number without spaces: <b>987-6543</b>

$ <b>java pbook.Phones add</b>
Enter name to add: <b>Tabaldak</b>

Enter phone number without spaces: <b>123-4567</b>

$ <b>java pbook.Phones add</b>
Enter name to add: <b>Sedna</b>

Enter phone number without spaces: <b>502-3845</b>

$ <b>java pbook.Phones list</b>
Sedna: 502-3845
Tabaldak: 123-4567
Tia: 987-6543
$ <b>java pbook.Phones remove</b>
Enter name: Tia

$ <b>java pbook.Phones list</b>
Sedna: 502-3845
Tabaldak: 123-4567
$ <b>java pbook.Phones undo</b>
$ <b>java pbook.Phones list</b>
Sedna: 502-3845
Tabaldak: 123-4567
Tia: 987-6543
</pre>
