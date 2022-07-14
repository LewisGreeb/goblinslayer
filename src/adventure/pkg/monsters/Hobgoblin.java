package adventure.pkg.monsters;

import adventure.pkg.Hero;
import adventure.pkg.items.Armour;
import adventure.pkg.items.Weapon;

import java.util.Random;

public class Hobgoblin extends Monster{

    private static final Weapon club = new Weapon("club", 1, 1,3);
    private static final Armour leather = new Armour("leather armour", 1, 2);


    public Hobgoblin(){
        super(13, 15, club, leather,"Hobgoblin", 100);
    }

    @Override
    public int attack(Hero hero) {
        // Random number generator - dice.
        Random rand = new Random();
        // Setup var to store damage.
        int dmg = 0;
        // Roll to hit.
        int hitRoll = rand.nextInt(1, 21);   // Roll a d20.
        int toHit = hitRoll + this.getWeapon().getHitMod() + 2;   // Constant +2 to hit.
        System.out.println("The " + this.getType() + " attacks " + hero.getName() + " with their club for " + toHit + "!");

        // Check success of attack.
        if(toHit > hero.getDefence()){
            // Calculate damage.
            dmg = rand.nextInt(1, 10);
            if(hitRoll == 20){
                System.out.println("Critical hit!");
                dmg *= 2;   // Double damage on crits.
            }
            dmg += this.getWeapon().getDmgMod();
            System.out.println("The " + this.getType() + " hits " + hero.getName() + " for " + dmg + "!");
        }else{
            System.out.println("The " + this.getType() + " misses!");
        }

        return dmg;
    }
}
