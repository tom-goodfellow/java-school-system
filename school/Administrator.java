import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Administrator {
    private School school;
    private String filename;
    private int days;
    // lists of randomly chosen names when creating new entities
    private String[] maleNames = {"Tom", "Bob", "Alfie", "Nick", "Aaron", "Jeff", "Frank"};
    private String[] femaleNames = {"Eve", "Grace", "Alice", "Diana", "Lisa", "Emily", "Holly"};

    public Administrator(String filename, int days) throws InvalidConfigurationException, IOException{
        this.filename = filename;
        this.days = days;

        //if reading a saved state file..
        if (filename.endsWith(".save.txt")) {
            String name = getSchoolName(filename);
            if (name == null) {
                throw new InvalidConfigurationException("No school name found in: " + filename);
            }
            this.school = new School(name);
            readFileExtension(filename);
        } else if (filename.endsWith(".txt")) { //reading a external file
            String name = getSchoolName(filename);
            if (name == null) {
                throw new InvalidConfigurationException("No school name found in: " + filename);
            }
            this.school = new School(name);
            readFile(filename);
        } else {
            throw new InvalidConfigurationException("Unsupported file type provided: " + filename);
        }
    }

    public void readFileExtension(String filename){
        try {
            File f = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) { //iterate through every line
                try {
                    String[] parts = line.split(":", 2); //split into parts, class title and its attributes

                    if (parts.length != 2){
                        throw new InvalidConfigurationException("Invalid configuration of line: " + line);
                    }

                    if (parts[0].equalsIgnoreCase("school")){ //ensures school name isnt handled as an exception
                        continue;

                    //Below checks if the class title is one of the expected entities, if so then it reads accordingly and creates new object

                    } else if  (parts[0].equals("subject")) {
                        String[] sections = parts[1].split(",", 4);

                        if (sections.length != 4){
                            throw new InvalidConfigurationException("Expected 4 fields but found " + sections.length + " in line: " + line);
                        }

                        try {
                            int ID = Integer.parseInt(sections[1]);
                            int specialism = Integer.parseInt(sections[2]);
                            int duration = Integer.parseInt(sections[3]);
                            String description = sections[0];
                            Subject subject = new Subject(ID, specialism, duration, description);
                            school.add(subject);
                        } catch (NumberFormatException e){
                            throw new InvalidConfigurationException("Invalid subject configuration (expected numeric values): " + line);
                        }

                    } else if (parts[0].equals("student")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields but found " + sections.length + " in line: " + line);
                        }

                        try {
                            String name = sections[0];
                            char gender = sections[1].charAt(0);
                            int age = Integer.parseInt(sections[2]);
                            Student student = new Student(name, gender, age);
                            school.add(student);
                        } catch (NumberFormatException e){
                            throw new InvalidConfigurationException("Invalid student configuration (expected string,char,int): " + line);
                        }

                    } else if (parts[0].equals("Teacher")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields but found " + sections.length + " in line: " + line);
                        }

                        try {
                            String name = sections[0];
                            char gender = sections[1].charAt(0);
                            int age = Integer.parseInt(sections[2]);
                            Teacher teacher = new Teacher(name,gender,age);
                            school.add(teacher);
                        } catch (NumberFormatException e){
                            throw new InvalidConfigurationException("Invalid Teacher configuration: " + line);
                        }

                    } else if (parts[0].equals("Demonstrator")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields but found " + sections.length + " in line: " + line);
                        }

                        try {
                            String name = sections[0];
                            char gender = sections[1].charAt(0);
                            int age = Integer.parseInt(sections[2]);
                            Demonstrator demonstrator = new Demonstrator(name,gender,age);
                            school.add(demonstrator);
                        } catch (NumberFormatException e){
                            throw new InvalidConfigurationException("Invalid Demonstrator configuration: " + line);
                        }

                    } else if (parts[0].equals("OOTrainer")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields but found " + sections.length + " in line: " + line);
                        }

                        try {
                            String name = sections[0];
                            char gender = sections[1].charAt(0);
                            int age = Integer.parseInt(sections[2]);
                            OOTrainer ootrainer = new OOTrainer(name,gender,age);
                            school.add(ootrainer);
                        } catch (NumberFormatException e){
                            throw new InvalidConfigurationException("Invalid OOTrainer configuration: " + line);
                        }

                    } else if (parts[0].equals("GUITrainer")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields but found " + sections.length + " in line: " + line);
                        }

                        try {
                            String name = sections[0];
                            char gender = sections[1].charAt(0);
                            int age = Integer.parseInt(sections[2]);
                            GUITrainer guitrainer = new GUITrainer(name,gender,age);
                            school.add(guitrainer);
                        } catch (NumberFormatException e){
                            throw new InvalidConfigurationException("Invalid GUITrainer configuration: " + line);
                        }

                    } else if (parts[0].equals("course")){
                        String[] sections = parts[1].split(",");

                        if (sections.length < 5){
                            throw new InvalidConfigurationException("Expected minimum 5 fields but found " + sections.length + " in line: " + line);
                        }

                        try {
                            int SubjectID = Integer.parseInt(sections[0]);
                            int daysUntilStarts = Integer.parseInt(sections[1]);
                            int daysToRun = Integer.parseInt(sections[2]);
                            boolean cancelled = Boolean.parseBoolean(sections[3]);
                            String instructorname = sections[4];
                            Instructor instructor = null;

                            Subject subject = school.findSubject(SubjectID);
                            if (subject == null){
                                throw new InvalidConfigurationException("Course references null subject in line: " + line);
                            }

                            if (!instructorname.isEmpty()){
                                instructor = school.findInstructor(instructorname);
                                if (instructor == null){
                                    throw new InvalidConfigurationException("Course references null instructor in line: " + line);
                                }

                                if (!instructor.canTeach(subject)){
                                    throw new InvalidConfigurationException("Instructor cannot teach subject in line: " + line);
                                }
                            }

                            Course course = new Course(subject, daysUntilStarts, daysToRun, cancelled);
                            school.add(course);

                            if (instructor != null){
                                 course.setInstructor(instructor);
                                 instructor.assignCourse(course);
                            }




                            for (int i = 5; i < sections.length; i++){
                                String studentname = sections[i];
                                if (!studentname.isEmpty()){
                                    Student student = school.findStudent(studentname);
                                    if (student == null){
                                        throw new InvalidConfigurationException("Student not found: " + studentname);
                                    }

                                    if (course.getDaysToRun() <= 0 || course.getDaysUntilStarts() <= 0){
                                        throw new SimulationStateException("Cannot enrol student on running/finished course: " + line);
                                    }

                                    course.enrolStudent(student);
                                }
                            }

                        } catch (NumberFormatException e){
                            throw new InvalidConfigurationException("Invalid numeric values in course line: " + line);
                        }

                    } else if (parts[0].equals("certificates")){
                        String[] sections = parts[1].split(",");

                        if (sections.length < 2){
                            throw new InvalidConfigurationException("Expected minimum 2 fields but found " + sections.length + " in line: " + line);
                        }

                        String name = sections[0];
                        Student student = school.findStudent(name);

                        if (student == null){
                            throw new InvalidConfigurationException("Certificates reference unknown student: " + name);
                        }

                        for (int i = 1; i < sections.length; i++){
                            try {
                                int certificateid = Integer.parseInt(sections[i]);
                                student.addCertificate(certificateid);
                            } catch (NumberFormatException e){
                                throw new InvalidConfigurationException("Invalid certificate ID: " + sections[i]);
                            }
                        }
                    }

                } catch (InvalidConfigurationException | SimulationStateException e) {
                    System.out.println("Error in file " + filename + ": " + e.getMessage());
                }
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("I/O error while reading file: " + filename);
        }
    }

    public void readFile(String filename){

        //Same as the extensionReadFile but doesn't include reading of runtime states or other specified classes.

        try {
            File f = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);

                if (parts.length != 2){
                    throw new InvalidConfigurationException("Invalid line format: " + line);
                }

                try {
                    if (parts[0].equalsIgnoreCase("school")){
                        continue;
                    } else if (parts[0].equals("subject")) {
                        String[] sections = parts[1].split(",", 4);

                        if (sections.length != 4){
                            throw new InvalidConfigurationException("Expected 4 fields for Subject object: " + line);
                        }

                        int ID = Integer.parseInt(sections[1]);
                        int specialism = Integer.parseInt(sections[2]);
                        int duration = Integer.parseInt(sections[3]);
                        String description = sections[0];

                        Subject subject = new Subject(ID, specialism, duration, description);
                        school.add(subject);

                    } else if (parts[0].equals("student")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields for Student object: " + line);
                        }

                        String name = sections[0];
                        char gender = sections[1].charAt(0);
                        int age = Integer.parseInt(sections[2]);

                        Student student = new Student(name, gender, age);
                        school.add(student);

                    } else if (parts[0].equals("Teacher")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields for Teacher object: " + line);
                        }

                        String name = sections[0];
                        char gender = sections[1].charAt(0);
                        int age = Integer.parseInt(sections[2]);

                        Teacher teacher = new Teacher(name,gender,age);
                        school.add(teacher);

                    } else if (parts[0].equals("Demonstrator")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields for Demonstrator object: " + line);
                        }

                        String name = sections[0];
                        char gender = sections[1].charAt(0);
                        int age = Integer.parseInt(sections[2]);

                        Demonstrator demonstrator = new Demonstrator(name,gender,age);
                        school.add(demonstrator);

                    } else if (parts[0].equals("OOTrainer")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields for OOTrainer object: " + line);
                        }

                        String name = sections[0];
                        char gender = sections[1].charAt(0);
                        int age = Integer.parseInt(sections[2]);

                        OOTrainer ootrainer = new OOTrainer(name,gender,age);
                        school.add(ootrainer);

                    } else if (parts[0].equals("GUITrainer")) {
                        String[] sections = parts[1].split(",", 3);

                        if (sections.length != 3){
                            throw new InvalidConfigurationException("Expected 3 fields for GUITrainer object: " + line);
                        }

                        String name = sections[0];
                        char gender = sections[1].charAt(0);
                        int age = Integer.parseInt(sections[2]);

                        GUITrainer guitrainer = new GUITrainer(name,gender,age);
                        school.add(guitrainer);

                    } else {
                        throw new InvalidConfigurationException("Unknown entity listed in file " + filename + ": " + parts[0]);
                    }

                } catch (NumberFormatException e){
                    System.out.println("Error in file " + filename + ": Invalid number format on line" + line);
                } catch (InvalidConfigurationException e){
                    System.out.println("Error in file " + filename + ": " + e.getMessage());
                }
            }
            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("IO error occured while reading file: " + filename);
        } catch (InvalidConfigurationException e){
            System.out.println("Error in file " + filename + ": " + e.getMessage());
        }
    }

    // Method for returning the school name, null if one isn't provided
    public String getSchoolName(String filename) throws FileNotFoundException, InvalidConfigurationException{
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts[0].equalsIgnoreCase("school")){
                    return parts[1];
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find configuration file: " + filename);
        } catch (IOException e) {
            throw new InvalidConfigurationException("No school name listed in configuration file: " + filename);
        }
    }

    //Runs simulation indefinitely
    public void run() {
        while (true) {
            admitStudents();
            admitInstructors();
            school.aDayAtSchool();
            school.EndOfTheDayUpdates();
        }
    }

    // Runs for a specified amount of days
    public void run(int days) {
        for (int i = 0; i < days; i++) {
            admitStudents();
            admitInstructors();
            school.aDayAtSchool();
            school.EndOfTheDayUpdates();
            System.out.println(school);
        }
        try {
            //Calls saveSimulation after every simulated day to ensure written file is up to date
            saveSimulation(filename.replace(".save.txt", "").replace(".txt", ""));
        } catch (FileNotFoundException e) {
            System.out.println("Could not save simulation: " + e.getMessage());
        }
    }

    private void admitStudents() {
        Random r = new Random();
        int amount = r.nextInt(3); // 0 to 2 students created
        if (amount > 0){
            for (int i = 0; i < amount; i++) {
                if (r.nextInt(2) == 1){ //equal chance of assigning student to be a man or a woman
                    String name = maleNames[r.nextInt(maleNames.length)];
                    int age = r.nextInt(18,60);
                    school.add(new Student(name, 'M', age));
                } else {
                    String name = femaleNames[r.nextInt(femaleNames.length)];
                    int age = r.nextInt(18,60);
                    school.add(new Student(name, 'F', age));
                }
            }
        }
    }

    private void admitInstructors() {
        Random r = new Random();
        int amount = r.nextInt(100);
        if (amount < 20) {
            if (r.nextInt(2) == 1){
                String name = maleNames[r.nextInt(maleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new Teacher(name, 'M', age));
            } else {
                String name = femaleNames[r.nextInt(femaleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new Teacher(name, 'F', age));
            }
        } else if (amount < 30) {
            if (r.nextInt(2) == 1){
                String name = maleNames[r.nextInt(maleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new Demonstrator(name, 'M', age));
            } else {
                String name = femaleNames[r.nextInt(femaleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new Demonstrator(name, 'F', age));
            }
        } else if (amount < 35) {
            if (r.nextInt(2) == 1){
                String name = maleNames[r.nextInt(maleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new OOTrainer(name, 'M', age));
            } else {
                String name = femaleNames[r.nextInt(femaleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new OOTrainer(name, 'F', age));
            }
        } else if (amount < 40) {
            if (r.nextInt(2) == 1){
                String name = maleNames[r.nextInt(maleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new GUITrainer(name, 'M', age));
            } else {
                String name = femaleNames[r.nextInt(femaleNames.length)];
                int age = r.nextInt(18, 60);
                school.add(new GUITrainer(name, 'F', age));
            }
        }
    }

    public void saveSimulation(String filename) throws FileNotFoundException {
        // saves current entities and runtime state to a file
        ArrayList<Student> students = school.getStudents();
        ArrayList<Instructor> instructors = school.getInstructors();
        ArrayList<Subject> subjects = school.getSubjects();
        ArrayList<Course> courses = school.getCourses();

        try {
            PrintWriter writer = new PrintWriter(new File(filename + ".save.txt"));

            writer.println("school:" + this.school.getName());

            // Iterating though subjects
            for (Subject subject : subjects) {
                writer.println("subject:" + subject.getDescription() + "," + subject.getID() + "," + subject.getSpecialism() + "," + subject.getDuration());
            }

            // students
            for (Student student : students) {
                writer.println("student:" + student.getName() + "," + student.getGender() + "," + student.getAge());
            }

            // instructors
            for (Instructor instructor : school.getInstructors()) {
                if (instructor instanceof GUITrainer) {
                    writer.println("GUITrainer:" + instructor.getName() + "," + instructor.getGender() + "," + instructor.getAge());
                } else if (instructor instanceof OOTrainer) {
                    writer.println("OOTrainer:" + instructor.getName() + "," + instructor.getGender() + "," + instructor.getAge());
                } else if (instructor instanceof Teacher) {
                    writer.println("Teacher:" + instructor.getName() + "," + instructor.getGender() + "," + instructor.getAge());
                } else if (instructor instanceof Demonstrator) {
                    writer.println("Demonstrator:" + instructor.getName() + "," + instructor.getGender() + "," + instructor.getAge());
                }
            }

            // courses
            for (Course course : courses) {
                String instructorName;
                if (course.hasInstructor()){
                     instructorName = course.getInstructor().getName();
                } else {
                     instructorName = "";
                }
                writer.print("course:" + course.getSubject().getID() + "," + course.getDaysUntilStarts() + "," + course.getDaysToRun() + "," + course.isCancelled() + "," + instructorName);

                for (Student student : course.getStudents()) {
                    writer.print("," + student.getName());
                }

                writer.println();

            }

            // student certificates
            for (Student student : students) {
                if (!student.getCertificates().isEmpty()) {
                    writer.print("certificates:" + student.getName());
                    for (Integer certID : student.getCertificates()) {
                        writer.print("," + certID);
                    }
                    writer.println();
                }
            }

            writer.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find configuration file: " + filename);
        }


    }

    public class InvalidConfigurationException extends Exception {
        public InvalidConfigurationException(String message) {
            super(message);
        }
    }

    public class SimulationStateException extends Exception {
        public SimulationStateException(String message) {
            super(message);
        }
    }

    public static void main(String[] args){
        try {
            if (args.length != 2){
                System.out.println("Expected 2 fields, instead recieved " + args.length);
                return;
            }
            String filename = args[0];
            int days = Integer.parseInt(args[1]);

            Administrator admin = new Administrator(filename, days);
            admin.run(days);

        } catch (Administrator.InvalidConfigurationException e){
            System.out.println("Configuration Error: " + e.getMessage());
        } catch (IOException e){
            System.out.println("File error: " + e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Only numeric value excepted for 'days'");
        }

    }
}
