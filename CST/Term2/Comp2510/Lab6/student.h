#ifndef STUDENT_H
#define STUDENT_H

#include <stdio.h>

// Constants for error messages and limits
#define ERROR_INVALID_FORMAT "Error: Invalid format.\n"
#define ERROR_INVALID_OPTION "Error: Undefined option.\n"
#define ERROR_OPENING_FILE "Error opening file: \"%s\"\n"
#define SUCCESS_WRITE_FILE "The file has been successfully written\n"
#define USAGE "Usage: %s <input file> <output file> <option>\n" \
              "Options:\n" \
              " \"1\": select domestic students with GPA > 3.9\n" \
              " \"2\": select international students with GPA > 3.9, TOEFL score >= 70\n" \
              " \"3\": select all students with GPA > 3.9, TOEFL score >= 70 (international)\n"

#define ARGUMENT_COUNT_INTERNATIONAL 5
#define ARGUMENT_COUNT_DOMESTIC 4
#define GPA_LIMIT 3.9
#define MAX_LINE 1024
#define MAXIMUM_TOEFL_VALUE 120
#define MINIMUM_GPA_VALUE 0.0
#define MINIMUM_TOEFL_VALUE 0
#define OPTION_BOTH 3
#define OPTION_DOMESTIC_ONLY 1
#define OPTION_INTERNATIONAL_ONLY 2
#define STATUS_DOMESTIC 'D'
#define STATUS_INTERNATIONAL 'I'
#define TOEFL_LIMIT 70

// Enum for student type
typedef enum{
    DOMESTIC,
    INTERNATIONAL
} StudentType;

// Structure for Domestic Student
typedef struct{
    char firstName[50];
    char lastName[50];
    float gpa;
    char status;
} DomesticStudent;

// Structure for International Student
typedef struct{
    char firstName[50];
    char lastName[50];
    float gpa;
    char status;
    int toefl;
} InternationalStudent;

// Node structure for linked list
typedef struct StudentNode{
    StudentType type;
    union{
        DomesticStudent domestic;
        InternationalStudent international;
    } student;
    struct StudentNode *next;
} StudentNode;

// Function prototypes
StudentNode *parseInputFile(const char *inputFileName, FILE *outputFile);

void writeInternationalStudent(FILE *outputFile, InternationalStudent student);

void writeDomesticStudent(FILE *outputFile, DomesticStudent student);

void writeOutputFile(const char *filename, StudentNode *head, int option);

void freeList(StudentNode *head);

int isValidFloat(const char *str);

int isValidInt(const char *str);

#endif // STUDENT_H
