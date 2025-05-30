#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#define ARRAY_SIZE 10 // Adjusted to match the problem constraints

/**
 * Function to rotate an array base on the input angle n
 *
 * @param arr the input array to be rotated
 * @param n   the rotation angle
 * @param rows the number of rows in the array
 * @param cols the number of columns in the array
 */
void rotateArray(int arr[10][10], int n, int rows, int cols) {
    int rotatedArr[10][10] = {0};
    // theta(norm) = {theta(mod) when theta(mod) >= 0} or {theta(mod) + 360 when theta(mod) < 0}
    switch (n % 360 > 0 ? n % 360 : n % 360 + 360) {
        case 0:
        case 360:
            // Nothing happens
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rotatedArr[i][j] = arr[i][j];
                }
            }
            break;
        case 90:
            // i to j, j to rows - 1 - i
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rotatedArr[j][rows - 1 - i] = arr[i][j];
                }
            }
            break;
        case 180:
            // i to rows - 1 - i, j to rows - 1 - j
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rotatedArr[rows - 1 - i][cols - 1 - j] = arr[i][j];
                }
            }
            break;
        case 270:
            // i to cols - 1 - j, j to i
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    rotatedArr[cols - 1 - j][i] = arr[i][j];
                }
            }
            break;
        default:
            // Invalid angle, output the 0 array (because I cannot add printf function or return anything)
            break;
    }
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            arr[i][j] = rotatedArr[i][j];
        }
    }
}

/*********************************/
// Do NOT touch anything below this line
int main(int argc, char **argv) {
    int i, j, n;
    int arr[10][10] = {0}; // Initialize the array to zero

    // Sample 5x5 input array
    int sample[5][5] = {
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3}
    };

    // Copy sample input to arr
    for (i = 0; i < 5; i++) {
        for (j = 0; j < 5; j++) {
            arr[i][j] = sample[i][j];
        }
    }

    // Set the rotation angle
    n = 540; // Change this to test different angles

    printf("========INPUT=======\n");
    for (i = 0; i < 5; i++) {
        for (j = 0; j < 5; j++) {
            printf("%d", arr[i][j]);
        }
        printf("\n");
    }

    rotateArray(arr, n, 5, 5);

    printf("=======OUTPUT=======\n");
    for (i = 0; i < 5; i++) {
        for (j = 0; j < 5; j++) {
            printf("%d", arr[i][j]);
        }
        printf("\n");
    }

    return 0;
}
