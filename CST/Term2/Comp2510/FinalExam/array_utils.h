#ifndef ARRAY_UTILS_H
#define ARRAY_UTILS_H

void reverseArray(int *arr, int start, int end);

void reverseStringArray(char *arr[], int start, int end);

void readIntArrayFromFile(const char *filename, int *arr, int *size);

void writeIntArrayToFile(const char *filename, int *arr, int size);

void readStringArrayFromFile(const char *filename, char *arr[], int *size);

void writeStringArrayToFile(const char *filename, char *arr[], int size);

#endif // ARRAY_UTILS_H