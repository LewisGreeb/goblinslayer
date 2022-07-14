package adventure.pkg.items;

public class Weapon extends Item{

    // Base weapon stat attributes.
    private int hitMod;
    private int dmgMod;

    // Constructor.
    public Weapon(String name, int rarity, int hitMod, int dmgMod){
        super(name, rarity);
        this.hitMod = hitMod;
        this.dmgMod = dmgMod;
    }

    // Getters and setters.
    public int getHitMod() {return hitMod;}
    public void setHitMod(int hitMod) {this.hitMod = hitMod;}
    public int getDmgMod() {return dmgMod;}
    public void setDmgMod(int dmgMod) {this.dmgMod = dmgMod;}
}
