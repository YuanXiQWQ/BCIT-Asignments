#include <stdio.h>
#include <stdlib.h>

/**
 * Comparison function for sorting the array in ascending order
 *
 * @param a the first number
 * @param b the second number
 * @return  0 if a == b, -1 if a < b, 1 if a > b
 */
int compare(const void *a, const void *b) {
    int int_a = *(int *) a;
    int int_b = *(int *) b;
    if(int_a < int_b) return -1;
    else if(int_a > int_b) return 1;
    else return 0;
}

/**
 * Returns the sum of the two largest unique numbers in the array
 *
 * @param numbers the array of numbers
 * @param size    the size of the array
 * @return        the sum of the two largest unique numbers
 */
int sum_two_largest_unique(int *numbers, int size) {
    if(size == 0) {
        return 0;
    }

    // Sort the array
    qsort(numbers, size, sizeof(int), compare);

    // Find the unique numbers
    int *unique_numbers = (int *) malloc(size * sizeof(int));
    int unique_count = 0;

    unique_numbers[unique_count++] = numbers[0];
    for(int i = 1; i < size; i++) {
        if(numbers[i] != numbers[i - 1]) {
            unique_numbers[unique_count++] = numbers[i];
        }
    }

    int sum = 0;
    if(unique_count >= 2) {
        // Return the sum of the two largest unique numbers
        sum = unique_numbers[unique_count - 1] + unique_numbers[unique_count - 2];
    } else {
        // Return the sum of all the unique numbers
        for(int i = 0; i < unique_count; i++) {
            sum += unique_numbers[i];
        }
    }

    free(unique_numbers);
    return sum;
}

/**
 * Main function for testing the program
 *
 * @return 0 on success
 */
int main() {
    // Test 1
    int numbers1[] = {1, 2, 3, 4, 5};
    int size1 = sizeof(numbers1) / sizeof(numbers1[0]);
    int result1 = sum_two_largest_unique(numbers1, size1);
    printf("Test 1 result: %d\n", result1);

    // Test 2
    int numbers2[] = {5, 5, 5, 5};
    int size2 = sizeof(numbers2) / sizeof(numbers2[0]);
    int result2 = sum_two_largest_unique(numbers2, size2);
    printf("Test 2 result: %d\n", result2);

    // Test 3
    int numbers3[] = {};
    int size3 = sizeof(numbers3) / sizeof(numbers3[0]);
    int result3 = sum_two_largest_unique(numbers3, size3);
    printf("Test 3 result: %d\n", result3);

    // Test 4
    int numbers4[] = {-1, -2, -3, -4, -5};
    int size4 = sizeof(numbers4) / sizeof(numbers4[0]);
    int result4 = sum_two_largest_unique(numbers4, size4);
    printf("Test 4 result: %d\n", result4);

    return 0;
}
