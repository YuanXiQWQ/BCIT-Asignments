#ifndef CODE_STUDENT_H
#define CODE_STUDENT_H

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

int monthStrToEnum(const char *monthStr);

int isInternational(const Student *student);

#endif
