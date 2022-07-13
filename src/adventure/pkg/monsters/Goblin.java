package adventure.pkg.monsters;

import adventure.pkg.Hero;

import java.util.Random;

public class Goblin extends Monster {

    // Constructor.
    public Goblin(){
        this.setHP(7);
        this.setAC(13);
        this.setType("Goblin");
    }

    @Override
    public int attack(Hero hero) {
        // Random number generator - dice.
        Random rand = new Random();

        int dmg = 0;

        int toHit = rand.nextInt(1, 21);   // Roll a d20.
        System.out.println("The " + this.getType() + " attacks " + hero.getName() + " with their short sword for " + toHit + "!");
        if(toHit == 20){
            System.out.println("Critical hit!");
        }
        // Check success of attack.
        if(toHit > hero.getAC()){
            // Calculate damage.
            dmg = rand.nextInt(1, 7);
            if(toHit == 20){
                dmg *= 2;   // Double damage on crits.
            }
            System.out.println("The " + this.getType() + " hits " + hero.getName() + " for " + dmg + "!");
        }else{
            System.out.println("The " + this.getType() + " misses!");
        }

        return dmg;
    }
}
