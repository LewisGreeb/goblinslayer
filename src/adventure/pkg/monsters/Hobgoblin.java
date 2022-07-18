package adventure.pkg.monsters;

import adventure.pkg.Hero;
import adventure.pkg.items.Armour;
import adventure.pkg.items.Weapon;

import java.util.Random;

public class Hobgoblin extends Monster{

    private static final Weapon club = new Weapon("club", 1, 1,3, 9);
    private static final Armour leather = new Armour("leather armour", 1, 1);

    public Hobgoblin(){
        super(13, 15, club, leather,"Hobgoblin", 100);
    }

}
