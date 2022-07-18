package adventure.pkg;

import adventure.pkg.items.*;


public abstract class Being {

    // Attributes.
    private int AC;
    private int HP;
    private Weapon weapon;
    private Armour armour;
    private Armour shield;

    public Being(int AC, int HP, Weapon weapon, Armour armour){
        this.AC = AC;
        this.HP = HP;
        this.weapon = weapon;
        this.armour = armour;
    }

    // Get defence.
    public int getDefence(){
        return this.AC + this.getArmour().getAcMod();
    }

    // Getters and setters.
    public void setAC(int AC) {this.AC = AC;}
    public int getHP() {return HP;}
    public void setHP(int HP) {this.HP = HP;}
    public Weapon getWeapon() {return weapon;}
    public void setWeapon(Weapon weapon) {this.weapon = weapon;}
    public Armour getArmour() {return armour;}
    public void setArmour(Armour armour) {this.armour = armour;}
    public Armour getShield() {return shield;}
    public void setShield(Armour shield) {this.shield = shield;}
}
