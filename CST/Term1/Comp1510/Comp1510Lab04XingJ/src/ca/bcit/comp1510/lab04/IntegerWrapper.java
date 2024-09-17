package ca.bcit.comp1510.lab04;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is a Lab4 Assignment about a test for using Integer class.
 *
 * @author Jiarui Xing
 * @version 2024.2.8
 */
public class IntegerWrapper {

    /**
     * Drives the program.
     *
     * @param args unused
     */
    public static void main(String[] args) {

        //Initialise
        Scanner input = new Scanner(System.in);
        int inputNum;
        System.out.println(
                "Enter an integer, "
                        + "I will change it into binary, octal and hexadecimal: ");

        //Interaction
        //Check if input is valid.
        while (true) {
            try {
                inputNum = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Enter an integer!!! Pls!!!");
                input.next();//Clear the area (enter)
            }
        }

        //Output Bin, Oct and Hex
        System.out.println("Binary: " + Integer.toBinaryString(inputNum));
        System.out.println("Octal: " + Integer.toOctalString(inputNum));
        System.out.println("Hexadecimal: " + Integer.toHexString(inputNum));

        //Output the maximum and minimum possible Java integer values
        System.out.println(
                "The maximum and minimum possible Java integer values is: "
                        + "\nMax: " + Integer.MAX_VALUE
                        + "\nMin: " + Integer.MIN_VALUE
                        + "\nIf you want a number that larger or smaller than this,"
                        + "you might want to use \"long\" or add \"L\" at the end of number.");


        //Use Integer class to finish nextInt
        //Interaction
        System.out.println("Enter two integers this time: ");
        //Check again.
        while (true) {
            try {
                System.out.println("First:");
                int firstNum = input.nextInt();
                System.out.println("Second:");
                int secondNum = input.nextInt();
                System.out.println("The sum is: " + (firstNum + secondNum));
                break;
            } catch (InputMismatchException e) {
                System.out.println("Enter an integer!!! Pls!!!");
                input.next();
            }
        }

        //End
        input.close();
    }
}
