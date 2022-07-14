package adventure.pkg.monsters;

import adventure.pkg.Hero;

import java.util.Random;

public class GoblinShaman extends Goblin{

    private int MP;

    public GoblinShaman(){
        super();
        this.setType("Goblin Shaman");
        this.setAC(9);
        this.setMP(10);
    }

    public int getMP() {return MP;}
    public void setMP(int MP) {this.MP = MP;}

    @Override
    public int attack(Hero hero) {
        // Random number generator - dice.
        Random rand = new Random();

        int dmg = 0;
        int toHit = rand.nextInt(1, 21);   // Roll a d20.

        if(this.getMP() > 4){
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
            this.setMP(this.getMP()-4);
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

        return dmg;
    }
}
