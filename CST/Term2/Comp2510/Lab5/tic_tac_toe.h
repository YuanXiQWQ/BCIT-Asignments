#ifndef TIC_TAC_TOE_H
#define TIC_TAC_TOE_H

#define SIZE 3
#define MAX_PLAYERS 8

typedef struct
{
    char board[SIZE][SIZE];
    char players[MAX_PLAYERS];
    int num_players;
    int current_player_index;
    int move_counts[MAX_PLAYERS];
} Game;

// Function prototypes
Game *init_game(int num_players);

void display_board(const Game *game);

int make_move(Game *game, int row, int col);

char check_winner(const Game *game);

void free_game(Game *game);

#endif
