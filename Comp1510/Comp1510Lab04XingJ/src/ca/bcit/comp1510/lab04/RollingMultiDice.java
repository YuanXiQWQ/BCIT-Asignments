package ca.bcit.comp1510.lab04;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class will use the MultiDie class to roll multiple dice.
 *
 * @author Jiarui Xing
 * @version 2024.2.8
 */

public class RollingMultiDice {
    /**
     * Drives the program.
     *
     * @param args unused
     */
    public static void main(String[] args) {

        //Initialise
        Scanner userChoiceOfSide = new Scanner(System.in);
        int numSides;

        //Interaction
        System.out.println("Enter the number of sides of the dice: ");
        //Check
        while (true) {
            try {
                numSides = userChoiceOfSide.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Enter an integer!!! Pls!!!");
                userChoiceOfSide.next();
            }
        }

        //Set the number of sides
        MultiDie die = new MultiDie(numSides);

        //Roll the die
        die.roll();

        //Output
        System.out.println("The result of die "
                + numSides
                + " you rolled is: "
                + die.getFaceValue());

        //End
        userChoiceOfSide.close();
    }
}
