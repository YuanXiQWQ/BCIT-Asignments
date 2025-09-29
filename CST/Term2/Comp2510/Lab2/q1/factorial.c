#include <stdio.h>

/**
 * Get factorial of a number bigger than 0.
 *
 * @param numberToFactor the number to get factorial, cannot smaller than 0
 * @return the factorial of the number
 */
long factorial(const int numberToFactor) {
    // factorial end with 1, using long to avoid overflow because factorial result always big.
    long result = 1;
    for (int i = 1; i <= numberToFactor; i++) {
        result *= i;
    }
    return result;
}

int main() {
    int startNumber = 1;
    int endNumber = 10;

    // Form title.
    printf("| NUMBER | FACTORIAL |\n");
    // Calculate factorial from start number to end number.
    for (int i = startNumber; i <= endNumber; i++) {
        long result = factorial(i);
        printf("| %6d | %9ld |\n", i, result);
    }
    return 0;
}
