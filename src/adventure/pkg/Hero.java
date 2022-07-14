package adventure.pkg;

import adventure.pkg.items.Armour;
import adventure.pkg.items.Weapon;
import adventure.pkg.monsters.*;

import java.util.Random;

public class Hero extends Being{

    // Attributes.
    private final String name;

    // Stat attributes.
    private int XP;
    private int level;
    private int maxHealth;

    // Modifiers. Unique to player character.
    private int atk;
    private int def;

    // Starting weapon and armour.
    private static final Weapon shortSword = new Weapon("short sword", 1, 1,1);
    private static final Armour leather = new Armour("leather armour", 1, 2);

    // Constructor.
    public Hero(String name){
        // Set base stats and gear.
        super(13, 20, shortSword, leather);
        // Set name.
        this.name = name;
        // Set leveling attributes.
        this.maxHealth = 20;
        this.XP = 0;
        this.level = 1;
        // Set modifiers.
        this.atk = 1;
        this.def = 1;
    }

    @Override
    public int getDefence(){
        return super.getDefence() + this.def;
    }

    public void levelUp(int xp){
        // Add new xp to total.
        this.XP += xp;
        // Evaluate level up conditions.
        if(this.XP >= (this.level * 100)){
            // Increase level by 1.
            this.level += 1;
            // Increase max health by 5;
            this.maxHealth += 5;
            this.setHP(this.getHP() + 5);   // Add new HP to total.
            // Increase modifiers by 1.
            this.atk += 1;
            this.def += 1;
            // Reset XP.
            this.XP -= (this.level * 100);
            // Provide feedback to player.
            System.out.println(this.name + " has leveled up!");
            System.out.println("Lvl: " + (this.level-1) + " -> " + this.level);
            System.out.println("Max HP: " + (this.maxHealth - 5) + " -> " + this.maxHealth);
            System.out.println("Atk: " + (this.atk-1) + " -> " + this.atk);
            System.out.println("Def: " + (this.def-1) + " -> " + this.def);
            System.out.println(" ");
        }
    }

    public void healthCheck(){
        System.out.println(this.name + ": HP - " + this.getHP());
        System.out.println(" ");
    }

    public int attack(Monster en){
        // Random number generator - dice.
        Random rand = new Random();
        // Store damage.
        int dmg = 0;

        // Try to hit.
        int hitRoll = rand.nextInt(1, 21);
        // Add modifier.
        int toHit = hitRoll + this.atk;
        // Report to player.
        System.out.println(this.getName() + " attacks the " + en.getType() + " with their sword for " + toHit + "!");
        // Check success of attack.
        if(toHit > en.getDefence()){
            // Calculate damage.
            dmg = rand.nextInt(1, 9);
            if(hitRoll == 20){
                System.out.println("Critical hit!");
                dmg *= 2;   // Double damage on crits.
            }
            dmg += this.getWeapon().getDmgMod() + this.atk;
            System.out.println(this.getName() + " hits the " + en.getType() + " for " + dmg + "!");
        }else{
            System.out.println(this.getName() + " misses!");
        }
        // Return damage dealt.
        return dmg;
    }

    // Getters and setters.
    public String getName() {return name;}
    public int getMaxHealth() {return maxHealth;}
    public void setMaxHealth(int maxHealth){this.maxHealth = maxHealth;}
    public void setAtk(int atk) {this.atk = atk;}
    public int getAtk() {return atk;}
    public void setDef(int def) {this.def = def;}
    public int getDef() {return def;}
}
