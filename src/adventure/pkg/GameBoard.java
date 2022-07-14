package adventure.pkg;

import adventure.pkg.monsters.*;

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
        // Initialise start and end zones.
        ArrayList<Monster> empty = new ArrayList<>();
        this.currentZone = new Zone(0,0, empty, "This is the beginning.");
        this.exit = new Zone(exitX, exitY, empty, "");  // TODO: No enemies currently spawn in end zone.

        // Initialise compass.
        this.compass = new Compass(0,0,exitX,exitY);

        // Initialise zone history with current zone.
        this.zoneHistory = new ArrayList<>();
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
            }else if(lInput.contains("rest") || lInput.contains("camp")){
                // Denote valid response.
                valid = true;
                // Trigger rest action.
                success = camp(scanner, hero);
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
                // Make the current zone the last zone visited before current.
                this.currentZone = this.zoneHistory.get(this.zoneHistory.size()-2);
                // Provide feedback to player.
                System.out.println("You flee back the way you came.");
                System.out.println(" ");
                // Set success flag.
                success = true;
            }

            // If no valid action was detected.
            if(!valid && fight){
                // Display error message.
                System.out.println("You're in battle, you must 'fight' or 'flee'.");
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
        System.out.println("To check the health and defence of your character, type 'check'");
        System.out.println("To heal after battle, type 'rest' or 'camp' - though you might be attacked as you sleep!");
        System.out.println("Explore and fight goblins to grow stronger - find your way to the exit to win!");
        System.out.println(" ");
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
                    // Try rest again?
                    String input = scanner.nextLine();
                    String lInput = input.toLowerCase();
                    // Validate input.
                    while(!lInput.contains("y") && !lInput.contains("n")){
                        System.out.println("Please enter 'yes' or 'no'.");
                        input = scanner.nextLine();
                        lInput = input.toLowerCase();
                    }
                    // Set flag from user response.
                    rest = lInput.contains("y");
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

    private boolean combatEncounter(Scanner scanner, Hero hero){
        // Guide player.
        System.out.println(" ");
        System.out.println("Choose an enemy to attack. - Enter a number for the corresponding enemy.");

        // Random number generator - dice.
        Random rand = new Random();

        // Get xp gained for this combat.
        int xpGain = 0;
        for (Monster en : this.currentZone.getEnemies()){
            xpGain += en.getXpValue();
        }

        // Enter combat loop.
        do{
            // Show enemies.
            System.out.println(" ");
            StringBuilder enList = new StringBuilder("| ");
            for (Monster en : this.currentZone.getEnemies()){
                enList.append(en.getType());
                enList.append(" | ");
            }
            // Display enemies list to player.
            System.out.println(enList);

            // Track target.
            boolean validTarget;
            int iEnemy = 0;

            // Player selects enemy to attack.
            do{
                System.out.println("Which enemy do you want to attack?");
                String enemy = scanner.nextLine();
                try{
                    iEnemy = Integer.parseInt(enemy);
                    iEnemy -= 1;
                    validTarget = true;
                }catch(Exception ex){
                    // Catch if not a number.
                    System.out.println("Invalid entry - Enter a number with a corresponding enemy.");
                    validTarget = false;
                }
                if (iEnemy >= this.currentZone.getEnemies().size()){
                    // Catch number with no corresponding enemy.
                    System.out.println("Invalid entry - Enter a number with a corresponding enemy.");
                    validTarget = false;
                }
            }while(!validTarget);

            // Selected monster.
            Monster selectedEnemy = this.currentZone.getEnemies().get(iEnemy);

            // Hero attacks selected enemy.
            selectedEnemy.setHP(selectedEnemy.getHP()-hero.attack(selectedEnemy));

            // Determine if the enemy was defeated.
            if(selectedEnemy.getHP() <= 0){
                System.out.println("The " + selectedEnemy.getType() + " is defeated!");
                this.currentZone.getEnemies().remove(iEnemy);
            }else{
                this.currentZone.getEnemies().set(iEnemy, selectedEnemy);
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

    private Zone generateZone(int x, int y){
        // Initialise monster array.
        ArrayList<Monster> zoneEnemies;

        // Get enemies based on zone placement.
        if (x <= 5 && y <= 5){
            // Select a random number of low level enemies.
            zoneEnemies = getLowLevelEnemies(3);
        }else{
            // Select a random number of high and low tier enemies.
            zoneEnemies = new ArrayList<>(getLowLevelEnemies(3));
            zoneEnemies.addAll(getHighLevelEnemies(2));
        }

        // Instantiate new zone.
        Zone newZone = new Zone(x, y, zoneEnemies, "");

        // Add new zone to list.
        this.zoneHistory.add(newZone);

        // Return new zone.
        return newZone;
    }

    /*   SETUP INITIAL ITEMS FOR HERO AND ITEMS FOR GOBLINS FIRST
    public Item randomiseItem(){
        // Var for new item.
        Item newItem;
        // Random num gen to decide item type.
        Random rand = new Random();
        //
    }*/

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
            System.out.println("You found the exit!");
            return true;
        }else {
            //System.out.println("Current x:" + this.currentZone.getXCoord() + " y:" + this.currentZone.getYCoord());
            return false;
        }
    }

    // Getters and setters.
    public Zone getCurrentZone() {return currentZone;}

}
