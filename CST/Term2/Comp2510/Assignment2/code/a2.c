#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "student.h"

/**
 * Function prototypes
 */
int compareStudents(const void *a, const void *b);

void mergeSort(Student *arr, int left, int right);

void merge(Student *arr, int left, int mid, int right);

void toLowerCase(char *str);

/**
 * Main function
 */
int main(int argc, char *argv[]){
    if(argc != 4){
        fprintf(stderr, "Error: Incorrect number of arguments.\n");
        return 1;
    }

    char *inputFile = argv[1];
    char *outputFile = argv[2];
    int option = atoi(argv[3]);

    // Validate option
    if(option < 1 || option > 3){
        FILE *fout = fopen(outputFile, "w");
        if(fout == NULL){
            fprintf(stderr, "Error: Cannot open output file.\n");
            return 1;
        }
        fprintf(fout, "Error: Invalid option.\n");
        fclose(fout);
        return 1;
    }

    FILE *fin = fopen(inputFile, "r");
    if(fin == NULL){
        FILE *fout = fopen(outputFile, "w");
        if(fout == NULL){
            fprintf(stderr, "Error: Cannot open output file.\n");
            return 1;
        }
        fprintf(fout, "Error: Cannot open input file.\n");
        fclose(fout);
        return 1;
    }

    // Dynamic array for students
    int capacity = 1000;
    int count = 0;
    Student *students = (Student *) malloc(sizeof(Student) * capacity);
    if(students == NULL){
        fprintf(stderr, "Error: Memory allocation failed.\n");
        fclose(fin);
        return 1;
    }

    char line[256];
    int lineNumber = 0;
    while(fgets(line, sizeof(line), fin)){
        lineNumber ++;
        // Remove newline character
        line[strcspn(line, "\n")] = 0;

        // Tokenize the line
        char firstName[64], lastName[64], monthStr[4];
        int day, year;
        float gpa;
        char status;
        int toefl = - 1;

        // Initialize temporary student
        Student tempStudent;
        memset(&tempStudent, 0, sizeof(Student));

        // Attempt to parse the mandatory fields
        int tokens = sscanf(line, "%s %s %3s-%d-%d %f %c %d", firstName, lastName,
                            monthStr, &day, &year, &gpa, &status, &toefl);

        // Minimum required tokens: 6 (without TOEFL)
        if(tokens < 6){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Missing fields in input at line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Assign parsed values to tempStudent
        strcpy(tempStudent.firstName, firstName);
        strcpy(tempStudent.lastName, lastName);
        tempStudent.birthM = monthStrToEnum(monthStr);
        tempStudent.birthD = day;
        tempStudent.birthY = year;
        tempStudent.gpa = gpa;
        tempStudent.status = status;
        tempStudent.toeflScore = - 1; // Default for domestic

        // Validate month
        if(tempStudent.birthM == - 1){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Invalid month abbreviation at line %d.\n",
                        lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Validate GPA
        if(tempStudent.gpa < 0.0 || tempStudent.gpa > 4.3){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Invalid GPA value at line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Validate year
        if(tempStudent.birthY < 1950 || tempStudent.birthY > 2010){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Invalid year of birth at line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Validate day
        if(tempStudent.birthD < 1 ||
           tempStudent.birthD > 31){ // Simplistic day validation
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Invalid day of birth at line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Validate status
        if(tempStudent.status != 'I' && tempStudent.status != 'i' &&
           tempStudent.status != 'D' && tempStudent.status != 'd'){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Invalid status value at line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Check TOEFL score based on status
        if(isInternational(&tempStudent)){
            if(tokens < 7){
                FILE *fout = fopen(outputFile, "w");
                if(fout != NULL){
                    fprintf(fout,
                            "Error: Missing TOEFL score for international student at line %d.\n",
                            lineNumber);
                    fclose(fout);
                }
                free(students);
                fclose(fin);
                return 1;
            }
            // Validate TOEFL score
            if(toefl < 0 || toefl > 120){
                FILE *fout = fopen(outputFile, "w");
                if(fout != NULL){
                    fprintf(fout, "Error: Invalid TOEFL score at line %d.\n", lineNumber);
                    fclose(fout);
                }
                free(students);
                fclose(fin);
                return 1;
            }
            tempStudent.toeflScore = toefl;
        } else{
            // For domestic students, ensure no TOEFL score is present
            if(tokens == 7){ // If TOEFL score is provided for domestic student
                FILE *fout = fopen(outputFile, "w");
                if(fout != NULL){
                    fprintf(fout,
                            "Error: TOEFL score provided for domestic student at line %d.\n",
                            lineNumber);
                    fclose(fout);
                }
                free(students);
                fclose(fin);
                return 1;
            }
        }

        // Add student to the array
        if(count >= capacity){
            capacity *= 2;
            Student *temp = realloc(students, sizeof(Student) * capacity);
            if(temp == NULL){
                fprintf(stderr, "Error: Memory reallocation failed.\n");
                free(students);
                fclose(fin);
                return 1;
            }
            students = temp;
        }
        students[count ++] = tempStudent;
    }

    fclose(fin);

    // Filter students based on option
    int filteredCount = 0;
    Student *filteredStudents = (Student *) malloc(sizeof(Student) * count);
    if(filteredStudents == NULL){
        fprintf(stderr, "Error: Memory allocation failed.\n");
        free(students);
        return 1;
    }

    for(int i = 0; i < count; i ++){
        if(option == 1 && (students[i].status == 'D' || students[i].status == 'd')){
            filteredStudents[filteredCount ++] = students[i];
        } else if(option == 2 &&
                  (students[i].status == 'I' || students[i].status == 'i')){
            filteredStudents[filteredCount ++] = students[i];
        } else if(option == 3){
            filteredStudents[filteredCount ++] = students[i];
        }
    }

    free(students);

    // Sort the filtered students using merge sort
    if(filteredCount > 1){
        mergeSort(filteredStudents, 0, filteredCount - 1);
    }

    // Write to output file
    FILE *fout = fopen(outputFile, "w");
    if(fout == NULL){
        fprintf(stderr, "Error: Cannot open output file.\n");
        free(filteredStudents);
        return 1;
    }

    for(int i = 0; i < filteredCount; i ++){
        fprintf(fout, "%s %s ", filteredStudents[i].firstName,
                filteredStudents[i].lastName);
        // Convert month enum back to string
        char *monthStr;
        switch(filteredStudents[i].birthM){
            case Jan: monthStr = "Jan";
                break;
            case Feb: monthStr = "Feb";
                break;
            case Mar: monthStr = "Mar";
                break;
            case Apr: monthStr = "Apr";
                break;
            case May: monthStr = "May";
                break;
            case Jun: monthStr = "Jun";
                break;
            case Jul: monthStr = "Jul";
                break;
            case Aug: monthStr = "Aug";
                break;
            case Sep: monthStr = "Sep";
                break;
            case Oct: monthStr = "Oct";
                break;
            case Nov: monthStr = "Nov";
                break;
            case Dec: monthStr = "Dec";
                break;
            default: monthStr = "Unknown";
                break;
        }
        // Ensure day is valid (1-31)
        if(filteredStudents[i].birthD < 1 || filteredStudents[i].birthD > 31){
            fprintf(fout, "Error: Invalid day of birth.\n");
            fclose(fout);
            free(filteredStudents);
            return 1;
        }
        // Print formatted student information
        fprintf(fout, "%s-%d-%d %.3f %c", monthStr, filteredStudents[i].birthD,
                filteredStudents[i].birthY, filteredStudents[i].gpa,
                filteredStudents[i].status);
        if(isInternational(&filteredStudents[i])){
            fprintf(fout, " %d", filteredStudents[i].toeflScore);
        }
        fprintf(fout, "\n");
    }

    fclose(fout);
    free(filteredStudents);

    return 0;
}

/**
 * Compare two students based on the sorting criteria
 */
int compareStudents(const void *a, const void *b){
    const Student *s1 = (const Student *) a;
    const Student *s2 = (const Student *) b;

    // 1. Year of birth
    if(s1->birthY != s2->birthY){
        return s1->birthY - s2->birthY;
    }

    // 2. Month of birth
    if(s1->birthM != s2->birthM){
        return s1->birthM - s2->birthM;
    }

    // 3. Day of birth
    if(s1->birthD != s2->birthD){
        return s1->birthD - s2->birthD;
    }

    // 4. Last name (case-insensitive)
    char last1[64], last2[64];
    strcpy(last1, s1->lastName);
    strcpy(last2, s2->lastName);
    toLowerCase(last1);
    toLowerCase(last2);
    int cmp = strcmp(last1, last2);
    if(cmp != 0){
        return cmp;
    }

    // 5. First name (case-insensitive)
    char first1[64], first2[64];
    strcpy(first1, s1->firstName);
    strcpy(first2, s2->firstName);
    toLowerCase(first1);
    toLowerCase(first2);
    cmp = strcmp(first1, first2);
    if(cmp != 0){
        return cmp;
    }

    // 6. GPA
    if(s1->gpa < s2->gpa){
        return - 1;
    }
    if(s1->gpa > s2->gpa){
        return 1;
    }

    // 7. TOEFL Score
    if(isInternational(s1) && isInternational(s2)){
        return s1->toeflScore - s2->toeflScore;
    } else if(isInternational(s1)){
        return 1; // Domestic students take precedence
    } else if(isInternational(s2)){
        return - 1;
    }

    // 8. Domestic > International (already handled above)
    return 0;
}

/**
 * Merge Sort implementation
 */
void mergeSort(Student *arr, int left, int right){
    if(left < right){
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}

void merge(Student *arr, int left, int mid, int right){
    int n1 = mid - left + 1;
    int n2 = right - mid;

    // Create temp arrays
    Student *L = (Student *) malloc(n1 * sizeof(Student));
    Student *R = (Student *) malloc(n2 * sizeof(Student));

    if(L == NULL || R == NULL){
        fprintf(stderr, "Error: Memory allocation failed during merge.\n");
        exit(1);
    }

    // Copy data to temp arrays
    for(int i = 0; i < n1; i ++){
        L[i] = arr[left + i];
    }
    for(int j = 0; j < n2; j ++){
        R[j] = arr[mid + 1 + j];
    }

    // Merge the temp arrays back into arr
    int i = 0, j = 0, k = left;
    while(i < n1 && j < n2){
        if(compareStudents(&L[i], &R[j]) <= 0){
            arr[k ++] = L[i ++];
        } else{
            arr[k ++] = R[j ++];
        }
    }

    // Copy any remaining elements
    while(i < n1){
        arr[k ++] = L[i ++];
    }
    while(j < n2){
        arr[k ++] = R[j ++];
    }

    free(L);
    free(R);
}

/**
 * Convert a string to lowercase
 */
void toLowerCase(char *str){
    for(; *str; ++ str){
        if(*str >= 'A' && *str <= 'Z'){
            *str = *str + ('a' - 'A');
        }
    }
}
