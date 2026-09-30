# java-school-system

A Java-based training school management system modelling students, instructors, courses and school operations.

The program is operated through the command line using the Administrator class.

To run the simulation:

java Administrator **filename** **days**

_filename - A text file containing the simulation configuration._

_(int) days - The number of days to simulate._

The configuration file must be located in the same directory as the Java source files.

The repository includes an example configuration file, mySchool.txt, which demonstrates the required format and can be used as a template for creating other simulation configurations.

After each simulation run, the current state is automatically saved to a .save.txt file. This stores the current simulation state, including courses, enrolments, instructor assignments and progress, allowing the simulation to be resumed without losing its state.

To resume a saved simulation:

java Administrator <filename>.save.txt <days>

The program includes exception handling throughout the save and load process to handle issues such as missing files, malformed data and invalid input.
