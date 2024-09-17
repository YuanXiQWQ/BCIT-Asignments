package ca.bcit.comp1510.lab03;

import java.util.Scanner;

/**
 * VaseConvert
 *
 * @author BCIT
 * @author JiaruiXing
 */
public class BaseConvert {
    /**
     * Drives the program.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        int base10number; // the number in base 10
        int base; // the new base
        int maximumNumber; // the maximum number that will fit
// in 4 digits in the new base
        // digit in the 1's (base^0) place
        Scanner scan = new Scanner(System.in);
        System.out.println("Base Conversion Program");
        System.out.print("Please enter a base (2-9): ");
        base = scan.nextInt();
        maximumNumber = (int) Math.pow(base, 4) - 1;
// Assign the user's input to the base variable
// Calculate the correct value to store in maxNumber
        System.out.println("The max base 10 number to convert for that base is " + maximumNumber);
        System.out.print("Please enter a base 10 number to convert: ");
        base10number = scan.nextInt();
// Assign the user's input to the base10number variable
// Do the conversion
// First compute place0 -- the units place. Remember this comes
// from the first division so it is the remainder when the
// base 10 number is divided by the base (HINT %).
// Then compute the quotient (integer division / will do it!) -
// You can either store the result back in base10Num or declare a
// new variable for the quotient
// Now compute place1 -- this is the remainder when the quotient
// from the preceding step is divided by the base.
// Then compute the new quotient
// Repeat the idea from above to compute place2 and the next quotient
// Repeat again to compute place3
// Print the result
        StringBuilder baseBNumber = new StringBuilder(""); // the number in the new base
        int quotient = base10number;
        for (int i = 0; i < 4; i++) {
            int remainder = quotient % base;
            quotient = quotient / base;
            baseBNumber.insert(0, remainder);
        }
        System.out.println("The base " + base + " equivalent is: " + baseBNumber);
        scan.close();
    }
}