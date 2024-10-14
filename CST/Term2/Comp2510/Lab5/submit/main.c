#include <stdio.h>
#include <stdlib.h>
#include "tic_tac_toe.h"

/**
 * Main function for the game.
 *
 * @return 0 on success
 *         1 on failure
 */
int main()
{
    char play_again = 'y';
    int draws = 0;
    int num_players;
    int player_wins[MAX_PLAYERS] = {0};

    printf("Welcome to Multi-player Tic-Tac-Toe!\n");

    printf("Enter the number of players (2-%d): ", MAX_PLAYERS);
    scanf("%d", &num_players);
    while(num_players < 2 || num_players > MAX_PLAYERS)
    {
        printf("Invalid number of players. Please enter a number between 2 and %d: ",
               MAX_PLAYERS);
        scanf("%d", &num_players);
    }

    // Main game loop
    while(play_again == 'y' || play_again == 'Y')
    {
        // Init new game
        Game *game = init_game(num_players);
        if(game == NULL)
        {
            printf("Failed to initialize the game.\n");
            return 1;
        }

        int row, col;
        char winner = ' ';
        int move_count = 0;

        printf("\nGame Start!\n");
        printf("Enter your move as row and column numbers (0, 1, 2).\n");

        // Game play loop
        while(winner == ' ' && move_count < SIZE * SIZE)
        {
            display_board(game);

            // Get current player's symbol and number
            char current_player_symbol = game -> players[game -> current_player_index];
            int current_player_number = game -> current_player_index + 1;

            // Prompt current player for their move
            printf("Player %d (%c), enter your move (row and column): ",
                   current_player_number, current_player_symbol);
            if(scanf("%d %d", &row, &col) != 2)
            {
                printf("Invalid input. Please enter two integers separated by space.\n");

                // Clear input buffer
                while(getchar() != '\n')
                {}
                continue;
            }

            // Attempt to make the move; if invalid, prompt again
            if(! make_move(game, row, col))
            {
                continue;
            }

            move_count ++;

            // Check for a winner
            winner = check_winner(game);
        }

        display_board(game);

        if(winner != ' ')
        {
            // Find the index of the winning player
            int winner_index = - 1;
            for(int i = 0; i < num_players; i ++)
            {
                if(game -> players[i] == winner)
                {
                    winner_index = i;
                    break;
                }
            }
            if(winner_index != - 1)
            {
                printf("Player %d (%c) wins!\n", winner_index + 1, winner);
                player_wins[winner_index] ++;
            } else
            {
                printf("Winner not found in player list.\n");
            }
        } else
        {
            printf("It's a draw!\n");
            draws ++;
        }

        // Display move counts for each player
        printf("\nMove Counts:\n");
        for(int i = 0; i < num_players; i ++)
        {
            printf("Player %d (%c) made %d moves.\n", i + 1, game -> players[i],
                   game -> move_counts[i]);
        }

        // Display current game statistics
        printf("\nCurrent Stats:\n");
        for(int i = 0; i < num_players; i ++)
        {
            printf("Player %d (%c) Wins: %d\n", i + 1, game -> players[i],
                   player_wins[i]);
        }
        printf("Draws: %d\n", draws);

        // Ask if the players want to play again
        printf("\nDo you want to play again? (y/n): ");
        scanf(" %c", &play_again);

        if(play_again == 'y' || play_again == 'Y')
        {
            printf("Same number of players? (y/n): ");
            char same_players;
            scanf(" %c", &same_players);
            if(same_players == 'n' || same_players == 'N')
            {
                // Prompt and read the new number of players
                printf("Enter the number of players (2-%d): ", MAX_PLAYERS);
                scanf("%d", &num_players);
                while(num_players < 2 || num_players > MAX_PLAYERS)
                {
                    printf("Invalid number of players. Please enter a number between 2 and %d: ",
                           MAX_PLAYERS);
                    scanf("%d", &num_players);
                }
            }
        }

        free_game(game);
    }

    printf("Thanks for playing!\n");
    return 0;
}
