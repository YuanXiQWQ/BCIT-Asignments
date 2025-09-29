#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MAX_ATTEMPTS 10

// Function declarations
int playGame(int *totalGames, int *wins, int *losses);

void displayGuesses(const int *guesses, int attempts);

/**
 * Main function
 *
 * @return 0 on success
 */
int main() {
    // Seed the random number generator
    srand(time(0));
    char playAgain;
    int totalGames = 0, wins = 0, losses = 0;

    do {
        if(playGame(&totalGames, &wins, &losses) == 1) {
            printf("You won this round!\n");
        } else {
            printf("You lost this round!\n");
        }

        printf("Do you want to play again? (y/n): ");
        scanf(" %c", &playAgain);
    } while(playAgain == 'y' || playAgain == 'Y');

    printf("Games played: %d, Wins: %d, Losses: %d\n", totalGames, wins, losses);
    return 0;
}

/**
 * Function to play the game and keep track of wins and losses
 *
 * @param totalGames Pointer to the total number of games played
 * @param wins Pointer to the number of wins
 * @param losses Pointer to the number of losses
 * @return 1 if the player wins, 0 if the player loses
 */
int playGame(int *totalGames, int *wins, int *losses) {
    int number = rand() % 100 + 1;
    int guess;
    int attempts = 0;
    int guesses[MAX_ATTEMPTS];
    int *ptr = guesses;

    printf("Guess the number between 1 and 100:\n");

    while(attempts < MAX_ATTEMPTS) {
        printf("Attempt %d: ", attempts + 1);

        // Input validation for numbers
        if(scanf("%d", &guess) != 1) {
            printf("Invalid input. Please enter a valid number between 1 and 100.\n");
            // Clear the input buffer
            while(getchar() != '\n');
            continue;
        }

        if(guess < 1 || guess > 100) {
            printf("Please enter a number between 1 and 100.\n");
            continue;
        }

        *(ptr + attempts) = guess;
        attempts++;

        if(guess < number) {
            printf("Too low!\n");
        } else if(guess > number) {
            printf("Too high!\n");
        } else {
            printf("Congratulations! You guessed the number in %d attempts.\n", attempts);
            displayGuesses(guesses, attempts);
            // Increment wins and total games
            (*wins)++;
            (*totalGames)++;
            return 1;
        }
    }

    printf("Sorry, you've used all %d attempts. The number was %d.\n", MAX_ATTEMPTS, number);
    displayGuesses(guesses, attempts);
    (*losses)++;
    (*totalGames)++;
    return 0;
}

/**
 * Function to display all guesses
 *
 * @param guesses the array of guesses
 * @param attempts the number of attempts
 */
void displayGuesses(const int *guesses, int attempts) {
    printf("Your guesses were: ");
    for(int i = 0; i < attempts; i++) {
        printf("%d ", *(guesses + i));
    }
    printf("\n");
}
