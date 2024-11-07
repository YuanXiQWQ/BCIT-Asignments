#include <string.h>
#include <stdio.h>
#include "student.h"

#define STUDENT_INFO_COUNT 5

#define ERROR_OPEN_FILE "Error: Could not open file %s\n"

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
 * @brief Partitions the students array around a pivot element for quicksort.
 *
 * This function selects the last element in the current subarray as the pivot.
 * It then rearranges the elements in the array such that all elements less than
 * or equal to the pivot are on the left of the pivot, and all elements greater
 * than the pivot are on the right. Finally, it places the pivot in its correct
 * sorted position and returns the index of the pivot.
 *
 * @param students Array of Student structures to be partitioned.
 * @param left The starting index of the subarray to partition.
 * @param right The ending index of the subarray to partition.
 * @return The index of the pivot element after partitioning.
 */
static int partition(Student students[], int left, int right){
    Student pivot = students[right];
    int i = left - 1;

    // Compare students[j] with pivot and swap if necessary
    for(int j = left; j < right; j ++){
        if(compare_students(&students[j], &pivot) <= 0){
            i ++;

            // Swap students[i] and students[j]
            Student temp = students[i];
            students[i] = students[j];
            students[j] = temp;
        }
    }
    Student temp = students[i + 1];
    students[i + 1] = students[right];
    students[right] = temp;

    return i + 1;
}

/**
 * @brief Sorts an array of Student structures using the quicksort algorithm.
 *
 * This function implements the quicksort algorithm to sort an array of Student
 * structures based on multiple criteria (defined by compare_students function).
 * It recursively partitions the array into subarrays and sorts each part.
 *
 * @param students Array of Student structures to be sorted.
 * @param left The starting index of the subarray to sort.
 * @param right The ending index of the subarray to sort.
 */
void quick_sort(Student students[], int left, int right){
    if(left < right){
        int pi = partition(students, left, right);

        // Recursively sort the two sub-arrays before and after the pivot element
        quick_sort(students, left, pi - 1);
        quick_sort(students, pi + 1, right);
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
        int result = fscanf(file, "%s %s %s %d %d",
                            students[count].last_name,
                            students[count].first_name,
                            students[count].student_number,
                            &students[count].midterm_grade,
                            &students[count].final_grade);

        // Check if the read was successful
        if( result == STUDENT_INFO_COUNT){
            count ++;
        } else if(result == EOF){
            // Reached the end of the file, stop reading
            break;
        } else {
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
        }
    }

    // Close the output file
    fclose(file);
}
