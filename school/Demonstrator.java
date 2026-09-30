public class Demonstrator extends Instructor {

    public Demonstrator(){

    }

    public Demonstrator(String name, char gender, int age){
        super(name, gender, age);
    }

    public boolean canTeach(Subject subject){ //returns boolean based on ability to teach subjects
        int specialism = subject.getSpecialism();
        if (specialism == 2){
            return true;
        } else {
            return false;
        }
    }
}
