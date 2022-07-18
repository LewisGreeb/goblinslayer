package adventure.pkg.monsters;

import adventure.pkg.items.*;


public class Goblin extends Monster {

    private static final Weapon shortSword = new Weapon("short sword", 1, 1,1, 7);
    private static final Armour clothes = new Armour("Clothes", 1, 0);

    // Constructor.
    public Goblin(){
        super(10, 7, shortSword, clothes, "Goblin", 50);
    }

}
