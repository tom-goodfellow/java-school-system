import java.util.ArrayList;

public class Course {
    private Subject subject;
    private int daysUntilStarts;
    private int daysToRun;
    private ArrayList<Student> students;
    private Instructor instructor;
    private boolean cancelled;

    public Course(Subject subject, int daysUntilStarts){
        this.subject = subject;
        this.daysUntilStarts = daysUntilStarts;
        this.daysToRun = subject.getDuration();
        this.students = new ArrayList<>();
        this.instructor = null;
        this.cancelled = false;
    }

    //Two constructors for different object initialisation

    public Course(Subject subject, int daysUntilStarts, int daysToRun, boolean cancelled){
        this.subject = subject;
        this.daysUntilStarts = daysUntilStarts;
        this.daysToRun = daysToRun;
        this.students = new ArrayList<>();
        this.instructor = null;
        this.cancelled = cancelled;
    }

    //Getters
    public Subject getSubject(){
        return subject;
    }

    public int getDaysUntilStarts(){
        return daysUntilStarts;
    }

    public int getDaysToRun(){
        return daysToRun;
    }

    public Instructor getInstructor(){
        return instructor;
    }

    public int getStatus(){
        if (daysUntilStarts > 0){
            return (daysUntilStarts*-1);
        } else if (daysToRun > 0) {
            return daysToRun;
        } else {
            return 0;
        }
    }

    public void aDayPasses(){

        if (daysUntilStarts > 0){ //status of days remaining and ahead
            daysUntilStarts -= 1;
            if (daysUntilStarts == 0 && (students.isEmpty() || !hasInstructor())) { //if already started, with no students and constructor then cancel it
                this.cancelled = true;
            }
        } else {
            daysToRun -= 1;
        }

        if (daysToRun == 0) { //graduates and unassigns instructors
            for (Student student : students) {
                student.graduate(this.subject);
            }
            if (instructor != null) {
                instructor.unassignCourse();
                this.instructor = null;
            }
        }

    }

    //Enrols if hasn't started and course is not full.
    public boolean enrolStudent(Student student){
        if (daysUntilStarts > 0 && students.size() < 3){
            students.add(student);
            return true;
        }
        return false;


    }

    public int getSize() {
        return students.size();
    }

    public Student[] getStudents(){
        Student[] students_array = new Student[getSize()];
        for (int i =0; i < getSize(); i++){
            students_array[i] = students.get(i);
        }
        return students_array;
    }

    public boolean setInstructor(Instructor instructor){
        if (instructor.canTeach(this.subject)){
            this.instructor = instructor;
            instructor.assignCourse(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean hasInstructor(){
        if (this.instructor != null){
            return true;
        } else {
            return false;
        }
    }

    public boolean isCancelled(){
        if (cancelled){
            return true;
        } else {
            return false;
        }
    }

}