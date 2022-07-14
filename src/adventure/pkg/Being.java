package adventure.pkg;

public abstract class Being {

    // Attributes.
    private int AC;
    private int HP;

    public Being(int AC, int HP){
        this.AC = AC;
        this.HP = HP;
    }

    // Getters and setters.
    public int getAC() {return AC;}
    public void setAC(int AC) {this.AC = AC;}
    public int getHP() {return HP;}
    public void setHP(int HP) {this.HP = HP;}

}
