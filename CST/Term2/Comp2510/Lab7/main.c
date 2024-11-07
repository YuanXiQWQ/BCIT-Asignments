#include <stdio.h>
#include <stdlib.h>
#include "student.h"

#define MAX_STUDENTS 100

#define ERROR_INVALID_OPTION "Error: Option must be between 1 and 5, received %d\n"
#define ERROR_STUDENT_NOT_FOUND "Error: No students found in the input file %s.\n"
#define SUCCESS_WRITE_FILE "Data successfully written to %s.\n"
#define USAGE "Usage: %s <input_file> <output_file> <filter_option>\n" \
              "Filter Options:\n" \
              "  1. Average > 90%%\n" \
              "  2. 80%% <= Average <= 90%%\n" \
              "  3. 70%% <= Average < 80%%\n" \
              "  4. 60%% <= Average < 70%%\n" \
              "  5. Average < 60%%\n"

/**
 * The program will:
 * TODO: Sort students by last name (and other fields in case of ties).
 * TODO: Filter students based on their average grade.
 * TODO: Read input data from a file.
 * TODO: Write filtered student data to an output file.
 *
 * @param argc number of arguments, accept 3 arguments
 * @param *argv[3] The pointer to an array of arguments.
 *        argv[0] <input_file> The name of the input file containing student data.
 *        argv[1] <output_file> The name of the output file where filtered data will be saved.
 *        argv[2] <filter_option> An integer (1-5) representing the grade filter.
 *                1. Average > 90%
 *                2. 80% <= Average <= 90%
 *                3. 70% <= Average < 80%
 *                4. 60% <= Average < 70%
 *                5. Average < 60%
 *
 * @return 0 if success
 *         1 if failure
 */

int main(int argc, char *argv[]){
    // Check if the correct number of arguments is provided
    if(argc != 4){
        fprintf(stderr, USAGE, argv[0]);
        return 1;
    }

    // Get the command line arguments
    const char *input_file = argv[1];
    const char *output_file = argv[2];
    int option = atoi(argv[3]);

    // Check if the filter option is valid
    if(option < 1 || option > 5){
        fprintf(stderr, ERROR_INVALID_OPTION, option);
        return 1;
    }

    // Read student data from the input file
    Student students[MAX_STUDENTS];
    int student_count = read_students(input_file, students, MAX_STUDENTS);

    // Check if any students were read
    if(student_count == 0){
        fprintf(stderr, ERROR_STUDENT_NOT_FOUND, input_file);
        return 1;
    }

    // Filter students based on the filter option
    merge_sort(students, 0, student_count - 1);
    write_to_file(output_file, students, student_count, option);

    // Confirm successful write
    printf(SUCCESS_WRITE_FILE, output_file);
    return 0;
}
