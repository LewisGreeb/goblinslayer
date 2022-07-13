package adventure.pkg;

import adventure.pkg.monsters.*;

import java.util.Random;

public class Hero extends Being{

    // Attributes.
    private String name;

    // Constructor.
    public Hero(String name){
        this.name = name;
        this.setHP(200);
        this.setAC(18);
    }

    public int attack(Monster en){
        // Random number generator - dice.
        Random rand = new Random();

        int dmg = 0;

        // Try to hit.
        int toHit = rand.nextInt(1, 21);
        System.out.println(this.getName() + " attacks the " + en.getType() + " with their sword for " + toHit + "!");
        if(toHit == 20){
            System.out.println("Critical hit!");
        }
        // Check success of attack.
        if(toHit > en.getAC()){
            // Calculate damage.
            dmg = rand.nextInt(1, 9);
            if(toHit == 20){
                dmg *= 2;   // Double damage on crits.
            }
            System.out.println(this.getName() + " hits the " + en.getType() + " for " + dmg + "!");
        }else{
            System.out.println(this.getName() + " misses!");
        }

        return dmg;
    }

    // Getters and setters.
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

}
