//
// Created by AyeshaA on 2024-12-01.
// Edit by Jiarui Xing on 2024-12-12
//
#include <stdio.h>

// Function Prototypes
int isValidScore(int score);

int detectCheating(const int scores[], int size);

void updateScore(int *score, int delta);

int calculateAverage(const int scores[], int size);

int findMaxScore(const int scores[], int size);

int findMinScore(const int scores[], int size);

void resetScores(int scores[], int size);

int countValidScores(int scores[], int size);

int main(){
    // Q1 Test
    // Check if a score is valid
    int score = 300;
    printf("Score: %d is %s\n", score, isValidScore(score) ? "valid" : "invalid");
    score = 800;
    printf("Score: %d is %s\n", score, isValidScore(score) ? "valid" : "invalid");
    score = - 200;
    printf("Score: %d is %s\n", score, isValidScore(score) ? "valid" : "invalid");

    // Q2 Test
    // Detect cheating in scores array
    int scores[] = {- 100, 200, 300, 400, 450};
    int size = sizeof(scores) / sizeof(scores[0]);
    printf("Cheating detected: %s\n", detectCheating(scores, size) ? "true" : "false");
    scores[0] = 100;
    printf("Cheating detected: %s\n", detectCheating(scores, size) ? "true" : "false");

    // Q3 Test
    // Update the score
    score = 100;
    updateScore(&score, 200);
    printf("Updated score: %d\n", score);

    // Q4 Test
    // Calculate average score
    int average = calculateAverage(scores, size);
    printf("Average score: %d\n", average);

    // Q5 and Q6 Test
    // Find the maximum score
    int maxScore = findMaxScore(scores, size);
    printf("Max score: %d\n", maxScore);

    //  Find the minimum score
    int minScore = findMinScore(scores, size);
    printf("Min score: %d\n", minScore);

    // Q7 Test
    // Reset all scores to zero
    resetScores(scores, size);
    printf("Scores after reset: ");
    for(int i = 0; i < size; i ++){
        printf("%d ", scores[i]);
    }
    printf("\n");

    // Q8 Test
    // Count valid scores
    int validCount = countValidScores(scores, size);
    printf("Number of valid scores: %d\n", validCount);

    return 0;
}

// Question 1: Write a function to check if a score is valid
/**
 * Checks if a given score is valid.
 * A valid score is a non-negative integer less than or equal to 500
 *
 * @param score The score to check
 * @return 1 if the score is valid
 *         0 otherwise
 */
int isValidScore(int score){
    return score >= 0 && score <= 500;
}

// Question 2: Write a function to detect cheating in scores array
/**
 * Returns 1 if cheating is detected
 * (e.g., if any score is negative or greater than 500)
 * otherwise returns 0
 *
 * @param scores The array of scores
 * @param size The size of the scores array
 * @return 1 if cheating is detected
 *         0 otherwise
 */
int detectCheating(const int scores[], int size){
    for(int i = 0; i < size; i ++){
        if(! isValidScore(scores[i])){
            return 1;
        }
    }
    return 0;
}

// Question 3: Write a function to update the score
/**
 * Updates the score by adding delta to it
 * It will insure the score remains within the valid range(0 to 500)
 *
 * @param score The score to update
 * @param delta The amount to add to the score
 */
void updateScore(int *score, int delta){
    *score += delta;
    // Check valid after adding
    if(*score < 0){
        *score = 0;
    }
    if(*score > 500){
        *score = 500;
    }
}

// Question 4: Write a function to calculate average score
/**
 * Calculates and returns the average score of the players
 *
 * @param scores The array of scores
 * @param size The size of the scores array
 * @return The average score
 */
int calculateAverage(const int scores[], int size){
    int sum = 0;
    for(int i = 0; i < size; i ++){
        sum += scores[i];
    }
    return sum / size;
}

// Question 5: Complete the missing line of code to find the maximum score in the array.
/**
 * Finds the maximum score in the array
 *
 * @param scores The array of scores
 * @param size The size of the scores array
 * @return The max score in the array
 */
int findMaxScore(const int scores[], int size){
    int max = scores[0];
    for(int i = 1; i < size; i ++){
        max = max > scores[i] ? max : scores[i];
    }
    return max;
}

// Question 6: Complete the missing line of code to find the minimum score in the array. Look at function findMinScore.
/**
 * Finds the minimum score in the array
 *
 * @param scores The array of scores
 * @param size The size of the scores array
 * @return The min score in the array
 */
int findMinScore(const int scores[], int size){
    int min = scores[0];
    for(int i = 1; i < size; i ++){
        min = min < scores[i] ? min : scores[i];
    }
    return min;
}

// Question 7: Complete the missing line of code to reset all scores to zero.
/**
 * Resets all scores to zero
 *
 * @param scores The array of scores
 * @param size The size of the scores array
 */
void resetScores(int scores[], int size){
    for(int i = 0; i < size; i ++){
        scores[i] = 0;
    }
}

// Question 8: Complete the missing line of code to count the number of valid scores in the array.
/**
 * Counts the number of valid scores in the array
 *
 * @param scores The array of scores
 * @param size The size of the scores array
 * @return The number of valid scores in the array
 */
int countValidScores(int scores[], int size){
    int count = 0;
    for(int i = 0; i < size; i ++){
        if(isValidScore(scores[i])){
            count ++;
        }
    }
    return count;
}