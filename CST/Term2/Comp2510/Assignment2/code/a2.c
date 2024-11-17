#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * Month enum in three-character abbreviation
 */
typedef enum{
    Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec
} Month;

/**
 * Student struct. The input file format is:
 * <FirstName> <LastName> <Month>-<Day>-<Year> <GPA> <Status> [<TOEFLScore>]
 *
 * Fields:
 * - Month: Three-character abbreviation (e.g., "Jan" for January)
 * - Status:
 *     - 'I' for International
 *     - 'D' for Domestic
 * - TOEFLScore:
 *     - TOEFL score for International student
 *     - -1 if no TOEFL score (Domestic student)
 */
typedef struct{
    char firstName[64];
    char lastName[64];
    Month birthM;
    int birthD;
    int birthY;
    float gpa;
    char status;
    int toeflScore;
} Student;

/**
 * Function prototypes
 */
int monthStrToEnum(const char *monthStr);
int isInternational(const Student *student);
int compareStudents(const void *a, const void *b);
void mergeSortStudents(Student *arr, int left, int right);
void mergeStudents(Student *arr, int left, int mid, int right);
void toLowerCase(char *str);
char toLowerChar(char c);

/**
 * Convert the three-character month abbreviation to the corresponding Month enum
 * @param monthStr Three-character month abbreviation (e.g., "Jan", "Feb")
 * @return Corresponding Month enum value
 *         -1 if invalid
 */
int monthStrToEnum(const char *monthStr){
    const char *months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int MONTHS_PER_YEAR = 12;
    for(int i = 0; i < MONTHS_PER_YEAR; i++){
        if(strcmp(monthStr, months[i]) == 0){
            return i;
        }
    }
    return -1;
}

/**
 * Check if a given student is international (has a TOEFL score)
 * @param student The student to check
 * @return 1 if the student is international (has TOEFL score)
 *         0 otherwise
 */
int isInternational(const Student* student) {
    return (student->status == 'I' || student->status == 'i') ? 1 : 0;
}

/**
 * Convert a single character to lowercase
 * @param c The character to convert
 * @return Lowercase version of the character if it's uppercase, otherwise the original character
 */
char toLowerChar(char c){
    if(c >= 'A' && c <= 'Z'){
        return c + ('a' - 'A');
    }
    return c;
}

/**
 * Convert a string to lowercase
 *
 * @param str The string to convert
 */
void toLowerCase(char *str){
    for(; *str; ++str){
        *str = toLowerChar(*str);
    }
}

/**
 * Compare two students based on the sorting criteria
 *
 * @param a Pointer to the first student
 * @param b Pointer to the second student
 *
 * @return 0 if the students are equal
 *         < 0 if the first student is less than the second
 *         > 0 if the first student is greater than the second
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
        return -1;
    }
    if(s1->gpa > s2->gpa){
        return 1;
    }

    // 7. TOEFL Score
    if(isInternational(s1) && isInternational(s2)){
        return s1->toeflScore - s2->toeflScore;
    } else if(isInternational(s1)){
        // 8. Domestic > International
        return 1;
    } else if(isInternational(s2)){
        return -1;
    }

    return 0;
}

/**
 * Merge Sort implementation for Students
 *
 * @param arr Pointer to the array of Students
 * @param left The left index of the subarray
 * @param right The right index of the subarray
 */
void mergeSortStudents(Student *arr, int left, int right){
    if(left < right){
        int mid = left + (right - left) / 2;
        mergeSortStudents(arr, left, mid);
        mergeSortStudents(arr, mid + 1, right);
        mergeStudents(arr, left, mid, right);
    }
}

/**
 * Merge function for Merge Sort
 *
 * @param arr Pointer to the array of Students
 * @param left The left index of the subarray
 * @param mid The middle index of the subarray
 * @param right The right index of the subarray
 */
void mergeStudents(Student *arr, int left, int mid, int right){
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
    for(int i = 0; i < n1; i++){
        L[i] = arr[left + i];
    }
    for(int j = 0; j < n2; j++){
        R[j] = arr[mid + 1 + j];
    }

    // Merge the temp arrays back into arr
    int i = 0, j = 0, k = left;
    while(i < n1 && j < n2){
        if(compareStudents(&L[i], &R[j]) <= 0){
            arr[k++] = L[i++];
        } else{
            arr[k++] = R[j++];
        }
    }

    // Copy any remaining elements
    while(i < n1){
        arr[k++] = L[i++];
    }
    while(j < n2){
        arr[k++] = R[j++];
    }

    free(L);
    free(R);
}

/**
 * Capitalize the first letter of the month string and make the rest lowercase
 *
 * @param str The month string to capitalize
 */
void capitalizeMonth(char *str){
    if(str[0] >= 'a' && str[0] <= 'z'){
        str[0] = str[0] - ('a' - 'A');
    }
    for(int i = 1; str[i]; i++){
        if(str[i] >= 'A' && str[i] <= 'Z'){
            str[i] = str[i] + ('a' - 'A');
        }
    }
}

/**
 * Main function
 *
 * @param argc The number of command-line arguments
 * @param argv The array of command-line arguments
 *
 * @return 0 on success
 *         1 on failure
 */
int main(int argc, char *argv[]){
    // Validate number of arguments
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
        lineNumber++;
        // Remove newline character
        line[strcspn(line, "\n")] = 0;

        // Tokenize the line
        char firstName[64], lastName[64], monthStr[4];
        int day, year;
        float gpa;
        char status;
        int toefl = -1;

        // Initialize temporary student
        Student tempStudent;
        memset(&tempStudent, 0, sizeof(Student));

        // Initialize variables for sscanf
        int fieldsRead = 0;
        int offset = 0;

        // First, parse the mandatory fields
        fieldsRead = sscanf(line, "%63s %63s %3[^-]-%d-%d %f %c%n",
                            firstName, lastName, monthStr, &day, &year, &gpa, &status, &offset);

        // Assign parsed values to tempStudent
        strcpy(tempStudent.firstName, firstName);
        strcpy(tempStudent.lastName, lastName);
        tempStudent.birthM = monthStrToEnum(monthStr);
        tempStudent.birthD = day;
        tempStudent.birthY = year;
        tempStudent.gpa = gpa;
        tempStudent.status = status;
        tempStudent.toeflScore = -1; // Default for domestic

        // Validate if mandatory fields are correctly read
        if(fieldsRead < 7){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Incorrect format on line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Capitalize the month string to ensure correct format
        capitalizeMonth(monthStr);

        // Re-convert month after capitalization
        tempStudent.birthM = monthStrToEnum(monthStr);

        // Check for invalid month
        if(tempStudent.birthM == -1){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Invalid month abbreviation on line %d.\n", lineNumber);
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
                fprintf(fout, "Error: Invalid GPA value on line %d.\n", lineNumber);
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
                fprintf(fout, "Error: Invalid year of birth on line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Validate day
        if(tempStudent.birthD < 1 || tempStudent.birthD > 31){
            FILE *fout = fopen(outputFile, "w");
            if(fout != NULL){
                fprintf(fout, "Error: Invalid day of birth on line %d.\n", lineNumber);
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
                fprintf(fout, "Error: Invalid status value on line %d.\n", lineNumber);
                fclose(fout);
            }
            free(students);
            fclose(fin);
            return 1;
        }

        // Determine if the student is international
        int international = isInternational(&tempStudent);

        if(international){
            int toeflOffset = 0;
            int toeflFieldsRead = sscanf(line + offset, " %d%n", &toefl, &toeflOffset);
            if(toeflFieldsRead < 1){
                FILE *fout = fopen(outputFile, "w");
                if(fout != NULL){
                    fprintf(fout, "Error: Missing TOEFL score for international student on line %d.\n", lineNumber);
                    fclose(fout);
                }
                free(students);
                fclose(fin);
                return 1;
            }
            tempStudent.toeflScore = toefl;

            // Check for extra data after TOEFL score
            if(line[offset + toeflOffset] != '\0'){
                FILE *fout = fopen(outputFile, "w");
                if(fout != NULL){
                    fprintf(fout, "Error: Extra data after TOEFL score for international student on line %d.\n", lineNumber);
                    fclose(fout);
                }
                free(students);
                fclose(fin);
                return 1;
            }

            // Validate TOEFL score
            if(tempStudent.toeflScore < 0 || tempStudent.toeflScore > 120){
                FILE *fout = fopen(outputFile, "w");
                if(fout != NULL){
                    fprintf(fout, "Error: Invalid TOEFL score on line %d.\n", lineNumber);
                    fclose(fout);
                }
                free(students);
                fclose(fin);
                return 1;
            }
        } else{
            // For domestic students, ensure no TOEFL score is present
            int tempOffset = offset;
            while(line[tempOffset] == ' ' || line[tempOffset] == '\t'){
                tempOffset++;
            }
            if(line[tempOffset] != '\0'){
                FILE *fout = fopen(outputFile, "w");
                if(fout != NULL){
                    fprintf(fout, "Error: TOEFL score provided for domestic student on line %d.\n", lineNumber);
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
        students[count++] = tempStudent;
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

    for(int i = 0; i < count; i++){
        if(option == 1 && (students[i].status == 'D' || students[i].status == 'd')){
            filteredStudents[filteredCount++] = students[i];
        } else if(option == 2 &&
                  (students[i].status == 'I' || students[i].status == 'i')){
            filteredStudents[filteredCount++] = students[i];
        } else if(option == 3){
            filteredStudents[filteredCount++] = students[i];
        }
    }

    free(students);

    // Sort the filtered students using merge sort
    if(filteredCount > 1){
        mergeSortStudents(filteredStudents, 0, filteredCount - 1);
    }

    // Write to output file
    FILE *fout = fopen(outputFile, "w");
    if(fout == NULL){
        fprintf(stderr, "Error: Cannot open output file.\n");
        free(filteredStudents);
        return 1;
    }

    for(int i = 0; i < filteredCount; i++){
        fprintf(fout, "%s %s ", filteredStudents[i].firstName,
                filteredStudents[i].lastName);
        // Convert month enum back to string
        char *monthStr;
        switch(filteredStudents[i].birthM){
            case Jan: monthStr = "Jan"; break;
            case Feb: monthStr = "Feb"; break;
            case Mar: monthStr = "Mar"; break;
            case Apr: monthStr = "Apr"; break;
            case May: monthStr = "May"; break;
            case Jun: monthStr = "Jun"; break;
            case Jul: monthStr = "Jul"; break;
            case Aug: monthStr = "Aug"; break;
            case Sep: monthStr = "Sep"; break;
            case Oct: monthStr = "Oct"; break;
            case Nov: monthStr = "Nov"; break;
            case Dec: monthStr = "Dec"; break;
            default: monthStr = "Unknown"; break;
        }
        // Print formatted student information
        fprintf(fout, "%s-%d-%d %.3f %c", monthStr, filteredStudents[i].birthD,
                filteredStudents[i].birthY, filteredStudents[i].gpa,
                (filteredStudents[i].status == 'I' || filteredStudents[i].status == 'i') ? 'I' : 'D');
        if(isInternational(&filteredStudents[i])){
            fprintf(fout, " %d", filteredStudents[i].toeflScore);
        }
        fprintf(fout, "\n");
    }

    fclose(fout);
    free(filteredStudents);

    return 0;
}
