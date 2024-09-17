package q3;

/**
 * A class that represents a MIX character.
 *
 * @author Xing Jiarui
 */
public class MIXChar {

    /**
     * The mapping from characters to MIX characters.
     */
    public static final char[] CHAR_MAP =
            {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'Δ', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'Σ', 'Π', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
                    'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',', '(',
                    ')', '+', '-', '*', '/', '=', '$', '<', '>', '@', ';', ':', '\''};
    private final char character;

    /**
     * Constructs a new MIX character.
     *
     * @param c the character to represent
     */
    public MIXChar(char c) {
        if (isMIXChar(c)) {
            throw new IllegalArgumentException("Invalid MIX character: " + c);
        }
        this.character = c;
    }

    /**
     * Checks if the given character is a valid MIX character.
     *
     * @param c the character to check
     * @return true if the character is a valid MIX character
     */
    public static boolean isMIXChar(char c) {
        for (char mapChar : CHAR_MAP) {
            if (mapChar == c) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the character represented by this MIX character.
     *
     * @return the character
     */
    public char toChar() {
        return this.character;
    }

    /**
     * Returns the ordinal of this MIX character.
     *
     * @return the ordinal
     */
    public int ordinal() {
        for (int i = 0; i < CHAR_MAP.length; i++) {
            if (CHAR_MAP[i] == this.character) {
                return i;
            }
        }
        throw new IllegalStateException("Invalid MIXChar state");
    }

    /**
     * Returns the string representation of this MIX character.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return Character.toString(this.character);
    }

    /**
     * Returns the string representation of an array of MIX characters.
     *
     * @param array the array of MIX characters
     * @return the string representation
     */
    public static String toString(MIXChar[] array) {
        StringBuilder builder = new StringBuilder();
        for (MIXChar mixChar : array) {
            builder.append(mixChar.toChar());
        }
        return builder.toString();
    }

    /**
     * Converts a string to an array of MIX characters.
     *
     * @param s the string
     * @return the array of MIX characters
     */
    public static MIXChar[] toMIXChar(String s) {
        MIXChar[] mixChars = new MIXChar[s.length()];
        for (int i = 0; i < s.length(); i++) {
            mixChars[i] = new MIXChar(s.charAt(i));
        }
        return mixChars;
    }
}
