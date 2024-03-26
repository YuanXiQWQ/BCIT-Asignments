package q2;

/**
 * A class that include the word and its frequency
 *
 * @author Xing Jiarui
 * @version 2024.3.26
 */
public class Word implements Comparable<Word> {
  /**
   * Final instance variable for the word
   */
  private final String word;
  /**
   * Instance variable for the frequency
   */
  private int frequency;

  /**
   * Constructor for the class
   *
   * @param word the word included in the book
   */
  public Word(String word) {
    this.word = word;
    this.frequency = 1;
  }

  /**
   * Getter for the word
   */
  public String getWord() {
    return word;
  }

  /**
   * Getter for the frequency
   */
  public int getFrequency() {
    return frequency;
  }

  public void incrementFrequency() {
    frequency++;
  }

  @Override
  public String toString() {
    return word + ": " + frequency;
  }

  @Override
  public int compareTo(Word other) {
    return Integer.compare(other.frequency, this.frequency);
  }
}