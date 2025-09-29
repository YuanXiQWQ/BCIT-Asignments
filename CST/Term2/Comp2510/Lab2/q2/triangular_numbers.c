
#include <stdio.h>

/**
 * Get the triangular number of a number.
 *
 * @param number the number to get triangular number
 * @return the triangular number of the number
 */
int getTriangularNumber(int number) {
    return number * (number + 1) / 2;
}

int main() {
    int startNumber = 5;
    int endNumber = 50;
    int stepNumber = 5;

    // Print table header
    printf("| NUMBER | TRIANGULAR NUMBER |\n");
    // Calculate and print triangular numbers from startNumber to endNumber with stepNumber increment
    for (int i = startNumber; i <= endNumber; i += stepNumber) {
        int result = getTriangularNumber(i);
        printf("| %6d | %17d |\n", i, result);
    }
    return 0;
}
