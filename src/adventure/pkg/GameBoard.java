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
            if((lInput.contains("north") || lInput.contains("south") || lInput.contains("east") || lInput.contains("west")) && !fight){
                // Denote valid response.
                valid = true;
                // Trigger move action.
                success = move(lInput);
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

    private boolean combatEncounter(Scanner scanner, Hero hero){
        // Guide player.
        System.out.println(" ");
        System.out.println("Choose an enemy to attack. - Enter a number for the corresponding enemy.");

        // Random number generator - dice.
        Random rand = new Random();

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
                return false;
            }

        }while(hero.getHP() > 0 && currentZone.getEnemies().size() > 0);

        System.out.println(" ");

        return true;
    }

    private Zone generateZone(int x, int y){
        // Get number of enemies.
        Random rand = new Random();
        int enemyCount = rand.nextInt(3);

        // Var to temporarily hold current enemy to add.
        Monster en = null;
        // Select a random number of random enemies.
        ArrayList<Monster> zoneEnemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++){
            int rnd = rand.nextInt(2);
            if(rnd == 0){
                en = new Goblin();
            }else if(rnd == 1){
                en = new GoblinShaman();
            }
            zoneEnemies.add(en);
        }

        // Instantiate new zone.
        Zone newZone = new Zone(x, y, zoneEnemies, "");

        // Add new zone to list.
        this.zoneHistory.add(newZone);

        // Return new zone.
        return newZone;
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
