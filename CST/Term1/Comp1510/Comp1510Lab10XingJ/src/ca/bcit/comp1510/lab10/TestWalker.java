package ca.bcit.comp1510.lab10;

import java.util.Scanner;

/**
 * Test class for RandomWalker.
 *
 * @author Xing Jiarui
 * @version 2024.4.5
 */
public class TestWalker {
    /**
     * Main method.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            RandomWalker walker1 = new RandomWalker(10, 5);
            simulateWalkerSteps(walker1);

            int maxStepsUser = getIntFromUser(input, "Enter maximum number of steps for the " +
                    "second walker:");
            int boundaryUser = getIntFromUser(input, "Enter boundary size for the second walker:");
            RandomWalker walker2 = new RandomWalker(maxStepsUser, boundaryUser);
            simulateWalkerSteps(walker2);

            RandomWalker testWalker = new RandomWalker(200, 10);
            System.out.println("\nInitial state of testWalker: " + testWalker);
            testWalker.walk();
            System.out.println("Final state of testWalker: " + testWalker);
            System.out.println("Maximum distance from origin reached during the walk: "
                    + testWalker.getMaxMDistance());
        }
    }

    /**
     * Simulates the steps of a walker.
     *
     * @param walker the walker
     */
    private static void simulateWalkerSteps(RandomWalker walker) {
        System.out.println("Initial state of walker: " + walker);
        for (int i = 0; i < 5; i++) {
            walker.takeStep();
            System.out.println("After step " + (i + 1) + ": " + walker);
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
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // clear the invalid input
            System.out.println(prompt);
        }
        return scanner.nextInt();
    }
}
