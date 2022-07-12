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
    private ArrayList<Monster> enemies;

    // Constructor.
    public GameBoard(int exitX, int exitY){
        // Initialise enemy types.
        Monster goblin = new Goblin();
        Monster goblinShaman = new GoblinShaman();
        // Initialise enemy list.
        this.enemies = new ArrayList<>();
        this.enemies.add(goblin);
        this.enemies.add(goblinShaman);

        // Initialise start and end zones.
        ArrayList<Monster> empty = new ArrayList<>();
        this.currentZone = new Zone(0,0, empty, "");
        this.exit = new Zone(exitX, exitY, empty, "");  // TODO: No enemies currently spawn in end zone.

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
                // Remove the current zone from the list of visited zones.
                this.zoneHistory.remove(this.zoneHistory.size()-1);
                // Make the current zone the last zone visited.
                this.currentZone = this.zoneHistory.get(this.zoneHistory.size()-1);
                // Provide feedback to player.
                System.out.println("You flee back the way you came.");
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
        System.out.println(" ");

        // Random number generator - dice.
        Random rand = new Random();

        // Enter combat loop.
        do{
            // Show enemies.
            StringBuilder enList = new StringBuilder("| ");
            for (Monster en : this.currentZone.getEnemies()){
                enList.append(en.getType());
                enList.append(" | ");
            }
            // Display enemies list to player.
            System.out.println(enList);

            // Track target.
            boolean validTarget = true;
            int iEnemy = 0;

            // Player selects enemy to attack.
            do{
                System.out.println("Which enemy do you want to attack?");
                String enemy = scanner.nextLine();
                try{
                    iEnemy = Integer.parseInt(enemy);
                    iEnemy -= 1;
                }catch(Exception ex){
                    System.out.println("Invalid entry - Enter a number with a corresponding enemy.");
                    validTarget = false;
                }
            }while(!validTarget);

            // Select monster.
            Monster selectedEnemy = this.currentZone.getEnemies().get(iEnemy);

            // Try to hit.
            int toHit = rand.nextInt(1, 21);
            System.out.println(hero.getName() + " attacks the " + selectedEnemy.getType() + " with their sword for " + toHit + "!");
            // Check success of attack.
            if(toHit > selectedEnemy.getAC()){
                // Calculate damage.
                int dmg = rand.nextInt(1, 9);
                System.out.println(hero.getName() + " hits the " + selectedEnemy.getType() + " for " + dmg + "!");
                // Apply damage to enemy.
                selectedEnemy.setHP(selectedEnemy.getHP()-dmg);
                // Determine if the enemy is defeated.
                if(selectedEnemy.getHP() <= 0){
                    System.out.println("The " + selectedEnemy.getType() + " is defeated!");
                    this.currentZone.getEnemies().remove(iEnemy);
                }else{
                    this.currentZone.getEnemies().add(iEnemy, selectedEnemy);
                }
            }else{
                System.out.println(hero.getName() + " misses!");
            }

            // Goblin's turn. Loop for number of goblins,

        }while(hero.getHP() > 0 && currentZone.getEnemies().size() > 0);

        return true;
    }

    private Zone generateZone(int x, int y){
        // Get number of enemies.
        Random rand = new Random();
        int enemyCount = rand.nextInt(4);

        // Select a random number of random enemies.
        ArrayList<Monster> zoneEnemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++){
            int e = rand.nextInt(this.enemies.size()-1);
            zoneEnemies.add(this.enemies.get(e));
        }

        // Return new zone.
        return new Zone(x, y, zoneEnemies, "");
    }

    private boolean move(String direction){
        // Move in direction specified.
        if(direction.contains("north")){
            System.out.println("Going north...");
            this.currentZone = generateZone(this.currentZone.getXCoord(), this.currentZone.getYCoord()+1);
        }else if(direction.contains("south")){
            System.out.println("Going south...");
            this.currentZone = generateZone(this.currentZone.getXCoord(), this.currentZone.getYCoord()-1);
        }else if(direction.contains("east")){
            System.out.println("Going east...");
            this.currentZone = generateZone(this.currentZone.getXCoord()+1, this.currentZone.getYCoord());
        }else if(direction.contains("west")){
            System.out.println("Going west...");
            this.currentZone = generateZone(this.currentZone.getXCoord()-1, this.currentZone.getYCoord());
        }
        // Add new zone to list.
        this.zoneHistory.add(this.currentZone);

        // Return success.
        return true;
    }

    public boolean checkWin(){
        // Check if exit has been found.
        if (exit.getXCoord() == this.currentZone.getXCoord() && this.exit.getYCoord() == this.currentZone.getYCoord()){
            System.out.println("You found the exit!");
            return true;
        }else {
            System.out.println("Current x:" + this.currentZone.getXCoord() + " y:" + this.currentZone.getYCoord());
            return false;
        }
    }

    // Getters and setters.
    public Zone getCurrentZone() {return currentZone;}

}
