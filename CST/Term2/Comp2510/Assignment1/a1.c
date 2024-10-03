#include <limits.h>
#include <stdio.h>
#include <stdlib.h>

#define ERROR_INVALID_ARGUMENT_LINE_LENGTH "Error: Line length must be a positive integer.\n"
#define ERROR_INVALID_ARGUMENT_USAGE "Usage: %s <line_length> <input_file>\n"
#define ERROR_INVALID_FILE "Error: Cannot open file %s.\n"

/**
 * Function to check the input arguments and print an error message if invalid.
 *
 * @param argc The number of arguments passed to the program.
 * @param argv An array of strings representing the arguments.
 *        argv[1] - the maximum number of characters per line (line_length)
 *
 * @return 0 if the input arguments are valid.
 *         1 if the input arguments are invalid.
 */
int verify_input(int argc, char *argv[], int *line_length)
{
    // Check the input arguments number, should be 3(<executable> <line_length> <input_file>)
    if(argc != 3)
    {
        fprintf(stderr, ERROR_INVALID_ARGUMENT_USAGE, argv[0]);
        return 1;
    }

    // Check the line length
    /* strtol - Function to convert a string to an integer
     * long int strtol(const char *nptr, char **endptr, int base);
     * *nptr: string to be converted
     * **endptr: pointer to the character after the end of the number, use for knowing
     *           the stop point during the conversion
     * *base: base of the number
     * */

    // Pointer to the character after the end of the string
    char *endptr;
    long int temp_line_length = strtol(argv[1], &endptr, 10);
    if(*endptr != '\0' || temp_line_length <= 0 || temp_line_length > INT_MAX)
    {
        fprintf(stderr, ERROR_INVALID_ARGUMENT_LINE_LENGTH);
        return 1;
    }
    *line_length = (int) temp_line_length;
    return 0;
}

/**
 * Function to read and save the file content into memory, and returns a pointer to it if
 * successful.
 *
 * @param file The file pointer to the input file
 * @return char* The pointer to the string content of the file
 *         NULL if an error occurred
 *         A donut if Ayesha is hungry
 * */
char *read_file(FILE *file)
{
    // Move the file pointer to the end of the file to get its size
    /* Function doc:
     * fseek(FILE *stream, long offset, int origin);
     * *stream: file pointer
     * offset: offset from whence
     * origin: 0 - SEEK_SET (defined)
     *         1 - SEEK_CUR
     *         2 - SEEK_END
     * return 0 if successful
     * */
    if(fseek(file, 0, SEEK_END) != 0)
    {
        return NULL;
    }

    // Get the file size
    /* Function doc:
     * long ftell(FILE *stream);
     * *stream: file pointer
     * return long -1L if an error occurred
     * */
    long file_size = ftell(file);
    if(file_size == - 1L)
    {
        return NULL;
    }

    // Move the file pointer to the beginning of the file
    if(fseek(file, 0, SEEK_SET) != 0)
    {
        return NULL;
    }

    // Allocate memory for the file content using the size just calculated, +1 for \0
    char *content = (char *) malloc(file_size + 1);
    if(content == NULL)
    {
        return NULL;
    }

    // Read the file content
    /* Function doc:
     * size_t fread(void *ptr, size_t size, size_t nmemb, FILE *stream);
     * *ptr: pointer to the buffer, where the content will be written
     * size: size of each element
     * nmemb: number of elements
     * *stream: file pointer
     * return size_t the number of bytes successfully read
     */
    size_t bytes_read = fread(content, 1, file_size, file);
    // If the data read from content is not complete
    if(bytes_read != file_size)
    {
        free(content);
        return NULL;
    }

    // Add \0 to the end(C string rule)
    content[file_size] = '\0';
    return content;
}

/**
 * Main function to run the program with formatted file output.
 *
 * This program requires 3 command-line arguments:
 * 1. The name of the executable.
 * 2. The maximum number of characters per line (line_length).
 * 3. The input file name containing the text to format.
 *
 * Example usage:
 *     ./a1 10 input.txt
 * This will format the content of the file `input.txt` with a maximum of 10 characters
 * per line, ensuring proper justification.
 *
 * Features:
 * - Cmd parameter parsing
 * - Read input file content
 * TODO- Split text into words
 * TODO- Align both ends of the text
 * TODO- Deal with hyphenated words
 * TODO- Error handling for long lines
 * TODO- Output formatted content
 *
 * @param argc The number of arguments passed to the program.
 * @param argv An array of strings representing the arguments.
 *        argv[0] - the name of the executable
 *        argv[1] - the maximum number of characters per line (line_length)
 *        argv[2] - the input file name
 *
 * @return 0 on successful execution.
 *         1 if the number of arguments is incorrect.
 */
int main(int argc, char *argv[])
{
    int line_length;

    // Check the input arguments.
    if(verify_input(argc, argv, &line_length) != 0)
    {
        return 1;
    }

    // Check the input file
    FILE *file = fopen(argv[2], "r");
    if(file == NULL)
    {
        fprintf(stderr, ERROR_INVALID_FILE, argv[2]);
        return 1;
    }

    // TODO: Open the output file and write formatted content
    fclose(file);
    return 0;
}
