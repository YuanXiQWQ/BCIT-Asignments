package q2;

/**
 * A class that includes the word and its frequency. Provides methods to get the
 * word, get and increment its frequency, and compare objects based on
 * frequency.
 *
 * @author Xing Jiarui
 * @version 2024.3.26
 */
public class Word implements Comparable<Word> {
    /**
     * This is 1.
     */
    private static final int ONE = 1;

    /**
     * The word.
     */
    private final String word;

    /**
     * The frequency of the word.
     */
    private int frequency;

    /**
     * Constructs a Word object with the specified word. The initial frequency
     * is set to 1.
     *
     * @param word the word included in the book.
     */
    public Word(String word) {
        this.word = word;
        this.frequency = ONE;
    }

    /**
     * Returns the word.
     *
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the frequency of the word.
     *
     * @return the frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Increments the frequency of the word by 1.
     */
    public void incrementFrequency() {
        frequency++;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return word + ": " + frequency;
    }

    /**
     * Compares this object with the specified object for order.
     *
     * @param otherWord the Word object to be compared.
     *
     * @return the result of the comparison
     */
    @Override
    public int compareTo(Word otherWord) {
        return Integer.compare(otherWord.frequency, this.frequency);
    }
}
