package q2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class that counts the words in the book, tracking each word's frequency.
 * Utilizes a List to manage Word objects, offering methods to parse a book
 * and report on the most frequently occurring words.
 *
 * @author Xing Jiarui
 * @version 2024.3.26
 */
public class WordCounter {
  /**
   * A list of Word objects representing the words found in the book.
   */
  private final List<Word> words = new ArrayList<>();

  /**
   * Parses the given book file, updating word frequencies as they are encountered.
   * Each unique word's frequency is tracked using a Word object.
   *
   * @param fileName the name of the book file to parse.
   *
   * @throws FileNotFoundException if the file cannot be found.
   */
  public void parseBook(String fileName) throws FileNotFoundException {
    Scanner fileScanner = new Scanner(new File(fileName));
    while (fileScanner.hasNext()) {
      String currentWord = fileScanner.next().toLowerCase().replaceAll("[^a-z0-9']", "");
      Word foundWord = findWord(currentWord);
      if (foundWord != null) {
        foundWord.incrementFrequency();
      } else {
        words.add(new Word(currentWord));
      }
    }
    fileScanner.close();
    System.out.println("Total number of unique words: " + words.size());
  }

  /**
   * Searches the list of words for a given word.
   *
   * @param word the word to search for.
   *
   * @return the Word object if found, or null if not found.
   */
  private Word findWord(String word) {
    for (Word w : words) {
      if (w.getWord().equals(word)) {
        return w;
      }
    }
    return null;
  }

  /**
   * Prints the top n most frequent words.
   *
   * @param n the number of top words to print.
   */
  public void printTopWords(int n) {
    words.sort(null);
    for (int i = 0; i < Math.min(n, words.size()); i++) {
      System.out.println(words.get(i));
    }
  }

  public static void main(String[] args) {
    WordCounter wordCounter = new WordCounter();
    try {
      wordCounter.parseBook("src/q2/The King James Version of the Bible.txt");
      wordCounter.printTopWords(10);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }

    System.out.println("Question two was called and ran successfully.");
  }
}
