public abstract class Instructor extends Person {
    protected Course assignedCourse;

    public Instructor(){

    }

    public Instructor(String name, char gender, int age){
        super(name, gender, age);
    }

    public void assignCourse(Course course){
        if (course != null){
            this.assignedCourse = course;
        } else {
            this.assignedCourse = null;
        }
    }
    public void unassignCourse(){
        assignedCourse = null;
    }
    public Course getAssignedCourse(){
        return assignedCourse;
    }
    public abstract boolean canTeach(Subject subject);



}
