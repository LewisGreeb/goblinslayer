package adventure.pkg.monsters;
import adventure.pkg.Being;

public abstract class Monster extends Being {

    private String type;

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
}