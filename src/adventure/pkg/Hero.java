package adventure.pkg;

public class Hero extends Being{

    // Attributes.
    private String name;

    // Constructor.
    public Hero(String name){
        this.name = name;
        this.setHP(20);
        this.setAC(18);
    }

    // Getters and setters.
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

}
