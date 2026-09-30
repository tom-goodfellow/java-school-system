public class OOTrainer extends Teacher{

    public OOTrainer(){

    }

    public OOTrainer(String name, char gender, int age){
        super(name, gender, age);
    }

    public boolean canTeach(Subject subject){
        int specialism = subject.getSpecialism();
        if (super.canTeach(subject) == true || specialism == 3){
            return true;
        } else {
            return false;
        }
    }
}
