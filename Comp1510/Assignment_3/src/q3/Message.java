package q3;

/**
 * A class that represents a message.
 *
 * @author Xing Jiarui
 * @version 1.0
 */
public class Message {
    /**
     * The packed long values representing the message.
     */
    private final long[] data;

    /**
     * The number of MIXChar characters in the message.
     */
    private final int count;

    /**
     * Constructs a message from an array of MIXChar characters. The characters are packed
     * into longs, with up to 11 characters per long.
     *
     * @param m the array of MIXChar characters to pack into the message
     */
    public Message(MIXChar[] m) {
        this.count = m.length;
        int dataSize = (count + 10) / 11;
        this.data = new long[dataSize];

        for (int i = 0; i < count; i++) {
            int dataIndex = i / 11;
            int charPosition = i % 11;
            data[dataIndex] |= ((long) m[i].ordinal()) << (6 * (10 - charPosition));
        }
    }

    /**
     * Constructs a message from a String. Each character of the string is converted to a
     * MIXChar and packed into the message.
     *
     * @param s the string to pack into the message
     */
    public Message(String s) {
        this(MIXChar.toMIXChar(s));
    }

    /**
     * Returns a string representation of the message, converting the packed long values
     * back into MIXChar characters.
     *
     * @return a string representation of the message
     */
    @Override
    public String toString() {
        MIXChar[] mixChars = new MIXChar[count];
        for (int i = 0; i < count; i++) {
            int dataIndex = i / 11;
            int charPosition = i % 11;
            int ordinal = (int) (data[dataIndex] >> (6 * (10 - charPosition)) & 0x3F);
            mixChars[i] = new MIXChar(MIXChar.CHAR_MAP[ordinal]);
        }
        return MIXChar.toString(mixChars);
    }

    /**
     * Returns a string representation of the message's long array, formatted as unsigned
     * integers separated by spaces.
     *
     * @return the formatted string of unsigned long values
     */
    public String toLongs() {
        StringBuilder builder = new StringBuilder();
        for (long l : data) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(Long.toUnsignedString(l));
        }
        return builder.toString();
    }
}
