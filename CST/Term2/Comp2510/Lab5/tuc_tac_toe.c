#include <stdio.h>
#include <stdlib.h>
#include "tic_tac_toe.h"

// Initialize a new game with the given number of players
Game *init_game(int num_players)
{
    if(num_players < 2 || num_players > MAX_PLAYERS)
    {
        printf("Number of players must be between 2 and %d.\n", MAX_PLAYERS);
        return NULL;
    }

    Game *game = (Game *) malloc(sizeof(Game));
    if(game == NULL)
    {
        printf("Memory allocation failed!\n");
        exit(1);
    }

    // Initialize the board to empty spaces
    for(int i = 0; i < SIZE; i ++)
    {
        for(int j = 0; j < SIZE; j ++)
        {
            game -> board[i][j] = ' ';
        }
    }

    game -> num_players = num_players;
    game -> current_player_index = 0;

    // Assign unique symbols to each player
    char symbols[MAX_PLAYERS] = {'X', 'O', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    for(int i = 0; i < num_players; i ++)
    {
        game -> players[i] = symbols[i];
        game -> move_counts[i] = 0;  // Initialize move counts to zero
    }

    return game;
}

// Display the current state of the game board
void display_board(const Game *game)
{
    printf("\n");
    for(int i = 0; i < SIZE; i ++)
    {
        for(int j = 0; j < SIZE; j ++)
        {
            printf(" %c ", game -> board[i][j]);
            if(j < SIZE - 1)
            { printf("|"); }
        }
        printf("\n");
        if(i < SIZE - 1)
        { printf("---|---|---\n"); }
    }
    printf("\n");
}

// Attempt to make a move for the current player at (row, col)
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

    // Switch to the next player
    game -> current_player_index =
            (game -> current_player_index + 1) % game -> num_players;

    return 1;
}

// Check if there's a winner. Returns the winning symbol or ' ' if no winner
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

    // Check columns
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
        // Top-left to bottom-right diagonal
        if(game -> board[0][0] == player_symbol &&
           game -> board[1][1] == player_symbol &&
           game -> board[2][2] == player_symbol)
        {
            return player_symbol;
        }
        // Top-right to bottom-left diagonal
        if(game -> board[0][2] == player_symbol &&
           game -> board[1][1] == player_symbol &&
           game -> board[2][0] == player_symbol)
        {
            return player_symbol;
        }
    }

    // No winner
    return ' ';
}

// Free the memory allocated for the game
void free_game(Game *game)
{
    if(game != NULL)
    {
        free(game);
    }
}
