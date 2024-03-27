package q3;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a list of prime numbers up to a given number.
 * It uses the Sieve of Eratosthenes algorithm to calculate the prime numbers.
 *
 * @author Xing Jiarui
 * @version 2024.3.26
 */
public class Primes {
  /**
   * A list of booleans that records whether a number is prime or not.
   */
  private final ArrayList<Boolean> primes;

  /**
   * Construct a list of booleans that records whether a number is prime or not.
   *
   * @param n the upper bound
   */
  public Primes(int n) {
    primes = new ArrayList<>(n + 1);
    calculatePrimes(n);
  }

  /**
   * Calculates all prime numbers up to a given number.
   *
   * @param n the number up to which to calculate prime numbers
   */
  private void calculatePrimes(int n) {
    //1. Make a list of numbers: 0, 1, 2, 3, ..., n
    for (int i = 0; i <= n; i++) {
      // 2-3. 0 and 1 false
      primes.add(i > 1);
    }

    // 7. Continue until you have reached sqrt n
    for (int factor = 2; factor * factor <= n; factor++) {
      // 4-6. if factor is true rn, false its multiples
      if (primes.get(factor)) {
        for (int i = factor * factor; i <= n; i += factor) {
          primes.set(i, false);
        }
      }
    }
  }

  /**
   * Prints all prime numbers stored in the `primes` list.
   */
  public void printPrimes() {
    System.out.println("Prime numbers:");
    for (int i = 2; i < primes.size(); i++) {
      // if true, std::sout << i;
      if (primes.get(i)) {
        System.out.print(i + " ");
      }
    }
    System.out.println();
  }

  /**
   * Counts the number of prime numbers in the given array.
   *
   * @return the count of prime numbers in the array
   */
  public int countPrimes() {
    int count = 0;
    for (boolean prime : primes) {
      if (prime) {
        count++;
      }
    }
    return count;
  }

  /**
   * Checks if the given number is prime.
   *
   * @param x the number to be checked
   *
   * @return true if the number is prime, false otherwise
   */
  public boolean isPrime(int x) {
    return primes.get(x);
  }

  /**
   * The main function that takes user input for an upper bound, calculates the prime numbers up to that bound,
   * and prints the count of prime numbers along with the prime numbers themselves.
   *
   * @param args the array of command-line arguments
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter an upper bound:");
    int n = scanner.nextInt();
    Primes primes = new Primes(n);

    System.out.println("There are " + primes.countPrimes() + " primes:");
    primes.printPrimes();
  }
}