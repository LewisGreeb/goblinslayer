package adventure.pkg.monsters;
import adventure.pkg.Being;
import adventure.pkg.Hero;

public abstract class Monster extends Being {

    private String type;

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}

    public abstract int attack(Hero hero);
}
