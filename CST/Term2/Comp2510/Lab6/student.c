#include "student.h"
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

// Function to check if a string represents a valid float
int isValidFloat(const char *str){
    if(str == NULL || *str == '\0'){
        return 0;
    }
    // Skip leading spaces
    while(isspace(*str)){
        str ++;
    }
    if(*str == '\0'){
        return 0;
    }
    char *endptr;
    float val = strtof(str, &endptr);
    // Check if entire string was converted
    if(*endptr != '\0'){
        return 0;
    }
    return 1;
}

// Function to check if a string represents a valid integer
int isValidInt(const char *str){
    if(str == NULL || *str == '\0'){
        return 0;
    }
    // Skip leading spaces
    while(isspace(*str)){
        str ++;
    }
    if(*str == '\0'){
        return 0;
    }
    char *endptr;
    long val = strtol(str, &endptr, 10);
    // Check if entire string was converted
    if(*endptr != '\0'){
        return 0;
    }
    return 1;
}

// Function to parse the input file and build the linked list of students
StudentNode *parseInputFile(const char *inputFileName, FILE *outputFile){
    FILE *inputFile = fopen(inputFileName, "r");
    if(inputFile == NULL){
        fprintf(outputFile, ERROR_OPENING_FILE, inputFileName);
        return NULL;
    }

    char line[MAX_LINE];
    StudentNode *head = NULL;
    StudentNode *current = NULL;
    int lineCount = 0;

    while(fgets(line, MAX_LINE, inputFile)){
        lineCount ++;
        // Remove the newline character, if present
        size_t len = strlen(line);
        if(len > 0 && line[len - 1] == '\n'){
            line[len - 1] = '\0';
        }

        // Parse the line into tokens
        char *tokens[6]; // One extra to detect extra tokens
        int tokenCount = 0;
        char *token = strtok(line, " ");
        while(token != NULL && tokenCount < 6){
            tokens[tokenCount ++] = token;
            token = strtok(NULL, " ");
        }

        // Check for extra tokens
        if(tokenCount > ARGUMENT_COUNT_INTERNATIONAL){
            fprintf(outputFile, ERROR_INVALID_FORMAT);
            fclose(inputFile);
            freeList(head);
            return NULL;
        }

        // Check for minimum required tokens
        if(tokenCount < ARGUMENT_COUNT_DOMESTIC){
            fprintf(outputFile, ERROR_INVALID_FORMAT);
            fclose(inputFile);
            freeList(head);
            return NULL;
        }

        char status = tokens[3][0];
        float gpa = atof(tokens[2]);

        // Validate the number of tokens based on status
        if((status != STATUS_DOMESTIC && status != STATUS_INTERNATIONAL) ||
           (status == STATUS_INTERNATIONAL &&
            tokenCount != ARGUMENT_COUNT_INTERNATIONAL) ||
           (status == STATUS_DOMESTIC && tokenCount != ARGUMENT_COUNT_DOMESTIC) ||
           ! isValidFloat(tokens[2]) ||
           gpa < MINIMUM_GPA_VALUE){
            fprintf(outputFile, ERROR_INVALID_FORMAT);
            fclose(inputFile);
            freeList(head);
            return NULL;
        }

        // Create a new student node
        StudentNode *newNode = (StudentNode *) malloc(sizeof(StudentNode));
        if(newNode == NULL){
            fprintf(outputFile, "Error: Memory allocation failed.\n");
            fclose(inputFile);
            freeList(head);
            return NULL;
        }
        newNode->next = NULL;

        if(status == STATUS_DOMESTIC){
            // Populate DomesticStudent fields
            newNode->type = DOMESTIC;
            strncpy(newNode->student.domestic.firstName, tokens[0],
                    sizeof(newNode->student.domestic.firstName) - 1);
            newNode->student.domestic.firstName[
                    sizeof(newNode->student.domestic.firstName) - 1] = '\0';
            strncpy(newNode->student.domestic.lastName, tokens[1],
                    sizeof(newNode->student.domestic.lastName) - 1);
            newNode->student.domestic.lastName[
                    sizeof(newNode->student.domestic.lastName) - 1] = '\0';
            newNode->student.domestic.gpa = gpa;
            newNode->student.domestic.status = STATUS_DOMESTIC;
        } else{
            // Validate TOEFL score
            int toefl = atoi(tokens[4]);
            if(! isValidInt(tokens[4]) ||
               toefl < MINIMUM_TOEFL_VALUE || toefl > MAXIMUM_TOEFL_VALUE){
                fprintf(outputFile, ERROR_INVALID_FORMAT);
                free(newNode);
                fclose(inputFile);
                freeList(head);
                return NULL;
            }

            // Populate InternationalStudent fields
            newNode->type = INTERNATIONAL;
            strncpy(newNode->student.international.firstName, tokens[0],
                    sizeof(newNode->student.international.firstName) - 1);
            newNode->student.international.firstName[
                    sizeof(newNode->student.international.firstName) - 1] = '\0';
            strncpy(newNode->student.international.lastName, tokens[1],
                    sizeof(newNode->student.international.lastName) - 1);
            newNode->student.international.lastName[
                    sizeof(newNode->student.international.lastName) - 1] = '\0';
            newNode->student.international.gpa = gpa;
            newNode->student.international.status = STATUS_INTERNATIONAL;
            newNode->student.international.toefl = toefl;
        }

        // Append the new node to the linked list
        if(head == NULL){
            head = newNode;
            current = newNode;
        } else{
            current->next = newNode;
            current = newNode;
        }
    }

    fclose(inputFile);
    return head;
}

// Function to write a domestic student to the output file
void writeDomesticStudent(FILE *outputFile, DomesticStudent student){
    fprintf(outputFile, "%s %s %.3f %c\n",
            student.firstName,
            student.lastName,
            student.gpa,
            student.status);
}

// Function to write an international student to the output file
void writeInternationalStudent(FILE *outputFile, InternationalStudent student){
    fprintf(outputFile, "%s %s %.3f %c %d\n",
            student.firstName,
            student.lastName,
            student.gpa,
            student.status,
            student.toefl);
}

// Function to write the filtered students to the output file based on the selected option
void writeOutputFile(const char *filename, StudentNode *head, int option){
    FILE *outputFile = fopen(filename, "w");
    if(outputFile == NULL){
        fprintf(stderr, ERROR_OPENING_FILE, filename);
        return;
    }

    StudentNode *current = head;

    while(current != NULL){
        if(current->type == DOMESTIC){
            if((option == OPTION_DOMESTIC_ONLY || option == OPTION_BOTH) &&
               current->student.domestic.gpa > GPA_LIMIT){
                writeDomesticStudent(outputFile, current->student.domestic);
            }
        } else{
            if((option == OPTION_INTERNATIONAL_ONLY || option == OPTION_BOTH) &&
               current->student.international.gpa > GPA_LIMIT &&
               current->student.international.toefl >= TOEFL_LIMIT){
                writeInternationalStudent(outputFile, current->student.international);
            }
        }
        current = current->next;
    }

    printf(SUCCESS_WRITE_FILE);
    fclose(outputFile);
}

// Function to free the memory allocated for the linked list
void freeList(StudentNode *head){
    StudentNode *current = head;
    while(current != NULL){
        StudentNode *temp = current;
        current = current->next;
        free(temp);
    }
}
