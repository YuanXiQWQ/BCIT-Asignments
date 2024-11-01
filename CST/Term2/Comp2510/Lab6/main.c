#include "student.h"
#include <stdio.h>
#include <stdlib.h>

// The main function that reads the input file and writes the output file
int main(int argc, char *argv[]){
    // Executable name
    const char *exe = argv[0];

    // Check for correct number of arguments
    if(argc != 4){
        fprintf(stderr, USAGE, exe);
        return 1;
    }

    // User-specified input file
    const char *argIF = argv[1];
    // User-specified output file
    const char *argOF = argv[2];
    // User option
    const char *argO = argv[3];

    // Convert option to integer
    int option = atoi(argO);
    if(option < 1 || option > 3){
        FILE *outputFile = fopen(argOF, "w");
        if(outputFile == NULL){
            fprintf(stderr, ERROR_OPENING_FILE, argOF);
            return 1;
        }
        fprintf(outputFile, ERROR_INVALID_OPTION);
        fclose(outputFile);
        return 1;
    }

    // Open the output file for writing errors during reading input
    FILE *outputFile = fopen(argOF, "w");
    if(outputFile == NULL){
        fprintf(stderr, ERROR_OPENING_FILE, argOF);
        return 1;
    }

    // Parse the input file and build the linked list of students
    StudentNode *head = parseInputFile(argIF, outputFile);
    if(head == NULL){
        fclose(outputFile);
        return 1;
    }

    fclose(outputFile);

    // Write the output file based on the selected option
    writeOutputFile(argOF, head, option);

    // Free the linked list
    freeList(head);

    return 0;
}
