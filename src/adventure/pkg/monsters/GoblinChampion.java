package adventure.pkg.monsters;

import adventure.pkg.items.*;

public class GoblinChampion extends Monster{

    private static final Weapon warHammer = new Weapon("war hammer", 3, 2,3, 11);
    private static final Armour scaleArmour = new Armour("scale armour", 2, 2);

    public GoblinChampion(){
        super(14, 30, warHammer, scaleArmour, "Goblin Champion", 500);
    }

}
