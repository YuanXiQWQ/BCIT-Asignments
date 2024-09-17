package q1;

import java.util.Scanner;

/**
 * This program is to find change for your payment.
 *
 * @author Jiarui Xing
 * @version 2024.2.4
 */
public class Change {

    /**
     * This is the entry point that gets called to run the program.
     *
     * @param args unused
     */

    public static void main(String[] args) {

        //Initialisation
        Scanner input = new Scanner(System.in);
        int centAmount;
        // Define various denominations of money
        final int[] centValues = {1000, 500, 200, 100, 25, 10, 5, 1};
        final String[] names = {"ten dollar bills",
                                "five dollar bills",
                                "two dollars", "one dollar",
                                "toonies", "loonies",
                                "quarters", "dimes",
                                "nickels", "pennies"};
        //Thanks for ant, I cannot use number "100" directly
        final int oneHundred = 100;


        //Interaction
        do {
            System.out.print("Enter a monetary amount: $");
            //dollar * 100 = cent
            centAmount = (int) (input.nextDouble() * oneHundred);

            //debugs
            if (centAmount < 0) {
                //Re-enter when a negative number is input
                System.out.println("## You should enter a positive value ##");
            } else if (centAmount == 0) {
                System.out.println("You don't need to give change.");
                System.out.println(
                        "Question one was called and ran successfully.");
                return;
            }
        } while (centAmount < 0);

        //Output
        for (int i = 0; i < centValues.length; i++) {
            int num = centAmount / centValues[i];
            centAmount %= centValues[i];
            System.out.println(num + " " + names[i]);
        }

        //End
        input.close();
        System.out.println("Question one was called and ran successfully.");
    }
}
