#include <stdio.h>

// Question 1
/**
 * Check if the score is valid
 * A valid score is a non-negative integer less than or equal to 100
 *
 * @param score The score
 * @return 1 if the score is valid, 0 otherwise
 */
int isValidScore(int score){
    return score >= 0 && score <= 100;
}

// Question 2
/**
 * Detect any anomalies e.g. if any score is negative or greater than 100
 *
 * @param scores The array of scores
 * @param size  The size of the array
 * @return  1 if any anomaly is detected
 *          0 otherwise
 */
int detectAnomalies(int scores[], int size){
    for(int i = 0; i < size; i ++){
        if(! isValidScore(scores[i])){
            return 1;
        }
    }
    return 0;
}

// Question 3
/**
 * Function that updates the score by adding delta to it.
 * It will ensure the score remains within the valid range (0 to 100)
 *
 * @param score The score to update
 * @param delta The delta to add
 */
void updateScore(int *score, int delta){
    *score = *score > 100 ? 100 : *score;
    *score = *score < 0 ? 0 : *score;
    *score += delta;
    *score = *score > 100 ? 100 : *score;
    *score = *score < 0 ? 0 : *score;
}

// Question 4
/**
 * Calculates and returns the average score of the employees
 *
 * Note: I think it is better to use float to record the average, also you mentioned that
 * we can feel free to change the code, so I just changed the return type.
 *
 * @param scores The array of scores
 * @param size   The size of the array
 * @return      The average score of the employees
 */
float calculateAverage(const int scores[], int size){
    float sum = 0;
    for(int i = 0; i < size; i ++){
        sum += (float) scores[i];
    }
    return sum / (float) size;
}

// Question 5
/**
 * Find the maximum score in the array
 *
 * @param scores The array of scores
 * @param size   The size of the array
 * @return      The maximum score
 */
int findMaxScore(const int scores[], int size){
    int max = scores[0];
    for(int i = 1; i < size; i ++){
        max = scores[i] > max ? scores[i] : max;
    }
    return max;
}

// Question 6
/**
 * Find the minimum score in the array
 *
 * @param scores The array of scores
 * @param size   The size of the array
 * @return      The minimum score
 */
int findMinScore(const int scores[], int size){
    int min = scores[0];
    for(int i = 1; i < size; i ++){
        min = scores[i] < min ? scores[i] : min;
    }
    return min;
}

// Question 7
/**
 * Reset all scores to zero
 *
 * @param scores The array of scores
 * @param size   The size of the array
 */
void resetScores(int scores[], int size){
    for(int i = 0; i < size; i ++){
        scores[i] = 0;
    }
}

// Question 8
/**
 * Count the number of valid scores in the array.
 *
 * @param scores The array of scores
 * @param size   The size of the array
 * @return      The number of valid scores
 */
int countValidScores(int scores[], int size){
    int count = 0;
    for(int i = 0; i < size; i ++){
        // Because the function returns 1 if the score is valid, so funny:)
        count += isValidScore(scores[i]);
    }
    return count;
}

int main(){
    int scores[] = {100, - 100, 662607015, 85, 90, 78, 88, 92};
    int size = sizeof(scores) / sizeof(scores[0]);

    // Tests
    // Question 1
    printf("Question 1:\n");
    for(int i = 0; i < size; i ++){
        printf("%d %s\n", scores[i],
               isValidScore(scores[i])
               ? "is valid"
               : "is invalid");
    }

    // Question 2
    printf("\nQuestion 2:\n");
    printf("%s\n", detectAnomalies(scores, size)
                   ? "Anomalies detected"
                   : "No anomalies detected");

    // Question 3
    printf("\nQuestion 3:\n");
    for(int i = 0; i < size; i ++){
        updateScore(&scores[i], 10);
        printf("%d\n", scores[i]);
    }

    // Question 4
    printf("\nQuestion 4:\n");
    printf("Average score: %.2f\n", calculateAverage(scores, size));

    // Question 5
    printf("\nQuestion 5:\n");
    printf("Max score: %d\n", findMaxScore(scores, size));

    // Question 6
    printf("\nQuestion 6:\n");
    printf("Min score: %d\n", findMinScore(scores, size));

    // Question 8
    printf("\nQuestion 8:\n");
    printf("Number of valid scores: %d\n", countValidScores(scores, size));

    // Question 7
    printf("\nQuestion 7:\n");
    resetScores(scores, size);
    for(int i = 0; i < size; i ++){
        printf("%d\n", scores[i]);
    }

    return 0;
}