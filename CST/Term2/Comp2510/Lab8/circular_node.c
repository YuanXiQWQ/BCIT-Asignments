#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "circular_node.h"

// Function to create a new node with given signal and duration
struct Node *createNode(const char *signal, int duration){
    struct Node *newNode = (struct Node *) malloc(sizeof(struct Node));
    if(newNode == NULL){
        printf("Memory allocation failed\n");
        exit(1);
    }
    strcpy(newNode->signal, signal);
    newNode->duration = duration;
    newNode->next = newNode; // Point to itself initially
    return newNode;
}

// Function to insert a node at the end of the circular linked list
void insertAtEnd(struct Node **head, const char *signal, int duration){
    struct Node *newNode = createNode(signal, duration); // Create a new node
    if(*head == NULL){ // If the list is empty
        *head = newNode; // Set the new node as the head
        return;
    }
    struct Node *temp = *head; // Temporary pointer to traverse the list
    while(temp->next != *head){ // Traverse to the last node
        temp = temp->next;
    }
    temp->next = newNode; // Link the last node to the new node
    newNode->next = *head; // Link the new node back to the head to maintain circularity
}

// Function to delete a node with a given signal color from the circular linked list
void deleteNode(struct Node **head, const char *signal){
    if(*head == NULL){ return; } // If the list is empty

    struct Node *temp = *head, *prev = NULL;

    // If the head node itself holds the signal to be deleted, and it's the only node
    if(strcmp(temp->signal, signal) == 0 && temp->next == *head){
        *head = NULL;
        free(temp);
        return;
    }

    // If the head node itself holds the signal to be deleted and there are more nodes
    if(strcmp(temp->signal, signal) == 0){
        while(temp->next != *head){ // Find the last node to update its next pointer
            temp = temp->next;
        }
        temp->next = (*head)->next; // Link the last node to the second node
        free(*head); // Free the old head
        *head = temp->next; // Update the head pointer
        return;
    }

    // Search for the node to be deleted, keeping track of the previous node
    while(temp->next != *head && strcmp(temp->signal, signal) != 0){
        prev = temp;
        temp = temp->next;
    }

    // If the signal was not found in the list
    if(strcmp(temp->signal, signal) != 0){ return; }

    // Unlink the node from the list and free its memory
    prev->next = temp->next;
    free(temp);
}

// Function to display the circular linked list
void displayList(struct Node *head){
    if(head == NULL){
        printf("The list is empty.\n\n");
        return;
    }

    struct Node *temp = head;
    printf("Circular linked list (traffic signals):\n");
    do{
        printf("%s (%d seconds) -> ", temp->signal,
               temp->duration); // Print signal and duration
        temp = temp->next;
    } while(temp != head);
    printf("%s (head)\n\n", head->signal); // Indicate the circular nature
}

/**
 * Updates the duration of a signal in the circular linked list based on its signal name.
 *
 * @param head the head of the circular linked list
 * @param signal the name of the signal
 * @param newDuration the new duration
 */
void updateDuration(struct Node *head, const char *signal, int newDuration){
    // Check if the list is empty
    if(head == NULL){
        return;
    }

    // Search for the signal in the list
    struct Node *temp = head;
    do{
        // If the signal is found, update its duration
        if(strcmp(temp->signal, signal) == 0){
            temp->duration = newDuration;
            printf("Updated duration of '%s' to %d seconds.\n\n", signal, newDuration);
            return;
        }

        // Move to the next node
        temp = temp->next;
    } while(temp != head);
    printf("Signal '%s' not found. Duration not updated.\n\n", signal);
}
