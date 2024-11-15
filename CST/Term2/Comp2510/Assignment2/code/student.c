#include "student.h"
#include <string.h>

#define MONTHS_PER_YEAR 12

const char *months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

/**
 * Convert the three-character month abbreviation to the corresponding Month enum
 * @param monthStr Three-character month abbreviation (e.g., "Jan", "Feb")
 * @return Corresponding Month enum value
 *         -1 if invalid
 */
int monthStrToEnum(const char *monthStr){
    for(int i = 0; i < MONTHS_PER_YEAR; i ++){
        if(strcmp(monthStr, months[i]) == 0){
            return i;  // Return the corresponding month index
        }
    }

    return - 1;
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