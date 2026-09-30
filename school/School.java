import java.util.*;

public class School {
    private String name;

    private ArrayList<Subject> subjects;
    private ArrayList<Instructor> instructors;
    private ArrayList<Student> students;
    private ArrayList<Course> courses;

    public School(String name){
        this.name = name;
        subjects = new ArrayList<>();
        instructors = new ArrayList<>();
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }

    //Getters
    public String getName(){
        return name;
    }

    public void add(Subject subject){
        subjects.add(subject);
    }

    public void remove(Subject subject){
        subjects.remove(subject);
    }

    public ArrayList<Subject> getSubjects(){
        return subjects;
    }

    public void add(Instructor instructor){
        instructors.add(instructor);
    }

    public void remove(Instructor instructor){
        instructors.remove(instructor);
    }

    public ArrayList<Instructor> getInstructors(){
        return instructors;
    }

    public void add(Student student){
        students.add(student);
    }

    public void remove(Student student){
        students.remove(student);
    }

    public ArrayList<Student> getStudents(){
        return students;
    }

    public void add(Course course){
        courses.add(course);
    }

    public void remove(Course course){
        courses.remove(course);
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

    //Below are helper methods included to find certain attributes based on data known.

    public Subject findSubject(int subjectID){ //For instance, with a subject ID, iterates through every subject in the school until a match is present
        for (Subject subject : subjects) {
            if (subject.getID() == subjectID) {
                return subject;
            }
        }
        return null;
    }

    public Instructor findInstructor(String name){
        for (Instructor instructor: instructors){
            if (instructor.getName().equals(name)){
                return instructor;
            }
        }
        return null;
    }

    public Student findStudent(String name){
        for (Student student: students){
            if (student.getName().equals(name)){
                return student;
            }
        }
        return null;
    }

    public String toString() {  //Pretty printed school details, relationships
        String bigLine = "============================================================\n";
        String smallLine = "------------------------------------------------------------\n";

        String text = "\n" + bigLine;
        text += "                        SCHOOL\n";
        text += "                   " + this.name + "\n";
        text += bigLine + "\n";

        text += smallLine;
        text += "  SUBJECTS\n";
        text += smallLine;
        for (Subject subject : subjects) {
            text += "ID: " + subject.getID() + "\n"
                    + "Specialism: " + subject.getSpecialism() + "\n"
                    + "Duration: " + subject.getDuration() + "\n"
                    + "Description: " + subject.getDescription() + "\n\n";
        }

        text += smallLine;
        text += "  COURSES\n";
        text += smallLine;
        for (Course course : courses) {
            text += "Subject ID: " + course.getSubject().getID() + "\n"
                    + "Status: " + course.getStatus() + "\n"
                    + "Course Size: " + course.getSize() + "\n";

            text += "Instructor Assigned?: ";
            if (course.hasInstructor()) {
                text += "Y\n";
            } else {
                text += "N\n";
            }

            text += "Cancelled?: ";
            if (course.isCancelled()) {
                text += "Y\n";
            } else {
                text += "N\n";
            }

            text += "\n";
        }

        text += smallLine;
        text += "  INSTRUCTORS\n";
        text += smallLine;
        for (Instructor instructor : instructors) {
            text += "Name: " + instructor.getName() + "\n";
            text += "Gender: " + instructor.getGender() + "\n";
            text += "Age: " + instructor.getAge() + "\n";

            text += "Teaching Subject ID: ";
            if (instructor.getAssignedCourse() != null) {
                text += instructor.getAssignedCourse().getSubject().getID() + "\n";
            } else {
                text += "No Assigned Course\n";
            }

            text += "\n";
        }

        text += smallLine;
        text += "  STUDENTS\n";
        text += smallLine;
        for (Student student : students) {
            text += "Name: " + student.getName() + "\n";

            text += "Certificates: ";
            for (Integer certificates : student.getCertificates()) {
                text += certificates + ",";
            }

            text += "\n\n";
        }

        text += smallLine;
        text += "  RELATIONSHIPS\n";
        text += smallLine;
        text += "Each Course has one instructor, Each instructor can be assigned to one course.\n";
        for (Course course : courses) {
            if (course.hasInstructor()) {
                text += "Course (Subject ID " + course.getSubject().getID() + ") -> Instructor: "
                        + course.getInstructor().getName() + "\n";
            }
        }

        text += "\nEach Course can have multiple (3) students.\n";
        for (Course course : courses) {
            text += "Course (Subject ID " + course.getSubject().getID() + ") -> Students: ";
            if (course.getSize() != 0) {
                for (Student student : course.getStudents()) {
                    text += student.getName() + " ";
                }
            }
            text += "\n";
        }

        text += "\nEach Student can have multiple certificates.\n";
        for (Student student : students) {
            text += "Student " + student.getName() + " -> Certificates: ";
            if (student.getCertificates().isEmpty()) {
                text += "None";
            } else {
                for (Integer cert : student.getCertificates()) {
                    text += cert + " ";
                }
            }
            text += "\n";
        }

        text += "\n" + bigLine;
        return text;
    }

    public void aDayAtSchool(){
        ArrayList<Course> NewCourses = new ArrayList<>();  //creates new courses for any subject that doesn't already have an open course running
        for (Subject subject: subjects){
            boolean stop = false;
            for (Course course: courses) {
                if (course.getSubject().equals(subject) && course.getStatus() != 0 && !course.isCancelled()) {
                    stop = true;
                    break;
                }
            }
            if (!stop){
                NewCourses.add(new Course(subject, 2));
            }


        }

        courses.addAll(NewCourses);

        // assigns a free instructor to any course that doesn't have one
        for (Course course : courses){
            if (!course.hasInstructor()){
                for (Instructor instructor: instructors){
                    if (instructor.canTeach(course.getSubject()) && instructor.getAssignedCourse() == null){
                        course.setInstructor(instructor);
                        instructor.assignCourse(course);
                        break;
                    }
                }
            }
        }

        // enrols free students onto courses they qualify for
        for (Course course : courses){
            for (Student student: students){
                if (course.getSize() < 3 && !student.hasCertificate(course.getSubject()) && !isEnrolled(student)){
                    course.enrolStudent(student);
                }
            }
        }

        // advances each course by one day and removes any that are finished or cancelled
        ArrayList<Course> removedCourses = new ArrayList<>();
        for (Course course : courses){
            course.aDayPasses();
            if (course.isCancelled() || course.getStatus() == 0){
                removedCourses.add(course);
            }
        }
        courses.removeAll(removedCourses);
    }

    public void EndOfTheDayUpdates(){

        ArrayList<Instructor> removedInstructors = new ArrayList<>();
        Random r = new Random();
        for (Instructor instructor : instructors){
            int amount = r.nextInt(100);
            if (instructor.getAssignedCourse() == null) {
                if (amount < 20){ // free instructors have a 20% chance of leaving
                    removedInstructors.add(instructor);
                }
            }
        }
        instructors.removeAll(removedInstructors);

        // builds a set of all subject IDs to check if a student has all certificates
        Set<Integer> subject_ids = new HashSet<>();
        for (Subject subject : subjects) {
            subject_ids.add(subject.getID());
        }


        // students leave if they have all certificates, otherwise 5% chance of leaving
        ArrayList<Student> removedStudents = new ArrayList<>();
        for (Student student: students){
            Set<Integer> certificates = new HashSet<>(student.getCertificates());
            if (subject_ids.equals(certificates)){
                removedStudents.add(student);
            } else {
                int amount2 = r.nextInt(100);
                if (amount2 < 5){
                    removedStudents.add(student);
                }
            }
        }
        students.removeAll(removedStudents);

    }

    // checks if a student is already enrolled on any course
    public boolean isEnrolled(Student student){
        for (Course course: courses){
            for (Student enrolledStudents: course.getStudents()){
                if (enrolledStudents == student){
                    return true;
                }
            }
        }
        return false;
    }

}
