package q2;

/**
 * A class that includes the word and its frequency.
 * Provides methods to get the word, get and increment its frequency, and compare objects based on frequency.
 *
 * @author Xing Jiarui
 * @version 2024.3.26
 */
public class Word implements Comparable<Word> {
  /**
   * The word.
   */
  private final String word;

  /**
   * The frequency of the word.
   */
  private int frequency;

  /**
   * Constructs a Word object with the specified word. The initial frequency is set to 1.
   *
   * @param word the word included in the book.
   */
  public Word(String word) {
    this.word = word;
    this.frequency = 1;
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
   * Returns a string representation of the Word object, including the word and its frequency.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return word + ": " + frequency;
  }

  /**
   * Compares this Word object with another based on their frequencies, for order. Returns a
   * negative integer, zero, or a positive integer as this object is less than, equal to, or
   * greater than the specified object.
   *
   * @param otherWord the Word object to be compared.
   *
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   * or greater than the specified object.
   */
  @Override
  public int compareTo(Word otherWord) {
    return Integer.compare(otherWord.frequency, this.frequency);
  }
}
