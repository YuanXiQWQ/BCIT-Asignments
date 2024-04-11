package q3;

import java.util.Scanner;

/**
 * A class that tests the MIXChar class.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class TestMIXChar {

    /**
     * The main method that tests the MIXChar class.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a string to encode with MIXChar characters:");

        String input = scanner.nextLine();
        if (!isAllMIXChar(input)) {
            System.out.println("Error: Input contains invalid MIX characters.");
        } else {

            MIXChar[] mixChars = MIXChar.toMIXChar(input);
            Message message = new Message(mixChars);

            System.out.println("Encoded long values: " + message.toLongs());

            String decodedString = message.toString();
            System.out.println("Decoded string: " + decodedString);

            if (input.equals(decodedString)) {
                System.out.println(
                        "Success: The encoded and then decoded string matches the " +
                                "original input.");
            } else {
                System.out.println(
                        "Failure: The decoded string does not match the original input.");
            }
        }
    }

    /**
     * Checks if the entire input string consists of valid MIX characters.
     *
     * @param input the input string to check
     * @return true if all characters are valid MIX characters, false otherwise
     */
    private static boolean isAllMIXChar(String input) {
        for (char c : input.toCharArray()) {
            if (MIXChar.isMIXChar(c)) {
                return false;
            }
        }
        return true;
    }
}
