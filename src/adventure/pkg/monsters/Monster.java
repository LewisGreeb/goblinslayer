package adventure.pkg.monsters;
import adventure.pkg.Being;
import adventure.pkg.Hero;

public abstract class Monster extends Being {

    private String type;
    private int xpValue;

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public int getXpValue() {return xpValue;}
    public void setXpValue(int xpValue) {this.xpValue = xpValue;}

    public abstract int attack(Hero hero);
}
