public class Person {
    private String name;
    private char gender;
    private int age;

    public Person(){

    }

    public Person(String name, char gender, int age){  //All classes that extend utilise this constructor
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName(){
        return name;
    }

    public char getGender(){
        return gender;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return age;
    }



}
