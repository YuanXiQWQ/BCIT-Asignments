#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "student.h"

#define STUDENT_INFO_COUNT 5

#define ERROR_OPEN_FILE "Error: Could not open file %s\n"
#define WARNING_INVALID_FORMAT "Warning: Invalid format in line %d\n"
#define WARNING_NOT_WRITEN "Warning: Student %s %s (ID: %s) does not meet criteria (option %d)\n"

/**
 * @brief Compares two students based on multiple criteria following a priority order.
 *
 * This function compares two students by applying the following priority order:
 * 1. Last Name: Compared using strcmp(). If different, the result is returned.
 * 2. First Name: If last names are the same, compares first names.
 * 3. Student Number: If names are identical, compares student numbers.
 * 4. Midterm Grade: If names and student numbers are the same, compares midterm grades.
 * 5. Final Grade: If all other criteria are the same, compares final grades.
 *
 * @param a Pointer to the first Student structure to compare.
 * @param b Pointer to the second Student structure to compare.
 * @return int 0 if the students are equal,
 *             < 0 if a is less than b,
 *             > 0 if a is greater than b.
 */
int compare_students(const Student *a, const Student *b){
    // 1.Last Name
    int cmp = strcmp(a->last_name, b->last_name);
    // Return if not equal
    if(cmp != 0){
        return cmp;
    }

    // 2. First Name
    cmp = strcmp(a->first_name, b->first_name);
    // Return if not equal
    if(cmp != 0){
        return cmp;
    }

    // 3. Student Number
    cmp = strcmp(a->student_number, b->student_number);
    // Return if not equal
    if(cmp != 0){
        return cmp;
    }

    // 4. Midterm Grade
    if(a->midterm_grade != b->midterm_grade){
        return a->midterm_grade - b->midterm_grade;
    }

    // 5. Final Grade
    return a->final_grade - b->final_grade;
}

/**
 * @brief Merges two sorted halves of an array of Student structures into a single sorted segment.
 *
 * This function takes an array segment defined by left, mid, and right indices,
 * where the two halves (from left to mid and from mid+1 to right) are already sorted.
 * It merges these two halves back into the original array in sorted order.
 *
 * @param students An array of Student structures containing the segments to merge.
 * @param left The starting index of the left sorted half.
 * @param mid The ending index of the left sorted half, and one less than the starting index of the right sorted half.
 * @param right The ending index of the right sorted half.
 */
void merge(Student students[], int left, int mid, int right){
    // Size of the left half and right half
    int n1 = mid - left + 1;
    int n2 = right - mid;

    // Allocate memory for temporary arrays
    Student *L = (Student *) malloc(n1 * sizeof(Student));
    Student *R = (Student *) malloc(n2 * sizeof(Student));

    // Copy data into the temporary arrays
    for(int i = 0; i < n1; i ++){
        L[i] = students[left + i];
    }
    for(int j = 0; j < n2; j ++){
        R[j] = students[mid + 1 + j];
    }

    // Merge the two halves back into the original array
    int i = 0, j = 0, k = left;
    while(i < n1 && j < n2){
        students[k ++] = compare_students(&L[i], &R[j]) <= 0
                         ? L[i ++]
                         : R[j ++];
    }

    // Copy any remaining elements of L[]
    while(i < n1){
        students[k ++] = L[i ++];
    }

    // Copy any remaining elements of R[]
    while(j < n2){
        students[k ++] = R[j ++];
    }

    // Free the allocated memory
    free(L);
    free(R);
}

/**
 * @brief Recursively sorts an array of Student structures using the Merge Sort algorithm.
 *
 * This function recursively divides the array into two halves, sorts each half,
 * and merges them back into a single sorted array. Sorting is based on multiple
 * criteria defined in the compare_students function.
 *
 * @param students An array of Student structures to be sorted.
 * @param left The starting index of the array segment to be sorted.
 * @param right The ending index of the array segment to be sorted.
 */
void merge_sort(Student students[], int left, int right){
    if(left < right){
        // Calculate mid-point
        int mid = left + (right - left) / 2;
        // Sort the left half
        merge_sort(students, left, mid);
        // Sort the right half
        merge_sort(students, mid + 1, right);
        // Merge the two halves
        merge(students, left, mid, right);
    }
}

/**
 * @brief Calculates the average grade of a student.
 *
 * This function computes the average of a student's midterm and final grades.
 *
 * @param student A constant pointer to a Student structure, containing the grades.
 * @return The average grade as a float.
 */
float calculate_average(const Student *student){
    return (float) (student->midterm_grade + student->final_grade) / 2.0f;
}

/**
 * @brief Reads student data from a specified input file.
 *
 * This function opens the specified file in read mode, reads each line
 * containing student data, and stores the data in an array of Student structures.
 * The function reads up to max_students entries or until the end of the file.
 *
 * @param filename The name of the file to read from.
 * @param students An array of Student structures to store the read data.
 * @param max_students The maximum number of students to read.
 * @return The number of students successfully read from the file.
 */
int read_students(const char *filename, Student students[], int max_students){
    // Open the file for reading
    FILE *file = fopen(filename, "r");
    // Check if the file opened successfully
    if(! file){
        fprintf(stderr, ERROR_OPEN_FILE, filename);
        return 0;
    }

    // Counter for the number of students
    int count = 0;
    // Read data line by line from the file
    while(count < max_students){
        if(fscanf(file, "%s %s %s %d %d",
                  students[count].last_name,
                  students[count].first_name,
                  students[count].student_number,
                  &students[count].midterm_grade,
                  &students[count].final_grade) == STUDENT_INFO_COUNT){
            count ++;
        } else{
            // Invalid format, show the error line
            fprintf(stderr, WARNING_INVALID_FORMAT, count);

            // Clear the rest of the line to avoid affecting subsequent reads
            int ch;
            while((ch = fgetc(file)) != '\n' && ch != EOF){}

            // Skip to the next iteration of the loop
            continue;
        }
    }

    // Close the file
    fclose(file);
    // Return the number of students read
    return count;
}

/**
 * @brief Writes student data that meets specified criteria to an output file.
 *
 * This function filters students based on their average grades according to
 * the specified option, and writes those who meet the criteria to the output file.
 *
 * @param filename The name of the output file.
 * @param students An array of Student structures containing the student data.
 * @param count The number of students in the array.
 * @param option The filtering option (1-5) to determine the grade range.
 */
void write_to_file(const char *filename, const Student students[], int count, int option){
    // Open the file for writing
    FILE *file = fopen(filename, "w");
    // Check if the file opened successfully
    if(! file){
        fprintf(stderr, ERROR_OPEN_FILE, filename);
        return;
    }

    // Loop through each student and filter based on the selected option
    for(int i = 0; i < count; i ++){
        // Calculate the average grade
        float avg = calculate_average(&students[i]);
        // Initialise the write flag
        int write = 0;

        // Determine if the student meets the criteria for the selected option
        switch(option){
            case 1:
                if(avg > 90){
                    write = 1;
                }
                break;
            case 2:
                if(avg >= 80 && avg <= 90){
                    write = 1;
                }
                break;
            case 3:
                if(avg >= 70 && avg < 80){
                    write = 1;
                }
                break;
            case 4:
                if(avg >= 60 && avg < 70){
                    write = 1;
                }
                break;
            case 5:
                if(avg < 60){
                    write = 1;
                }
                break;
            default: break;
        }

        // Write the student's data if it meets the criteria
        if(write){
            fprintf(file, "%s %s %s %d %d %.2f\n",
                    students[i].last_name,
                    students[i].first_name,
                    students[i].student_number,
                    students[i].midterm_grade,
                    students[i].final_grade,
                    avg);
        } else{
            fprintf(stderr, WARNING_NOT_WRITEN,
                    students[i].first_name,
                    students[i].last_name,
                    students[i].student_number,
                    option);
        }
    }

    // Close the output file
    fclose(file);
}
