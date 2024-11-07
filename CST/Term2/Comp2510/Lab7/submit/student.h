#ifndef STUDENT_H
#define STUDENT_H

#define MAX_NAME_LENGTH 50
#define SID_LENGTH 10

typedef struct{
    char last_name[MAX_NAME_LENGTH];
    char first_name[MAX_NAME_LENGTH];
    char student_number[SID_LENGTH];
    int midterm_grade;
    int final_grade;
} Student;

int compare_students(const Student *a, const Student *b);

void quick_sort(Student students[], int left, int right);

float calculate_average(const Student *student);

int read_students(const char *filename, Student students[], int max_students);

void write_to_file(const char *filename, const Student students[], int count, int option);

#endif