package adventure.pkg;
import adventure.pkg.monsters.*;
import java.util.ArrayList;

public class Zone {

    // Attributes.
    private int xCoord;
    private int yCoord;
    private ArrayList<Monster> enemies;
    private String obstacle;

    // Constructor.
    public Zone(int xCoord, int yCoord, ArrayList<Monster> enemies, String obstacle){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.enemies = enemies;
        this.obstacle = obstacle;
    }

    // Getters and setters.
    public int getXCoord() {return xCoord;}
    public void setXCoord(int xCoord) {this.xCoord = xCoord;}
    public int getYCoord() {return yCoord;}
    public void setYCoord(int yCoord) {this.yCoord = yCoord;}
    public ArrayList<Monster> getEnemies() {return enemies;}
    public void setEnemies(ArrayList<Monster> enemies) {this.enemies = enemies;}
    public String getObstacle() {return obstacle;}

}
