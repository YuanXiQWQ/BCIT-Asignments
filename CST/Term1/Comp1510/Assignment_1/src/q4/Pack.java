package q4;

import java.util.Scanner;

/**
 * This program is for encoding and decoding.
 *
 * @author Jiarui Xing
 * @version 2024.2.4
 */
public class Pack {
    /**
     * ANT said that I cannot use numbers directly.
     *
     */
    static final int BASE = 56;

    /**
     * ANT said that I cannot use numbers directly.
     *
     */
    static final int TEXTLENGTH = 5;

    /**
     * This method encodes the text.
     *
     * @param text text to be encoded
     * @return encoded value
     */
    private static int encode(String text) {
        int encodeValue = 0;
        for (int i = 0; i < text.length(); i++) {
            int charValue = text.charAt(i) - 'A' + 1;
            encodeValue = encodeValue * BASE + charValue;
        }
        return encodeValue;
    }

    /**
     * This method decodes the text.
     *
     * @param value encoded value
     * @return decoded text
     */
    private static String decode(int value) {
        //CheckStyle said I should use StringBuilder instead this
        //"result = character + result;"
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < TEXTLENGTH; i++) {
            int charValue = value % BASE;
            char character = (char) (charValue + 'A' - 1);
            result.insert(0, character);
            value /= BASE;
        }
        return result.toString();
    }

    /**
     * This is the entry point that gets called to run the program.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        //Initialisation
        Scanner input = new Scanner(System.in);
        String text;
        boolean error = false;

        //Interaction
        System.out.println("Enter 5 characters from A to I: ");
        do {
            text = input.nextLine();

            //Check if the text is 5 characters
            if (text.length() == TEXTLENGTH) {

                //Check if the text contains letters outside A to I
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) < 'A' || text.charAt(i) > 'I') {
                        error = true;
                        System.out.println(
                                "The text contains letters outside A to I.");
                        break;
                    } else {
                        error = false;
                    }
                }
            } else {
                error = true;
                System.out.println("The text is not 5 characters long.");
            }
        } while (error);

        //Encode
        int encodeValue = encode(text);
        System.out.println("Encoded value: " + encodeValue);

        //Decode
        String decodedText = decode(encodeValue);
        System.out.println("Decoded text: " + decodedText);

        //End
        System.out.println("Question four was called and ran successfully.");
    }

}
