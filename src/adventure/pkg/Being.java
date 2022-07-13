package adventure.pkg;

public abstract class Being {

    // Attributes.
    private int AC;
    private int HP;

    // Stat attributes.
    private int str;
    private int dex;
    private int con;

    // Getters and setters.
    public int getAC() {return AC;}
    public void setAC(int AC) {this.AC = AC;}
    public int getHP() {return HP;}
    public void setHP(int HP) {this.HP = HP;}

    // Stat getters and setters.
    public int getStr() {return str;}
    public void setStr(int str) {this.str = str;}
    public int getCon() {return con;}
    public void setCon(int con) {this.con = con;}
    public int getDex() {return dex;}
    public void setDex(int dex) {this.dex = dex;}
}
