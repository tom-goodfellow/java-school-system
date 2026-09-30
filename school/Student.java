import java.util.ArrayList;
import java.util.Iterator;


public class Student extends Person{
    private ArrayList<Integer> certificates;

    public Student(){
        certificates = new ArrayList<>();
    }

    public Student(String name, char gender, int age){
        super(name, gender, age);
        this.certificates = new ArrayList<>();
    }

    public void graduate(Subject subject){
        int subject_id = subject.getID();
        certificates.add(subject_id);
    }

    public void addCertificate(int SubjectID){
        this.certificates.add(SubjectID);
    }

    public ArrayList<Integer> getCertificates(){
        return certificates;
    }

    public boolean hasCertificate(Subject subject){ //iterate through students certificates, if it matches the subject passed then return true
        Iterator<Integer> it = certificates.iterator();

        while (it.hasNext()){
            int certificate = it.next();
            if (certificate == subject.getID()){
                return true;
            }
        }

        return false;
    }

}
