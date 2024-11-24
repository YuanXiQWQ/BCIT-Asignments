#ifndef CIRCULAR_NODE_H
#define CIRCULAR_NODE_H

struct Node{
    char signal[10];
    int duration;
    struct Node *next;
};

// Function prototypes
struct Node *createNode(const char *signal, int duration);

void insertAtEnd(struct Node **head, const char *signal, int duration);

void deleteNode(struct Node **head, const char *signal);

void displayList(struct Node *head);

void updateDuration(struct Node *head, const char *signal, int newDuration);

#endif
