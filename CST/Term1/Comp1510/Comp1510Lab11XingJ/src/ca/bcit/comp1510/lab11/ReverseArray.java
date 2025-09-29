package ca.bcit.comp1510.lab11;

import java.util.Scanner;

/**
 * ReverseArray class to reverse the elements of an array provided by the user input.
 *
 * @author Xing Jiarui
 * @version 2024.4.11
 */
public class ReverseArray {
    /**
     * Swaps two elements in an array.
     *
     * @param array the array where elements need to be swapped
     * @param i     the index of the first element
     * @param j     the index of the second element
     */
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Reverses the elements of the array.
     *
     * @param array the array to be reversed
     */
    private static void reverse(int[] array) {
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            swap(array, start, end);
            start++;
            end--;
        }
    }

    /**
     * Main method.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        // Initialise.
        Scanner scanner = new Scanner(System.in);

        // Interactive.
        System.out.print("Enter the number of elements: ");
        int count = scanner.nextInt();
        int[] array = new int[count];

        System.out.println("Enter " + count + " integers:");
        for (int i = 0; i < count; i++) {
            array[i] = scanner.nextInt();
        }

        System.out.println("Original array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();

        reverse(array);

        System.out.println("Reversed array:");
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();

        // End.
        scanner.close();
    }
}
