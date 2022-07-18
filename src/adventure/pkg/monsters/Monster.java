package adventure.pkg.monsters;
import adventure.pkg.Being;
import adventure.pkg.Hero;
import adventure.pkg.items.*;

import java.util.Random;

public abstract class Monster extends Being {

    private String type;
    private int xpValue;

    public Monster(int AC, int HP, Weapon weapon, Armour armour, String type, int xpValue){
        super(AC, HP, weapon, armour);
        this.type = type;
        this.xpValue = xpValue;
    }

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public int getXpValue() {return xpValue;}

    public int attack(Hero hero){
        // Random number generator - dice.
        Random rand = new Random();

        int dmg = 0;

        int hitRoll = rand.nextInt(1, 21);   // Roll a d20.
        int toHit = hitRoll + this.getWeapon().getHitMod();
        System.out.println("The " + this.getType() + " attacks " + hero.getName() + " with their " + this.getWeapon().getName() + " for " + toHit + "!");
        // Check success of attack.
        if(toHit > hero.getDefence()){
            // Calculate damage.
            dmg = rand.nextInt(1, this.getWeapon().getDice());
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
    };
}
