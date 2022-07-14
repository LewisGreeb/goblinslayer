package adventure.pkg.monsters;

import adventure.pkg.Hero;

import java.util.Random;

public class GoblinShaman extends Goblin{

    private int MP;

    public GoblinShaman(){
        super();
        // Set unique type name and reduce AC from Goblin base.
        this.setType("Goblin Shaman");
        this.setAC(9);

        // Set a semi-random value for MP.
        Random rand = new Random();
        // Base 8 for two fire bolts, potential for up to two more.
        this.MP = (8 + rand.nextInt(1, 9));
    }

    @Override
    public int attack(Hero hero) {
        // Random number generator - dice.
        Random rand = new Random();

        int dmg = 0;
        int toHit = rand.nextInt(1, 21);   // Roll a d20.

        // Use magic if available.
        if(this.MP > 4){
            System.out.println("The " + this.getType() + " uses firebolt to attack " + hero.getName() + " for " + toHit + "!");
            // Check success of attack.
            if(toHit > hero.getDefence()){
                // Calculate damage.
                dmg = rand.nextInt(1, 11);   // d10 for a firebolt.
                if(toHit == 20){
                    dmg *= 2;   // Double damage on crits.
                }
                System.out.println("The " + this.getType() + " hits " + hero.getName() + " for " + dmg + "!");
            }else{
                System.out.println("The " + this.getType() + " misses!");
            }
            // Consume MP for casting spells.
            this.MP -= 4;
        }else{
            System.out.println("The " + this.getType() + " attacks " + hero.getName() + " with their dagger for " + toHit + "!");
            if(toHit == 20){
                System.out.println("Critical hit!");
            }
            // Check success of attack.
            if(toHit > hero.getDefence()){
                // Calculate damage.
                dmg = rand.nextInt(1, 5);   // d4 for a dagger.
                if(toHit == 20){
                    dmg *= 2;   // Double damage on crits.
                }
                System.out.println("The " + this.getType() + " hits " + hero.getName() + " for " + dmg + "!");
            }else{
                System.out.println("The " + this.getType() + " misses!");
            }
        }
        // Return damage.
        return dmg;
    }
}
