package adventure.pkg.monsters;

import adventure.pkg.items.*;

public class GoblinLord extends Monster{

    private static final Weapon greatAxe = new Weapon("double-bladed great axe", 4, 3,4, 13);
    private static final Armour plateArmour = new Armour("plate armour", 3, 3);

    public GoblinLord(){
        super(15, 40, greatAxe, plateArmour, "Goblin Lord", 1000);
    }

}
