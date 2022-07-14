package adventure.pkg.monsters;

import adventure.pkg.Hero;
import java.util.Random;

public class Hobgoblin extends Monster{

    public Hobgoblin(){
        this.setHP(15);
        this.setAC(14);
        this.setType("Hobgoblin");
        this.setXpValue(100);
    }

    @Override
    public int attack(Hero hero) {
        // Random number generator - dice.
        Random rand = new Random();
        // Setup var to store damage.
        int dmg = 0;
        // Roll to hit.
        int hitRoll = rand.nextInt(1, 21);   // Roll a d20.
        int toHit = hitRoll + 2;   // Constant +2 to hit.
        System.out.println("The " + this.getType() + " attacks " + hero.getName() + " with their club for " + toHit + "!");

        // Check success of attack.
        if(toHit > hero.getDefence()){
            // Calculate damage.
            dmg = rand.nextInt(1, 10);
            if(hitRoll == 20){
                System.out.println("Critical hit!");
                dmg *= 2;   // Double damage on crits.
            }
            System.out.println("The " + this.getType() + " hits " + hero.getName() + " for " + dmg + "!");
        }else{
            System.out.println("The " + this.getType() + " misses!");
        }

        return dmg;
    }
}
