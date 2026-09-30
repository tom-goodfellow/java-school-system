Parts Attempted

(1. Specification)
2. Subject and Course Classes
3. The Student Class
4. The Instructor Class
5. The School
6. Running the Simulation
7. Reading a Simulation Configuration File
8. Saving and Resuming the Simulation (Extension)

Extension Description

The simulation supports saving and resuming state. After each run the simulation automatically saves to a .save.txt file, capturing all entities and runtime state including courses, enrolments, instructor assignments and timing details. This means the simulation can be paused and resumed at any point without losing progress.

To resume a saved simulation just pass the .save.txt file as the first argument instead of .txt: 

'java Administrator <filename>.save.txt <days>'

I also added exception handling throughout the save and load process to catch any issues like missing files, malformed lines, or invalid data.


Notes:

Program is designed to be executed from Administrator class as specified. After compiling, run 'java Administrator <filename> <days>'. 
 
Files to be read though MUST be in the same directory as java files.
