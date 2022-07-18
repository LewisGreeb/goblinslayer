package adventure.pkg;

import adventure.pkg.items.*;
import adventure.pkg.monsters.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
    private static final Weapon shortSword = new Weapon("short sword", 1, 1,1, 7);
    private static final Armour leather = new Armour("leather armour", 1, 1);

    // Inventory of items.
    private ArrayList<Item> inventory = new ArrayList<>();
    // Slot for shield as extra armour unique to PC.
    private Armour shield;

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

    public boolean useItem(Scanner scanner){
        if(this.inventory.isEmpty()){
            System.out.println("No items in inventory.");
            return false;
        }else{
            // Display items in inventory.
            System.out.println("Available items:");
            System.out.print("| ");
            for (Item item : this.inventory){
                System.out.print(item.getName());
                System.out.print(" | ");
            }
            System.out.println(" ");
            // Select item.
            int iItem = Integer.MAX_VALUE;
            boolean valid;
            do{
                System.out.println("Which item do you want to use?");
                String item = scanner.nextLine();
                try{
                    iItem = Integer.parseInt(item);
                    iItem -= 1;
                    valid = true;
                    // Check and respond to user input.
                    if (iItem >= this.inventory.size() || iItem < -1){
                        // Catch number with no corresponding enemy.
                        System.out.println("Invalid entry - Enter a number with a corresponding item or 0 to exit.");
                        // Loop for input again.
                        valid = false;
                    }else if(iItem == -1){
                        System.out.println("Closing inventory.");
                        System.out.println(" ");
                        return false;
                    }else{
                        // Apply item effect.
                        ((Potion) this.inventory.get(iItem)).use(this);
                        System.out.println(this.inventory.get(iItem).getName() + " used!");
                        // Remove item after use.
                        this.inventory.remove(iItem);
                    }
                }catch(Exception ex){
                    // Catch if not a number.
                    System.out.println("Invalid entry - Enter a number. (no spaces or other characters)");
                    // Loop for input again.
                    valid = false;
                }

            }while(!valid);
            // Space for aesthetics.
            System.out.println(" ");
            return true;
        }
    }

    public void statCheck(){
        System.out.println(this.name + ": HP - " + this.getHP() + "  AC - " + this.getDefence() + "  Lvl - " + this.level);
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
            dmg = rand.nextInt(1, this.getWeapon().getDice());
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
    public ArrayList<Item> getInventory() {return inventory;}
    public void setInventory(ArrayList<Item> inventory) {this.inventory = inventory;}
}
