package ca.bcit.comp1510.lab06;

import java.util.Scanner;

/**
 * This class is to calculate Factorial
 *
 * @author Xing Jiarui
 * @version 2024.2.21
 */
public class Factorial {
    /**
     * The Factorial method.
     *
     * @param n the number
     * @return the factorial
     */
    long getFactorial(int n) {
        //Stop condition
        if (n < 2) {
            return 1;
            //According to the definition of factorial, 0! = 1.
        } else {
            return n * getFactorial(n - 1);
        }
    }

    /**
     * To drive the program.
     *
     * @param args not used
     */
    public static void main(String[] args) {

        // Initialise
        Scanner input = new Scanner(System.in);
        Factorial includeFactorialMethod = new Factorial();
        int inputNum;

        //Interaction
        System.out.println("Enter a non-negative integer : ");
        while (true) {
            //Check if valid.
            while (!input.hasNextInt()) {
                System.out.println("At least an integer,PLS!!!");
                //clear input
                input.next();
            }
            inputNum = input.nextInt();
            //Check if negative.
            if (inputNum < 0) {
                System.out.println("Invalid input. Please enter a non-negative integer.");
                //Check if return bigger than maxLong 9,223,372,036,854,775,807.
            } else if (inputNum > 20) {
                System.out.println("Don't do that, just don't...");
            } else {
                break;
            }
        }

        //Output
        System.out.println(inputNum + "! = " + includeFactorialMethod.getFactorial(inputNum));

        //End
        input.close();
    }
}
