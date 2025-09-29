#include <stdio.h>
#include <stdlib.h>
#include "tic_tac_toe.h"

/**
 * Initialise the game.
 *
 * @param num_players The number of players
 * @return            A pointer to the initialised game
 *                    NULL if the game could not be initialised
 */
Game *init_game(int num_players)
{
    // Check if the number of players is within the allowed range
    if(num_players < 2 || num_players > MAX_PLAYERS)
    {
        printf("Number of players must be between 2 and %d.\n", MAX_PLAYERS);
        return NULL;
    }

    // Allocate memory for the Game structure
    Game *game = (Game *) malloc(sizeof(Game));
    if(game == NULL)
    {
        printf("Memory allocation failed!\n");
        exit(1);
    }

    // Initialise the game board with empty spaces
    for(int i = 0; i < SIZE; i ++)
    {
        for(int j = 0; j < SIZE; j ++)
        {
            game -> board[i][j] = ' ';
        }
    }

    game -> num_players = num_players;
    game -> current_player_index = 0;

    char symbols[MAX_PLAYERS] = {'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F'};
    for(int i = 0; i < num_players; i ++)
    {
        game -> players[i] = symbols[i];
        game -> move_counts[i] = 0;
    }

    return game;
}

/**
 * Display the game board.
 *
 * @param game A pointer to the game
 */
void display_board(const Game *game)
{
    printf("\n");
    for(int i = 0; i < SIZE; i ++)
    {
        for(int j = 0; j < SIZE; j ++)
        {
            printf(" %c ", game -> board[i][j]);
            if(j < SIZE - 1)
            {
                printf("|");
            }
        }
        printf("\n");
        if(i < SIZE - 1)
        {
            printf("---|---|---\n");
        }
    }
    printf("\n");
}

/**
 * Make a move on the game board.
 *
 * @param game A pointer to the game
 * @param row  The row of the move
 * @param col  The column of the move
 * @return     1 if the move was successful
 *             0 if the move was invalid
 */
int make_move(Game *game, int row, int col)
{
    if(row < 0 || row >= SIZE || col < 0 || col >= SIZE)
    {
        printf("Move out of bounds. Please enter values between 0 and %d.\n", SIZE - 1);
        return 0;
    }
    if(game -> board[row][col] != ' ')
    {
        printf("Cell already occupied. Choose another cell.\n");
        return 0;
    }

    char current_player = game -> players[game -> current_player_index];
    game -> board[row][col] = current_player;
    game -> move_counts[game -> current_player_index] ++;

    game -> current_player_index =
            (game -> current_player_index + 1) % game -> num_players;

    return 1;
}

/**
 * Check if there is a winner.
 *
 * @param game A pointer to the game
 * @return     The symbol of the winner
 */
char check_winner(const Game *game)
{
    // Check rows
    for(int i = 0; i < SIZE; i ++)
    {
        for(int p = 0; p < game -> num_players; p ++)
        {
            char player_symbol = game -> players[p];
            if(game -> board[i][0] == player_symbol &&
               game -> board[i][1] == player_symbol &&
               game -> board[i][2] == player_symbol)
            {
                return player_symbol;
            }
        }
    }

    // Check cols
    for(int i = 0; i < SIZE; i ++)
    {
        for(int p = 0; p < game -> num_players; p ++)
        {
            char player_symbol = game -> players[p];
            if(game -> board[0][i] == player_symbol &&
               game -> board[1][i] == player_symbol &&
               game -> board[2][i] == player_symbol)
            {
                return player_symbol;
            }
        }
    }

    // Check diagonals
    for(int p = 0; p < game -> num_players; p ++)
    {
        char player_symbol = game -> players[p];

        // Main diagonal
        if(game -> board[0][0] == player_symbol &&
           game -> board[1][1] == player_symbol &&
           game -> board[2][2] == player_symbol)
        {
            return player_symbol;
        }

        // Anti-diagonal
        if(game -> board[0][2] == player_symbol &&
           game -> board[1][1] == player_symbol &&
           game -> board[2][0] == player_symbol)
        {
            return player_symbol;
        }
    }

    // No winner found
    return ' ';
}

/**
 * Free the memory used by the game.
 *
 * @param game A pointer to the game
 */
void free_game(Game *game)
{
    if(game != NULL)
    {
        free(game);
    }
}
