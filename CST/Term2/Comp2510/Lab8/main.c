#include <stdio.h>
#include "circular_node.h"

int main(){
    struct Node *head = NULL; // Initialize head to NULL

    // Insert nodes
    insertAtEnd(&head, "Red", 30);
    insertAtEnd(&head, "Green", 45);
    insertAtEnd(&head, "Yellow", 10);

    // Display the list
    displayList(head);

    // Add a new signal
    printf("Adding a new signal 'Blue' with duration 20 seconds.\n\n");
    insertAtEnd(&head, "Blue", 20);
    displayList(head);

    // Update duration of "Yellow" signal
    printf("Updating duration of 'Yellow' to 15 seconds.\n\n");
    updateDuration(head, "Yellow", 15);
    displayList(head);

    // Delete the "Green" signal node
    printf("Deleting the 'Green' signal node.\n\n");
    deleteNode(&head, "Green");

    // Display the list after deletion
    printf("Circular linked list after deletion:\n");
    displayList(head);

    return 0;
}
