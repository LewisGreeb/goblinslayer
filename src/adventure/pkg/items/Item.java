package adventure.pkg.items;

public abstract class Item {

    // Attributes.
    private String name;
    private int rarity;

    // Constructor.
    public Item(String name, int rarity){
        this.name = name;
        this.rarity = rarity;
    }

    // Getters and setters.
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getRarity() {return rarity;}
    public void setRarity(int rarity) {this.rarity = rarity;}
}
