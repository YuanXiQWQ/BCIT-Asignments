package ca.bcit.comp1510.lab10;

import java.util.Scanner;

/**
 * Class to test the RandomWalker class.
 *
 * @author Xing Jiarui
 * @version 2024.4.5
 */
public class DrunkWalker {

    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int boundary = getIntFromUser(scanner, "Enter the boundary for the walker: ");
            int maxSteps = getIntFromUser(scanner, "Enter the maximum number of steps: ");
            int numDrunks = getIntFromUser(scanner, "Enter the number of drunks (simulations) to "
                    + "run: ");

            int falls = 0;
            for (int i = 0; i < numDrunks; i++) {
                RandomWalker drunkWalker = new RandomWalker(maxSteps, boundary);
                drunkWalker.walk();
                if (!drunkWalker.inBounds()) {
                    falls++;
                }
            }

            System.out.printf("Total simulations: %d, Falls: %d\n", numDrunks, falls);
        }
    }

    /**
     * Gets an integer from the user.
     *
     * @param scanner the scanner
     * @param prompt  the prompt
     *
     * @return the integer
     */
    private static int getIntFromUser(Scanner scanner, String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next();
            System.out.println(prompt);
        }
        return scanner.nextInt();
    }
}
