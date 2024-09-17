package q3;

import java.util.Scanner;
import java.util.Stack;

/**
 * This is the program for reversing the words.
 *
 * @author Jiarui Xing
 * @version 2024.2.5
 */
public class Reverse {

    /**
     * This is the main method that runs the code.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        //Initialise
        Scanner input = new Scanner(System.in);
        //ant
        final int ten = 10;
        Stack<String> words = new Stack<>();

        //Interaction
        System.out.println("Enter 10 words: ");
        //Push to the stack, loop through, and add to the stack
        for (int i = 0; i < ten; i++) {
            System.out.print("Word " + (i + 1) + ": ");
            words.push(input.nextLine());
        }

        //Output
        System.out.println("\nThe words in reverse order are: ");
        //Continuously output until the stack is empty
        while (!words.isEmpty()) {
            System.out.println(words.pop());
        }


        //End
        input.close();
        System.out.println("Question three was called and ran successfully.");
    }
}
