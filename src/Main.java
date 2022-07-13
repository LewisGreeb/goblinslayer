import adventure.pkg.*;
import adventure.pkg.monsters.*;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main (String[] args){
        // Prep scanner for user input.
        Scanner scanner = new Scanner(System.in);

        // Control game loop.
        String cntue = "";

        // Game loop.
        do{
            // Activate game.
            startGame(scanner);

            // Check if user wants to end the program.
            System.out.println("Please enter 'end' to close the program. Otherwise, press enter to continue.");
            cntue = scanner.nextLine();
        } while (!cntue.equalsIgnoreCase("End"));
    }

    public static void startGame(Scanner scanner){
        // Intro message.
        System.out.println("~~~~~ WELCOME TO GOBLIN SLAYER ~~~~~");
        System.out.println(" ");

        // Get random exit location, at least five zones away from centre in any direction.
        int exitX = randomCoordinate();
        int exitY = randomCoordinate();

        // Instantiate game board.
        GameBoard board = new GameBoard(exitX, exitY);

        // Get player name.
        System.out.println("Please enter a name for your hero:");
        String name = scanner.nextLine();
        // Instantiate player character.
        Hero hero = new Hero(name);

        // Game tracker variables.
        boolean success;
        boolean win = false;

        // Gameplay variables.
        boolean fight = false;

        // Game intro.
        System.out.println(" ");
        System.out.println(hero.getName() + " is deep in a dark cave, hunting goblins.");
        System.out.println("Navigate with 'north', 'south', 'east', and 'west' to find your way out of the caves.");
        System.out.println("The navigation stone will guide your way, growing warmer as you approach the exit.");
        System.out.println(" ");

        // Game loop
        do{
            // Get and process player input.
            success = board.getInput(scanner, fight, hero);

            // Check win condition.
            win = board.checkWin();

            // Check if current zone has enemies.
            Zone current = board.getCurrentZone();
            if(current.getEnemies().size() > 0 && success && !win){   // Success var stops message printing when hero is dead.
                System.out.println(" ");
                // Player feedback.
                System.out.println("Some enemies approach!");
                // Show enemies.
                StringBuilder enList = new StringBuilder("| ");
                for (Monster en : current.getEnemies()){
                    enList.append(en.getType());
                    enList.append(" | ");
                }
                System.out.println(" ");
                // Display enemies list to player.
                System.out.println(enList);
                // Set fight flag to true.
                fight = true;
            }else{
                // Set fight flag to false.
                fight = false;
            }

        }while (success && !win);

    }

    public static int randomCoordinate(){
        Random rand = new Random();
        // Set up variable.
        int coord = 0;
        // Flip coin.
        int coin = rand.nextInt(2);
        // Generate exit x coordinate.
        if(coin == 0){
            coord = rand.nextInt(-10,-4);
        }else{
            coord = rand.nextInt(5, 11);
        }

        return coord;
    }

}
