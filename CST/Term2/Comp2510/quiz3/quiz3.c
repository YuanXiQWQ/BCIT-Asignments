#include "library.h"

#include <stdio.h>
#include <malloc.h>

typedef struct node {
    int data;
    struct node *link;
} Node;

void addNode(Node **pNode, int i);
void freeList(Node *head);

/**
 * Function that receives a pointer to the first node (head) of a linked list as a
 * parameter, find and print the duplicate integers in the linked list,  assume that a
 * given integer occurs at most twice in the linked list.
 *
 * @param head pointer to the first node of the linked list
 */
void printDuplicates(Node *head){
    Node *i, *j;
    // Treat over every node of the list
    for (i = head; i != NULL; i = i->link) {
        // Start from the next node of the current node, and then find repeated integers in the remaining nodes
        for (j = i->link; j != NULL; j = j->link) {
            // If a repeated integer is found
            if (i->data == j->data) {
                // Print out
                printf("%d\n", i->data);
                // Move to the next node
                break;
            }
        }
    }
}

int main(){
    // Test the function
    Node *head = NULL;
    addNode(&head, 1);
    addNode(&head, 7);
    addNode(&head, 3);
    addNode(&head, 3);
    addNode(&head, 5);
    addNode(&head, 5);
    addNode(&head, 7);
    addNode(&head, 1);
    addNode(&head, 4);
    addNode(&head, 4);

    printDuplicates(head);

    freeList(head);
    return 0;
}

void addNode(Node **pNode, int i){
    Node *newNode = malloc(sizeof(Node));
    newNode->data = i;
    newNode->link = *pNode;
    *pNode = newNode;
}

/**
 * Function to free all nodes in the linked list to prevent memory leak
 * @param head pointer to the first node of the linked list
 */
void freeList(Node *head) {
    Node *temp;
    while (head != NULL) {
        temp = head;        // Save the current node
        head = head->link;  // Move to the next node
        free(temp);         // Free the current node
    }
}