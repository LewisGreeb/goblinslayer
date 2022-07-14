package adventure.pkg;
import adventure.pkg.items.Item;
import adventure.pkg.monsters.*;
import java.util.ArrayList;

public class Zone {

    // Attributes.
    private int xCoord;
    private int yCoord;
    private ArrayList<Monster> enemies;
    private String obstacle;
    private Item zItem;

    // Constructor.
    public Zone(int xCoord, int yCoord, ArrayList<Monster> enemies, String obstacle){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.enemies = enemies;
        this.obstacle = obstacle;
    }

    // Methods.
    public void setZItem(Item item){
        this.zItem = item;
    }


    // Getters and setters.
    public int getXCoord() {return xCoord;}
    public int getYCoord() {return yCoord;}
    public ArrayList<Monster> getEnemies() {return enemies;}
    public void setEnemies(ArrayList<Monster> enemies) {this.enemies = enemies;}
    public String getObstacle() {return obstacle;}
    public Item getZItem(){return this.zItem;}

}
