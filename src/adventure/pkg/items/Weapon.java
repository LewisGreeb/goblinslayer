package adventure.pkg.items;

public class Weapon extends Item{

    // Base weapon stat attributes.
    private int hitMod;
    private int dmgMod;
    private int dice;

    // Constructor.
    public Weapon(String name, int rarity, int hitMod, int dmgMod, int dice){
        super(name, rarity);
        this.hitMod = hitMod;
        this.dmgMod = dmgMod;
        this.dice = dice;
    }

    // Getters and setters.
    public int getHitMod() {return hitMod;}
    public int getDmgMod() {return dmgMod;}
    public int getDice() {return dice;}

}
