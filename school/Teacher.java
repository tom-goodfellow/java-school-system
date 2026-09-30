public class Teacher extends Instructor{

    public Teacher(){

    }

    public Teacher(String name, char gender, int age){
        super(name,gender,age);
    }

    public boolean canTeach(Subject subject){
        int specialism = subject.getSpecialism();
        if (specialism == 1 || specialism == 2){
            return true;
        } else {
            return false;
        }
    }
}
