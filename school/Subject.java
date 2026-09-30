public class Subject{
    private int ID;
    private int specialism;
    private int duration;
    private String description;

    public Subject(int ID, int specialism, int duration, String description){
        this.ID = ID;
        this.specialism = specialism;
        this.duration = duration;
        this.description = description;
    }

    public int getID(){
        return ID;
    }

    public int getSpecialism(){
        return specialism;
    }

    public int getDuration(){
        return duration;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }


}