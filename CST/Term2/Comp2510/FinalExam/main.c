#include "array_utils.h"
#include <stdio.h>
#include <malloc.h>

#define MAX_INT_SIZE 100
#define MAX_STRING_SIZE 1024

int main(){
    int intArray[MAX_INT_SIZE];
    char *stringArray[MAX_STRING_SIZE];
    int size;

    // Example usage of integer array functions
    readIntArrayFromFile("input_int.txt", intArray, &size);
    reverseArray(intArray, 0, size - 1);
    writeIntArrayToFile("output_int.txt", intArray, size);

    // Example usage of string array functions
    readStringArrayFromFile("input_str.txt", stringArray, &size);
    reverseStringArray(stringArray, 0, size - 1);
    writeStringArrayToFile("output_str.txt", stringArray, size);


    int *p = (int *) malloc(5 * sizeof(int));
    if(p == NULL){
        printf("Memory allocation failed.");
        return 1;
    }

    free(p);

    return 0;
}