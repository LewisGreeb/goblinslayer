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

        // Get random exit location.
        Random rand = new Random();
        int exitX = rand.nextInt(11);
        int exitY = rand.nextInt(11);

        // Instantiate game board.
        GameBoard board = new GameBoard(exitX, exitY);

        // Get player name.
        System.out.println("Please enter a name for your hero:");
        String name = scanner.nextLine();
        // Instantiate player character.
        Hero hero = new Hero(name);

        // Game tracker variables.
        boolean success;
        boolean win;

        // Gameplay variables.
        boolean fight = false;

        // Game loop
        do{

            // Get and process player input.
            success = board.getInput(scanner, fight, hero);

            // Check if current zone has enemies.
            Zone current = board.getCurrentZone();
            if(current.getEnemies().size() > 0){
                // Player feedback.
                System.out.println("Some enemies approach!");
                // Show enemies.
                StringBuilder enList = new StringBuilder("| ");
                for (Monster en : current.getEnemies()){
                    enList.append(en.getType());
                    enList.append(" | ");
                }
                // Display enemies list to player.
                System.out.println(enList);
                // Set fight flag to true.
                fight = true;
            }else{
                // Set fight flag to false.
                fight = false;
            }

            // Check win condition.
            win = board.checkWin();

        }while (success && !win);

    }

}
