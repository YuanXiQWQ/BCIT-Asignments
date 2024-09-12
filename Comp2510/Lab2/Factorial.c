#include <stdio.h>

/**
 * Get factorial of a number.
 *
 * @param numberToFactor the number to get factorial
 * @return the factorial of the number
 */
long factorial(const int numberToFactor) {
    // Check if the number is valid.
    if (numberToFactor < 0 || numberToFactor > 17) {
        // Factorial of negative number is undefined in math, and I tested long will overflow when number is bigger than 17.
        return -1;
    } else if (numberToFactor == 0) {
        // 0! is defined as 1.
        return 1;
    }

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

    // From start number to end number.
    for (int i = startNumber; i < endNumber; i++) {
        long result = factorial(i);
        if (result != -1) {
            // Number is valid.
            printf("| %6d | %9ld |\n", i, result);
        } else {
            // Number is invalid, check why it is invalid.
            char *issue = i > 17 ? "Overflow" : "N/A";
            printf("| %6d | %9s |\n", i, issue);
        }
    }
    return 0;
}
