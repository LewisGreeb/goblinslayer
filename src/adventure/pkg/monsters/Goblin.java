package adventure.pkg.monsters;

import adventure.pkg.Hero;
import adventure.pkg.items.Armour;
import adventure.pkg.items.Weapon;

import java.util.Random;

public class Goblin extends Monster {

    private static final Weapon shortSword = new Weapon("short sword", 1, 1,1);
    private static final Armour clothes = new Armour("Clothes", 1, 0);

    // Constructor.
    public Goblin(){
        super(10, 7, shortSword, clothes, "Goblin", 50);
    }

    @Override
    public int attack(Hero hero) {
        // Random number generator - dice.
        Random rand = new Random();

        int dmg = 0;

        int hitRoll = rand.nextInt(1, 21);   // Roll a d20.
        int toHit = hitRoll + this.getWeapon().getHitMod();
        System.out.println("The " + this.getType() + " attacks " + hero.getName() + " with their short sword for " + toHit + "!");
        // Check success of attack.
        if(toHit > hero.getDefence()){
            // Calculate damage.
            dmg = rand.nextInt(1, 7);
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
