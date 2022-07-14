package adventure.pkg.items;

public class Armour extends Item {

    // Base armour/defence attributes.
    private int acMod;

    public Armour(String name, int rarity, int acMod){
        super(name, rarity);
        this.acMod = acMod;
    }

    public int getAcMod() {return acMod;}
}
