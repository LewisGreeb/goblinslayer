package adventure.pkg;

import adventure.pkg.monsters.*;
import adventure.pkg.items.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameBoard {

    // Attributes.
    private Zone currentZone;
    private Zone exit;
    private ArrayList<Zone> zoneHistory;
    private Compass compass;

    // Constructor.
    public GameBoard(int exitX, int exitY){
        // Initialise start zone.
        ArrayList<Monster> monsters = new ArrayList<>();
        this.currentZone = new Zone(0,0, monsters, "This is the beginning.");

        // Initialise end zone.
        GoblinLord goblinLord = new GoblinLord();
        monsters.add(goblinLord);
        this.exit = new Zone(exitX, exitY, monsters, "");

        // Initialise compass.
        this.compass = new Compass(0,0,exitX,exitY);

        // Initialise zone history with exit and current zone.
        this.zoneHistory = new ArrayList<>();
        this.zoneHistory.add(this.exit);
        this.zoneHistory.add(this.currentZone);
    }

    // Methods.
    public boolean getInput(Scanner scanner, boolean fight, Hero hero){
        // Track action success.
        boolean success = false;

        // Track valid input.
        boolean valid = false;

        // Get and process input.
        do{
            // Get player input.
            System.out.println("What would you like to do?");
            String input = scanner.nextLine();
            String lInput = input.toLowerCase();

            // If player wants to move and is not in a fight.
            if(lInput.contains("help")){
                // Denote valid response.
                valid = true;
                // Print health check.
                printHelp();
                // Return success.
                success = true;
            }else if((lInput.contains("north") || lInput.contains("south") || lInput.contains("east") || lInput.contains("west")) && !fight){
                // Denote valid response.
                valid = true;
                // Trigger move action.
                success = move(lInput);
            }else if((lInput.contains("rest") || lInput.contains("camp")) && !fight){
                // Denote valid response.
                valid = true;
                // Trigger rest action.
                success = camp(scanner, hero);
            }else if(lInput.contains("take") && !fight) {
                // Denote valid response.
                valid = true;
                // Trigger rest action.
                success = takeItem(scanner, hero);
            }else if((lInput.contains("use") || lInput.contains("item")) && !fight){
                // Denote valid response.
                valid = true;
                // Process item and escape loop.
                hero.useItem(scanner);
                success = true;
            }else if(lInput.contains("check")){
                // Denote valid response.
                valid = true;
                // Print health check.
                hero.statCheck();
                // Return success.
                success = true;
            }else if(lInput.contains("fight") && fight){
                // Denote valid response.
                valid = true;
                // Trigger fight.
                success = combatEncounter(scanner, hero);
            }else if(lInput.contains("flee") && fight){
                // Denote valid response.
                valid = true;
                // Set success flag.
                success = fleeCombat();
            }

            // If no valid action was detected.
            if(!valid && fight){
                // Display error message.
                System.out.println("Enemies have approached, you must 'fight' or 'flee'.");
            }else if(!valid){
                // Display error message.
                System.out.println("No valid action detected.");
            }

        }while(!valid);

        // Return success of action.
        return success;
    }

    private void printHelp(){
        System.out.println("~~~ Goblin Slayer Controls ~~~");
        System.out.println(" ");
        System.out.println("Input a compass direction to move between zones - north, south, east or west.");
        System.out.println("Some zones contain items like new gear or potions! Take these items with the 'take' command.");
        System.out.println("To check the health and defence of your character, type 'check'");
        System.out.println("To heal after battle, type 'rest' or 'camp' - though you might be attacked as you sleep!");
        System.out.println("Explore and fight goblins to grow stronger - find your way to the exit to win!");
        System.out.println(" ");
    }

    private boolean yesNo(Scanner scanner){
        // Get input.
        String input = scanner.nextLine();
        String lInput = input.toLowerCase();
        // Validate input.
        while(!lInput.contains("y") && !lInput.contains("n")){
            System.out.println("Please enter 'yes' or 'no'.");
            input = scanner.nextLine();
            lInput = input.toLowerCase();
        }
        // Return true if 'yes'.
        return lInput.contains("y");
    }

    private boolean takeItem(Scanner scanner, Hero hero){
        // Get item from current zone.
        Item zoneItem = this.currentZone.getZItem();
        if(zoneItem != null){
            // Check item type.
            if (zoneItem instanceof Weapon){
                // Compare stats of new weapon to current weapon.
                System.out.println("New weapon:");
                System.out.println(zoneItem.getName() + ": Hit Mod - " + ((Weapon) zoneItem).getHitMod() + "   Dmg Mod - " + ((Weapon) zoneItem).getDmgMod());
                System.out.println("Current weapon:");
                System.out.println(hero.getWeapon().getName() + ": Hit Mod - " + hero.getWeapon().getHitMod() + "   Dmg Mod - " + hero.getWeapon().getDmgMod());
                System.out.println(" ");
                System.out.println("Replace the " + hero.getWeapon().getName() + " with the " + zoneItem.getName() + "?");
                // Check if hero wants weapon.
                if(yesNo(scanner)){
                    hero.setWeapon((Weapon) zoneItem);
                    System.out.println(hero.getName() + " equips the " + zoneItem.getName());
                }
            }else if(zoneItem instanceof Armour){
                // Check if item is normal armour or a shield.
                if(zoneItem.getName().contains("shield")){
                    if(hero.getShield() == null){
                        // Hero finds first shield.
                        System.out.println(hero.getName() + " found a shield!");
                        hero.setShield((Armour) zoneItem);
                        System.out.println(hero.getShield().getName() + ": Defence Mod - " + hero.getShield().getAcMod());
                    }else{
                        // Compare stats of new shield to current shield.
                        System.out.println("New shield:");
                        System.out.println(zoneItem.getName() + ": Defence Mod - " + ((Armour) zoneItem).getAcMod());
                        System.out.println("Current shield:");
                        System.out.println(hero.getShield().getName() + ": Defence Mod - " + hero.getShield().getAcMod());
                        System.out.println(" ");
                        System.out.println("Replace the " + hero.getShield().getName() + " with the " + zoneItem.getName() + "?");
                        // Check if hero wants shield.
                        if(yesNo(scanner)){
                            hero.setShield((Armour) zoneItem);
                            System.out.println(hero.getName() + " equips the " + zoneItem.getName());
                        }
                    }
                }else{
                    // Compare stats of new armour to current armour.
                    System.out.println("New armour:");
                    System.out.println(zoneItem.getName() + ": Defence Mod - " + ((Armour) zoneItem).getAcMod());
                    System.out.println("Current armour:");
                    System.out.println(hero.getArmour().getName() + ": Defence Mod - " + hero.getArmour().getAcMod());
                    System.out.println(" ");
                    System.out.println("Replace the " + hero.getArmour().getName() + " with the " + zoneItem.getName() + "?");
                    // Check if hero wants armour.
                    if(yesNo(scanner)){
                        hero.setArmour((Armour) zoneItem);
                        System.out.println(hero.getName() + " equips the " + zoneItem.getName());
                    }
                }
            }else{
                // Inform hero of potion type.
                System.out.println(hero.getName() + " found a " + zoneItem.getName() + "!");
                // Inform hero of potion effect.
                ((Potion) zoneItem).info();
                // Add potion to inventory.
                ArrayList<Item> inventory = hero.getInventory();
                inventory.add(zoneItem);
                hero.setInventory(inventory);
            }
            // Set zone item to null.
            this.currentZone.setZItem(null);
        }else{
            System.out.println("No item to take.");
        }

        // Space for aesthetics.
        System.out.println(" ");

        // Return success.
        return true;
    }

    private boolean camp(Scanner scanner, Hero hero){
        // Random to use as dice.
        Random rand = new Random();
        // Boolean to track if the user wants to rest.
        boolean rest;
        do{
            // Determine by chance if rest is interrupted.
            if(rand.nextInt(6) > 4){
                // Rest is interrupted.
                System.out.println(hero.getName() + "'s camp is attacked!");
                // Add more enemies to current zone, as if they're arriving while you rest.
                this.currentZone.setEnemies(getLowLevelEnemies(4));
                // Trigger combat.
                boolean success = combatEncounter(scanner, hero);
                // If you survive, ask if they want to try to rest again.
                if(success){
                    System.out.println("Do you want to try to rest again?");
                    // Set flag from user response.
                    rest = yesNo(scanner);
                }else{
                    // Indicate player death to game loop.
                    return false;
                }
            }else{
                // Rest is uninterrupted.
                System.out.println(hero.getName() + " is able to rest.");
                System.out.println(" ");
                // HP restored successfully.
                hero.setHP(hero.getMaxHealth());
                // Rest successful, break loop.
                rest = false;
            }
        }while(rest);

        // Return success.
        return true;
    }

    private boolean fleeCombat(){
        // Make the current zone the last zone visited before current.
        this.currentZone = this.zoneHistory.get(this.zoneHistory.size()-2);
        // Provide feedback to player.
        System.out.println("You flee back the way you came.");
        System.out.println(" ");
        // Set success flag.
        return true;
    }

    private boolean combatEncounter(Scanner scanner, Hero hero){
        // Guide player.
        System.out.println("You've entered combat! (attack, flee, check, or use item)");

        // Random number generator - dice.
        Random rand = new Random();

        // Get xp gained for this combat.
        int xpGain = 0;
        for (Monster en : this.currentZone.getEnemies()){
            xpGain += en.getXpValue();
        }

        // Enter combat loop.
        do{
            // Track target.
            boolean validTarget;
            int iEnemy = 0;

            // Show enemies.
            printEnemies(this.currentZone.getEnemies());

            // Player selects enemy to attack.
            do{
                System.out.println("What do you want to do?");
                String input = scanner.nextLine();
                if (input.contains("check")){
                    // Print stats.
                    hero.statCheck();
                    // Loop for input again.
                    validTarget = false;
                }else if(input.contains("flee")){
                    // Random 1/2 chance to successfully flee mid-combat.
                    int coin = rand.nextInt(2);
                    if(coin == 0){
                        return fleeCombat();   // Flee successfully.
                    }else{
                        System.out.println("Your way is blocked by the enemy!");
                    }
                    // Set target to -1 to denote unsuccessful attempt to flee.
                    iEnemy = -1;
                    // Escape loop.
                    validTarget = true;
                }else if(input.contains("item")){
                    // Process item and escape loop.
                    validTarget = hero.useItem(scanner);
                    // Set target to -1 to denote no attack made.
                    iEnemy = -1;
                }else if(input.contains("attack")){
                    // Show enemies.
                    printEnemies(this.currentZone.getEnemies());
                    System.out.println("Which enemy do you want to attack?");
                    String enemy = scanner.nextLine();
                    try{
                        iEnemy = Integer.parseInt(enemy);
                        iEnemy -= 1;
                        validTarget = true;
                    }catch(Exception ex){
                        // Catch if not a number.
                        System.out.println("Invalid entry - Enter a number. (no spaces or other characters)");
                        // Loop for input again.
                        validTarget = false;
                    }
                    if (iEnemy >= this.currentZone.getEnemies().size()){
                        // Catch number with no corresponding enemy.
                        System.out.println("Invalid entry - Enter a number with a corresponding enemy.");
                        // Loop for input again.
                        validTarget = false;
                    }
                }else{
                    System.out.println("No valid action detected.");
                    // Loop for input again.
                    validTarget = false;
                }
            }while(!validTarget);

            // If the hero chooses to attack.
            if(iEnemy != -1) {
                // Selected monster.
                Monster selectedEnemy = this.currentZone.getEnemies().get(iEnemy);

                // Hero attacks selected enemy.
                selectedEnemy.setHP(selectedEnemy.getHP() - hero.attack(selectedEnemy));

                // Determine if the enemy was defeated.
                if (selectedEnemy.getHP() <= 0) {
                    System.out.println("The " + selectedEnemy.getType() + " is defeated!");
                    this.currentZone.getEnemies().remove(iEnemy);
                } else {
                    this.currentZone.getEnemies().set(iEnemy, selectedEnemy);
                }
            }

            // If the enemies aren't dead.
            if(currentZone.getEnemies().size() > 0){
                // Goblin's turn.
                System.out.println(" ");
                System.out.println("Enemy's turn:");
                // Loop for number of goblins.
                for (Monster en : this.currentZone.getEnemies()){
                    // Each goblin makes an attack.
                    hero.setHP(hero.getHP()-en.attack(hero));
                }
            }

            // Check hero's hp.
            if(hero.getHP() <= 0){
                System.out.println("You died!");
                // Return failure.
                return false;
            }

        }while(hero.getHP() > 0 && currentZone.getEnemies().size() > 0);

        System.out.println(" ");
        // Add xp gained from this encounter.
        hero.levelUp(xpGain);

        // Return success.
        return true;
    }

    public boolean riddleMe(Scanner scanner, Hero hero, Monster nilbog){
        // Player guidance.
        System.out.println("A small, grey creature approaches. It beckons you closer, and when you approach, whispers a riddle...");
        System.out.println(((Nilbog) nilbog).getRiddle());

        boolean correct;

        // Get player input loop.
        do{
            // Get input.
            String input = scanner.nextLine();

            if(input.contains("help")){
                // Print guidance for players.
                System.out.println(hero.getName() + " has been approached by a Nilbog!");
                System.out.println("These small creatures can kill humans easily, but won't attack if you 'flee' or answer the riddle correctly within three guesses.");
            }else if(input.contains("flee")){
                // Allow player to flee.
                return fleeCombat();
            }else{
                // Try answer.
                correct = ((Nilbog) nilbog).compareAnswer(input);
                // If answer is incorrect.
                if(!correct){
                    // Increment answer count.
                    ((Nilbog) nilbog).incrementAnswerCount();
                    // Check if answer count is 3.
                    if(((Nilbog) nilbog).getAnswerCount() == 3){
                        // Kill player.
                        System.out.println(hero.getName() + " answered incorrectly three times! The Nilbog pounces and kills " + hero.getName());
                        return false;
                    }
                }else{
                    // Answer is correct.
                    System.out.println(hero.getName() + " got the right answer!");
                    System.out.println(hero.getName() + " gains " + nilbog.getXpValue() + " xp!");
                    System.out.println(" ");
                    // Remove nilbog from zone.
                    this.currentZone.getEnemies().remove(0);
                    // Add xp gained from this encounter.
                    hero.levelUp(nilbog.getXpValue());
                    return true;
                }
            }
        }while(true);
    }

    public void printEnemies(ArrayList<Monster> enemies){
        System.out.println(" ");
        StringBuilder enList = new StringBuilder("| ");
        for (Monster en : enemies){
            enList.append(en.getType());
            enList.append(" | ");
        }
        // Display enemies list to player.
        System.out.println(enList);
    }

    public void printItem(Zone zone, Hero hero){
        // Check if an item is present.
        if(zone.getZItem() != null){
            // Alert player.
            System.out.println(" ");
            System.out.println(hero.getName() + " notices a " + this.currentZone.getZItem().getName() + "!");
        }
    }

    private Zone generateZone(int x, int y){
        // Initialise monster array.
        ArrayList<Monster> zoneEnemies = new ArrayList<>();

        // Get enemies based on zone placement.
        if (x <= 5 && y <= 5){
            // Select a random number of low level enemies.
            zoneEnemies = getLowLevelEnemies(3);
        }else{
            // Random chance to spawn a special zone.
            Random rand = new Random();
            int rnd = rand.nextInt(7);   // 1 in 6 chance to spawn special zone.
            if(rnd == 0){
                zoneEnemies.add(getSpecialEnemy());
            }else{
                // Select a random number of high and low tier enemies.
                zoneEnemies.addAll(getLowLevelEnemies(3));
                zoneEnemies.addAll(getHighLevelEnemies(2));
            }
        }

        // Instantiate new zone.
        Zone newZone = new Zone(x, y, zoneEnemies, "");

        // Random num gen to decide if zone will have an item.
        Random rand = new Random();
        if(rand.nextInt(4) == 0){   // 1 in 4 chance.
            newZone.setZItem(randomiseItem());
        }

        // Add new zone to list.
        this.zoneHistory.add(newZone);

        // Return new zone.
        return newZone;
    }

    private Monster getSpecialEnemy(){
        // Get random enemy.
        Random rand = new Random();

        // Var to temporarily hold current enemy to add.
        Monster en;

        // Select one random special enemy.
        int rnd = rand.nextInt(3);
        if(rnd <= 1){
            en = new GoblinChampion();
        }else {
            en = new Nilbog();
        }
        // Return enemy.
        return en;
    }

    public Item randomiseItem(){
        // Var for new item.
        Item newItem;
        // Random num gen to decide item type.
        Random rand = new Random();

        int rnd = rand.nextInt(8);
        switch(rnd){
            case 0 -> {
                // Randomise weapon selected.
                rnd = rand.nextInt(15);
                // Get new weapon.
                switch (rnd) {
                    default -> newItem = new Weapon("rusted longsword", 1, 2, 1, 9);
                    case 3,4,5 -> newItem = new Weapon("whip", 1, 2, 1, 9);
                    case 6,7,8 -> newItem = new Weapon("axe", 1, 1, 2, 9);
                    case 9,10 -> newItem = new Weapon("battle axe", 2, 2, 2, 11);
                    case 11,12 -> newItem = new Weapon("longsword", 2, 2, 2, 11);
                    case 13 -> newItem = new Weapon("chain whip", 3, 3, 3, 13);
                    case 14 -> newItem = new Weapon("magical longsword", 3, 3, 3, 13);
                }
            }
            case 1 -> {
                // Randomise armour selected.
                rnd = rand.nextInt(9);
                // Get new armour.
                switch (rnd){
                    default -> newItem = new Armour("wooden shield", 1, 1);
                    case 3,4 -> newItem = new Armour("metal shield", 2, 2);
                    case 5,6 -> newItem = new Armour("scale armour", 2, 2);
                    case 7 -> newItem = new Armour("plate armour", 3, 3);
                    case 8 -> newItem = new Armour("magic shield", 3, 3);
                }
            }
            default ->{
                // Randomise potion selected.
                rnd = rand.nextInt(5);
                // Get new potion.
                switch (rnd){
                    default -> newItem = new Potion("healing potion", 1, "heal");
                    case 2 -> newItem = new Potion("attack boost potion", 2, "atkBoost");
                    case 3 -> newItem = new Potion("defence boost potion", 2, "defBoost");
                    case 4 -> newItem = new Potion("health boost potion", 2, "hpBoost");
                }
            }
        }

        return newItem;
    }

    private ArrayList<Monster> getLowLevelEnemies(int maxNumOfEnemies){
        // Get number of enemies.
        Random rand = new Random();
        int enemyCount = rand.nextInt(maxNumOfEnemies);

        // Var to temporarily hold current enemy to add.
        Monster en;
        // Select a random number of random enemies.
        ArrayList<Monster> zoneEnemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++){
            int rnd = rand.nextInt(3);
            if(rnd <= 1){
                en = new Goblin();
            }else {
                en = new GoblinShaman();
            }
            zoneEnemies.add(en);
        }

        return zoneEnemies;
    }

    private ArrayList<Monster> getHighLevelEnemies(int maxNumOfEnemies){
        // Get number of enemies.
        Random rand = new Random();
        int enemyCount = rand.nextInt(maxNumOfEnemies);

        // Var to temporarily hold current enemy to add.
        Monster en;
        // Select a random number of random enemies.
        ArrayList<Monster> zoneEnemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++){
            en = new Hobgoblin();   // Currently removed for loop as the only high level enemy is hobs.
            zoneEnemies.add(en);
        }

        return zoneEnemies;
    }

    private boolean move(String direction){
        // Move in direction specified.
        if(direction.contains("north")){
            // Provide feedback.
            System.out.println("Going north...");
            // Check if zone exists.
            int zEx = zoneExists(this.currentZone.getXCoord(), this.currentZone.getYCoord()+1);
            // If zone exists.
            if(zEx != -1){
                this.currentZone = this.zoneHistory.get(zEx);
            }else{
                this.currentZone = generateZone(this.currentZone.getXCoord(), this.currentZone.getYCoord()+1);   // Generate new zone.
            }
        }else if(direction.contains("south")){
            // Provide feedback.
            System.out.println("Going south...");
            // Check if zone exists.
            int zEx = zoneExists(this.currentZone.getXCoord(), this.currentZone.getYCoord()-1);
            // If zone exists.
            if(zEx != -1){
                this.currentZone = this.zoneHistory.get(zEx);
            }else{
                this.currentZone = generateZone(this.currentZone.getXCoord(), this.currentZone.getYCoord()-1);   // Generate new zone.
            }
        }else if(direction.contains("east")){
            // Provide feedback.
            System.out.println("Going east...");
            // Check if zone exists.
            int zEx = zoneExists(this.currentZone.getXCoord()+1, this.currentZone.getYCoord());
            // If zone exists.
            if(zEx != -1){
                this.currentZone = this.zoneHistory.get(zEx);
            }else{
                this.currentZone = generateZone(this.currentZone.getXCoord()+1, this.currentZone.getYCoord());   // Generate new zone.
            }
        }else if(direction.contains("west")){
            // Provide feedback.
            System.out.println("Going west...");
            // Check if zone exists.
            int zEx = zoneExists(this.currentZone.getXCoord()-1, this.currentZone.getYCoord());
            // If zone exists.
            if(zEx != -1){
                this.currentZone = this.zoneHistory.get(zEx);
            }else{
                this.currentZone = generateZone(this.currentZone.getXCoord()-1, this.currentZone.getYCoord());   // Generate new zone.
            }
        }

        // Evaluate new position.
        this.compass.evaluatePos(this.currentZone.getXCoord(), this.currentZone.getYCoord(), exit.getXCoord(), exit.getYCoord());
        System.out.println(" ");

        // Return success.
        return true;
    }

    public int zoneExists(int x, int y){
        // Tracker variable.
        int tr = 0;
        // Loop through existing zones.
        for(Zone zone : this.zoneHistory){
            // Check if zone matches coordinates.
            if(zone.getXCoord() == x && zone.getYCoord() == y){
                // Return index of zone found.
                return tr;
            }
            // Increment tracker variable.
            tr++;
        }

        return -1;   // If no zone is found, return -1.
    }

    public boolean checkWin(){
        // Check if exit has been found.
        if (exit.getXCoord() == this.currentZone.getXCoord() && this.exit.getYCoord() == this.currentZone.getYCoord()){
            if (this.currentZone.getEnemies().size() == 0){
                System.out.println("Well done! You have defeated the Goblin Lord and found the exit!");
                return true;
            }
        }

        return false;
    }

    // Getters and setters.
    public Zone getCurrentZone() {return currentZone;}

}
