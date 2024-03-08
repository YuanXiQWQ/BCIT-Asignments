package ca.bcit.comp1510.lab07;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Version 1 (Buggy).
 * <p>
 * This program asks the user to enter a number "n" (bigger than 2).
 * It then prints out the first "n" numbers of the Fibonacci Sequence.
 * Each number is the sum of the two previous numbers.
 * <p>
 * Example: The output for n=11 should look exactly like this:
 * <p>
 * 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ...
 * <p>
 * Fix all compile-time and run-time errors.
 *
 * @author Carly Orr
 * @version 1
 */
public class FibonacciBuggy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
            do {
                System.out.println("Enter a number bigger than 2: ");
                while(true) {
                    try {
                        n = scanner.nextInt();
                        break;
                    } catch (Exception ignored) {
                        System.out.println("Please enter a number: ");
                        scanner.nextLine();
                    }
                }
            } while (n <= 2);
        printList(getFiboList(n));
    }

    private static List<Integer> getFiboList(int n) {
        List<Integer> f = new ArrayList<>(n);
        f.add(0);
        f.add(1);
        int i = 2;
        while (i < n) {
            f.add(f.get(i - 2) + f.get(i - 1));
            i++;
        }
        return f;
    }

    private static void printList(List<Integer> fiboList) {
        int i = 0;
        while (i <= fiboList.size()-1) {
            System.out.print(fiboList.get(i) + ", ");
            i++;
        }
        System.out.println("...");
    }
}