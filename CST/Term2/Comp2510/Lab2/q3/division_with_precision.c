#include <stdio.h>

int main() {
    int firstNum, SecondNum;
    double result;

    // Get user input, try again when divisor is zero.
    do {
        printf("please input first integer: ");
        scanf("%d", &firstNum);

        printf("please input second integer: ");
        scanf("%d", &SecondNum);

        // Check if divisor is zero.
        if (SecondNum == 0) {
            // Print error message and try again.
            printf("divisor number cannot be zero, try again.\n");
        } else {
            // Calculate result and print with 3 decimal places.
            result = (double) firstNum / SecondNum;
            printf("result: %.3f\n", result);
            break;
        }
    } while (1);

    return 0;
}
