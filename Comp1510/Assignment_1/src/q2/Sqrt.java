package q2;

import java.util.Scanner;

/**
 * This program will perform ten iterations of
 * the Newton-Raphson square root formula on the input
 * double-precision floating-point number to
 * approximate (or obtain an exact result for perfect squares)
 * its square root value.
 *
 * @author Jiarui Xing
 * @version 2024.2.5
 */
public class Sqrt {

    /**
     * This is the entry point that gets called to run the program.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        //Initialisation
        Scanner input = new Scanner(System.in);
        //Thank you ant
        final int ten = 10;

        //Interaction
        System.out.print("Enter a number: ");
        double a = input.nextDouble();

        //Limiting condition: a >= 0
        boolean first = true;
        while (a < 0) {
            if (first) {
                System.out.println("""
                    The square of a real number is positive number or zero.
                    With a simple program like mine,
                    you don't expect for imaginary numbers.
                    Please try again, and enter a positive number\s""");
                a = input.nextDouble();
                first = false;
            } else {
                System.out.print("Please enter a positive number: ");
                a = input.nextDouble();
            }
        }

        //Calculate
        double s = (a + 1) / 2;
        //Limiting condition: a != 0
        if (a == 0) {
            s = 0;
            System.out.println(s);
        } else {
            for (int i = 0; i < ten; i++) {
                s = (s + a / s) / 2;
                System.out.println((i + 1) + ": " + s);
            }
            System.out.println("In the tenth iteration of "
                    + "Newton-Raphson square root formula,"
                    + " the approximate value (or maybe accurate value) of "
                    + a + " is " + s + ".");
        }

        //End
        input.close();
        System.out.println("Question two was called and ran successfully.");
    }
}
