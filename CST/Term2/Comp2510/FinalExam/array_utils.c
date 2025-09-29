#include "array_utils.h"
#include <stdio.h>
#include <string.h>

#define MAX_LINE_LENGTH 1024

/**
 * Function to reverse an array of integers
 * Use pointers to manipulate the array elements.
 *
 * @param arr The array to reverse
 * @param start The index of the first element
 * @param end The index of the last element
 */
void reverseArray(int *arr, int start, int end){
    int size = end - start + 1;
    int *p, temp, mid;

    // Get the middle element of the array
    mid = size / 2;

    // Get the first and last element
    int *i = arr;
    int *j = arr + size - 1;

    // p points to the middle element
    p = arr + mid;

    // Swap the elements
    for(; i < p; i ++, j --){
        temp = *i;
        *i = *j;
        *j = temp;
    }
}

/**
 * Function to reverse an array of strings
 * Use pointers to manipulate the array elements.
 *
 * @param arr The array to reverse
 * @param start The index of the first element
 * @param end The index of the last element
 */
void reverseStringArray(char *arr[], int start, int end){
    int size = end - start + 1;
    char *temp;
    for(int i = 0; i < size / 2; i ++){
        temp = arr[start + i];
        arr[start + i] = arr[end - i];
        arr[end - i] = temp;
    }
}

/**
 * Function to reads integers from a file into an array.
 * (I think the description of this function in exam is different from your template code)
 * (So I just follow with the exam description)
 *
 * @param filename The file to read
 * @param arr The array to store the integers
 * @param size The size of the array
 */
void readIntArrayFromFile(const char *filename, int *arr, int *size){
    // Open file
    FILE *file = fopen(filename, "r");
    if(file == NULL){
        printf("Error opening file\n");
        return;
    }

    // Read and record into array
    int i = 0;
    while(! feof(file)){
        // Find function usage from note
        fscanf(file, "%d", &arr[i]);
        i ++;
    }
    *size = i;
    fclose(file);
}

/**
 * Function to write the array of integers to a file.
 *
 * @param filename The file to write in
 * @param arr The array to write
 * @param size The size of the array
 */
void writeIntArrayToFile(const char *filename, int *arr, int size){
    // Open file
    FILE *file = fopen(filename, "w");

    // Write
    for(int i = 0; i < size; i ++){
        fprintf(file, "%d\n", arr[i]);
    }
    fclose(file);
}

/**
 * Function to reads strings from a file into an array
 *
 * @param filename The file to read
 * @param arr The array to store the strings
 * @param size The size of the array
 */
void readStringArrayFromFile(const char *filename, char *arr[], int *size){
    // Open file
    FILE *file = fopen(filename, "r");
    if(file == NULL){
        printf("Error opening file\n");
        return;
    }

    // Read and record into array
    char line[MAX_LINE_LENGTH];
    int i = 0;
    while(fgets(line, sizeof(line), file) != NULL){
        arr[i] = strdup(line);
        i ++;
    }
    *size = i;
    fclose(file);
}

/**
 * Function to writes the array of strings to a file.
 *
 * @param filename The file to write in
 * @param arr The array to write
 * @param size The size of the array
 */
void writeStringArrayToFile(const char *filename, char *arr[], int size){
    // Open file
    FILE *file = fopen(filename, "w");

    // Write
    for(int i = 0; i < size; i ++){
        fprintf(file, "%s\n", arr[i]);
    }
    fclose(file);
}