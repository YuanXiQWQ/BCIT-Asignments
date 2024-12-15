#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct{
    void **items;
    int top;
    int capacity;
} Stack;

Stack *createStack(int capacity);

int isFull(Stack *stack);

int isEmpty(Stack *stack);

void push(Stack *stack, void *item, size_t itemSize);

void pop(Stack *stack, void *item, size_t itemSize);

void freeStack(Stack *stack);

void printIntStack(Stack *stack);

int main(){
    Stack *stack = createStack(10);
    for(int i = 0; i < 10; i ++){
        int item = i;
        push(stack, &item, sizeof(int));
    }
    pop(stack, NULL, sizeof(int));
    printIntStack(stack);
    freeStack(stack);
    return 0;
}

// Function definitions
/**
 * Creates a stack with the given capacity
 *
 * @param capacity The capacity of the stack
 * @return A pointer to the created stack
 */
Stack *createStack(int capacity){
    Stack *stack = malloc(sizeof(Stack));
    stack->items = malloc(sizeof(void *) * capacity);
    stack->top = 0;
    stack->capacity = capacity;
    return stack;
}

/**
 * Checks if the stack is full.
 *
 * @param stack A pointer to the stack
 * @return 1 if the stack is full, 0 otherwise
 */
int isFull(Stack *stack){
    for(int i = 0; i < stack->capacity; i ++){
        if(stack->items[i] == NULL){
            return 0;
        }
    }
    return 1;
}

/**
 * Checks if the stack is empty.
 *
 * @param stack A pointer to the stack
 * @return 1 if the stack is empty, 0 otherwise
 */
int isEmpty(Stack *stack){
    for(int i = 0; i < stack->capacity; i ++){
        if(stack->items[i] != NULL){
            return 0;
        }
    }
    return 1;
}

/**
 * Pushes an item onto the stack.
 *
 * @param stack A pointer to the stack
 * @param item The item to be pushed
 * @param itemSize The size of the item
 */
void push(Stack *stack, void *item, size_t itemSize){
    if(isFull(stack)){
        printf("Stack is full\n");
        return;
    }
    stack->items[stack->top] = item;
    stack->top ++;
}

/**
 * Pops an item from the stack
 *
 * @param stack
 * @param item
 * @param itemSize
 */
void pop(Stack *stack, void *item, size_t itemSize){
    if(isEmpty(stack)){
        printf("Stack is empty\n");
        return;
    }
    stack->top --;
    stack->items[stack->top] = NULL;
}

/**
 * Frees the allocated memory of the stack.
 *
 * @param stack A pointer to the stack
 */
void freeStack(Stack *stack){
    free(stack->items);
    free(stack);
}

/**
 * Prints the items in the stack.
 *
 * @param stack A pointer to the stack
 */
void printIntStack(Stack *stack){
    for(int i = 0; i < stack->top; i ++){
        printf("%d\n", *(int *) stack->items[i]);
    }
}