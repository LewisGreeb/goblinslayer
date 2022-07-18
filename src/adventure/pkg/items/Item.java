package adventure.pkg.items;

public abstract class Item {

    // Attributes.
    private String name;
    private final int rarity;

    // Constructor.
    public Item(String name, int rarity){
        this.name = name;
        this.rarity = rarity;
    }

    // Getters and setters.
    public String getName() {return name;}
}
