public class GUITrainer extends Teacher{


    public GUITrainer(){

    }

    public GUITrainer(String name, char gender, int age){
        super(name, gender, age);
    }

    public boolean canTeach(Subject subject){
        int specialism = subject.getSpecialism();
        if (super.canTeach(subject) || specialism == 4){
            return true;
        } else {
            return false;
        }
    }
}
