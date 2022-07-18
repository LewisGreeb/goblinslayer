package adventure.pkg;
import adventure.pkg.items.Item;
import adventure.pkg.monsters.*;
import java.util.ArrayList;

public class Zone {

    // Attributes.
    private final int xCoord;
    private final int yCoord;
    private ArrayList<Monster> enemies;
    private Item zItem;

    // Constructor.
    public Zone(int xCoord, int yCoord, ArrayList<Monster> enemies){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.enemies = enemies;
        this.zItem = null;
    }

    // Getters and setters.
    public int getXCoord() {return xCoord;}
    public int getYCoord() {return yCoord;}
    public ArrayList<Monster> getEnemies() {return enemies;}
    public void setEnemies(ArrayList<Monster> enemies) {this.enemies = enemies;}
    public Item getZItem(){return this.zItem;}
    public void setZItem(Item item){this.zItem = item;}

}
