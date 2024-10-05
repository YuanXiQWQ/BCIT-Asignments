#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ERROR_FREAD_FAILED "Error: fread failed. Bytes read: %zu, Expected: %ld.\n"
#define ERROR_INVALID_ARGUMENT_LINE_LENGTH "Error: Line length must be a positive integer.\n"
#define ERROR_INVALID_ARGUMENT_USAGE "Usage: %s <line_length> <input_file>\n"
#define ERROR_INVALID_FILE "Error: Cannot open file %s.\n"
#define ERROR_MEMORY_ALLOCATION_FAILED "Error: Memory allocation failed.\n"
#define ERROR_NULL_WORD "Error: Word at index %d is NULL.\n"
#define ERROR_READING_FILE "Error: Failed to read file %s.\n"
#define ERROR_WORD_PROCESSOR "Error. The word processor can't display the output.\n"

/**
 * Function to check if a character is a whitespace character.
 *
 * @param ch The character to check.
 * @return 1 if the character is a whitespace character, 0 otherwise.
 * */
int is_space(char ch)
{
    return ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r' || ch == '\v' ||
           ch == '\f';
}

/**
 * Function to check the input arguments and print an error message if invalid.
 *
 * This function checks whether the correct number of arguments is provided and
 * whether the line length argument is a valid positive integer within the allowable range.
 *
 * @param argc The number of arguments passed to the program.
 * @param argv An array of strings representing the arguments.
 *        argv[1] - the maximum number of characters per line (line_length)
 * @param line_length A pointer to an integer to store the line length.
 *
 * @return 0 if the input arguments are valid.
 *         1 if the input arguments are invalid.
 * */
int verify_input(int argc, char *argv[], int *line_length)
{
    // Check the input arguments number, should be 3(<executable> <line_length> <input_file>)
    if(argc != 3)
    {
        fprintf(stderr, ERROR_INVALID_ARGUMENT_USAGE, argv[0]);
        return 1;
    }

    // Check if the parameters are valid.
    /* Function doc:
     * strtol - Function to convert a string to an integer
     * long int strtol(const char *nptr, char **endptr, int base);
     * *nptr: string to be converted
     * **endptr: pointer to the character after the end of the number, use for knowing
     *           the stop point during the conversion
     * *base: base of the number
     * */

    // Pointer to the character after the end of the string
    char *endptr;
    long int temp_line_length = strtol(argv[1], &endptr, 10);
    if(*endptr != '\0' || temp_line_length <= 0)
    {
        fprintf(stderr, ERROR_INVALID_ARGUMENT_LINE_LENGTH);
        return 1;
    }
    *line_length = (int) temp_line_length;
    return 0;
}

/**
 * Function to read and save the file content into memory.
 *
 * This function determines the size of the file, allocates sufficient memory,and reads
 * the file content into a dynamically allocated buffer.
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
    if(bytes_read != (size_t) file_size)
    {
        fprintf(stderr, ERROR_FREAD_FAILED, bytes_read, file_size);
        free(content);
        return NULL;
    }

    // Add \0 to mark the end of the string
    content[file_size] = '\0';
    return content;
}

/**
 * Splits the input content into individual words.
 *
 * This function parses the input string, identifies word boundaries based on whitespace,
 * and stores each word into a dynamically allocated array.
 *
 * @param content The input string containing the text to be split.
 * @param words A pointer to a dynamically allocated array to store the extracted words.
 * @param word_count A pointer to an integer to store the total number of words extracted.
 * @param words_capacity A pointer to an integer storing the current capacity of the words array.
 * */
void split_words(char *content, char ***words, int *word_count, int *words_capacity)
{
    int char_index = 0;
    size_t content_len = strlen(content);
    *word_count = 0;
    *words_capacity = 100;
    *words = malloc(*words_capacity * sizeof(char *));
    if(*words == NULL)
    {
        fprintf(stderr, ERROR_MEMORY_ALLOCATION_FAILED);
        exit(1);
    }

    while(char_index < content_len)
    {
        // Skip leading whitespace
        while(char_index < content_len
              && is_space(content[char_index]))
        {
            char_index ++;
        }

        int word_start = char_index;
        while(char_index < content_len &&
              ! is_space(content[char_index]))
        {
            char_index ++;
        }
        int word_end = char_index;

        if(word_end > word_start)
        {
            // Add the word to the words array
            int word_length = word_end - word_start;
            char *word = malloc((word_length + 1) * sizeof(char));
            if(word == NULL)
            {
                fprintf(stderr, ERROR_MEMORY_ALLOCATION_FAILED);
                exit(1);
            }
            strncpy(word, &content[word_start], word_length);
            word[word_length] = '\0';

            // Check if the words array needs to be resized
            if(*word_count >= *words_capacity)
            {
                *words_capacity *= 2;
                char **temp = realloc(*words, (*words_capacity) * sizeof(char *));
                if(temp == NULL)
                {
                    fprintf(stderr, ERROR_MEMORY_ALLOCATION_FAILED);
                    exit(1);
                }
                *words = temp;
            }

            (*words)[(*word_count) ++] = word;
        }
    }
}

/**
 * Justifies and prints a line of words based on the specified line length.
 *
 * This function calculates the required spacing between words to ensure that the line
 * meets the desired length. If the line contains only a single word, it centers the word.
 *
 * @param words A dynamically allocated array containing the words in the current line.
 * @param word_count The number of words in the current line.
 * @param line_length The maximum number of characters per line.
 * */
void justify_line(char **words, int word_count, int line_length)
{
    if(word_count == 1)
    {
        // Centre aligned
        size_t total_spaces = line_length - strlen(words[0]);
        size_t left_spaces = total_spaces / 2 + (total_spaces % 2);
        size_t right_spaces = total_spaces / 2;
        for(int i = 0; i < left_spaces; i ++)
        {
            printf(" ");
        }
        printf("%s", words[0]);
        for(int i = 0; i < right_spaces; i ++)
        {
            printf(" ");
        }
        printf("\n");
        return;
    }

    // Align both ends
    size_t total_chars = 0;
    for(int i = 0; i < word_count; i ++)
    {
        total_chars += strlen(words[i]);
    }

    size_t total_spaces = line_length - total_chars;
    int gaps = word_count - 1;
    size_t space_per_gap = total_spaces / gaps;
    size_t extra_spaces = total_spaces % gaps;

    for(int i = 0; i < word_count; i ++)
    {
        printf("%s", words[i]);
        if(i < word_count - 1)
        {
            size_t spaces_to_print = space_per_gap;
            if(extra_spaces > 0)
            {
                spaces_to_print += 1;
                extra_spaces -= 1;
            }
            for(int s = 0; s < spaces_to_print; s ++)
            {
                printf(" ");
            }
        }
    }
    printf("\n");
}

/**
 * Searches for a hyphen character within a word up to a maximum index.
 *
 * This function scans the input word up to `max_index` and returns the index of the last hyphen
 * found within that range.
 *
 * @param word The word in which to search for a hyphen.
 * @param max_index The maximum index up to which to search for a hyphen.
 *
 * @return The index of the hyphen if found within the range.
 *         -1 if no hyphen is found within the specified range.
 * */
int find_hyphen(const char *word, int max_index)
{
    for(int i = max_index; i >= 0; i --)
    {
        if(word[i] == '-')
        {
            return i;
        }
    }
    return - 1;
}

/**
 * Frees all allocated memory for words.
 *
 * @param words The array of words.
 * @param word_count The number of words.
 * */
void free_words(char **words, int word_count)
{
    for(int i = 0; i < word_count; i ++)
    {
        if(words[i] != NULL)
        {
            free(words[i]);
        }
    }
    free(words);
}

/**
 * Function to print the remaining line before exiting with an error.
 *
 * This ensures that all processed lines are printed before the error message.
 *
 * @param current_line_words The array of words in the current line.
 * @param current_word_count The number of words in the current line.
 * @param line_length The maximum number of characters per line.
 * */
void print_remaining_line(char **current_line_words, int current_word_count,
                          int line_length)
{
    if(current_word_count > 0)
    {
        justify_line(current_line_words, current_word_count, line_length);
    }
}

/**
 * Processes the command-line input and reads the file content.
 *
 * @param argc The number of command-line arguments.
 * @param argv The array of command-line arguments.
 * @param line_length Pointer to store the line length.
 * @param content Pointer to store the file content.
 * @return 0 if successful, 1 if an error occurs.
 * */
int process_input(int argc, char *argv[], int *line_length, char **content)
{
    // Verify input arguments
    if(verify_input(argc, argv, line_length) != 0)
    {
        return 1;
    }

    // Open and check the input file
    FILE *file = fopen(argv[2], "rb");
    if(file == NULL)
    {
        fprintf(stderr, ERROR_INVALID_FILE, argv[2]);
        return 1;
    }

    // Read the file content
    *content = read_file(file);
    fclose(file);
    if(*content == NULL)
    {
        fprintf(stderr, ERROR_READING_FILE, argv[2]);
        return 1;
    }

    return 0;
}

/**
 * Formats the text content and handles the justification.
 *
 * @param content The text content to format.
 * @param line_length The maximum number of characters per line.
 * @return 0 if successful, 1 if an error occurs.
 * */
int format_text(char *content, int line_length)
{
    char **words = NULL;
    int word_count = 0;
    int words_capacity = 0;
    split_words(content, &words, &word_count, &words_capacity);

    char **current_line_words = malloc(100 * sizeof(char *));
    if(current_line_words == NULL)
    {
        fprintf(stderr, ERROR_MEMORY_ALLOCATION_FAILED);
        free_words(words, word_count);
        return 1;
    }

    int current_word_count = 0;
    int current_line_capacity = 100;
    size_t current_line_length = 0;
    int error_flag = 0;

    // Process each word
    for(int word_index = 0; word_index < word_count; word_index ++)
    {
        if(words[word_index] == NULL)
        {
            fprintf(stderr, ERROR_NULL_WORD, word_index);
            continue;
        }

        // Check if the current word can be added to the current line
        size_t word_len = strlen(words[word_index]);

        // Handle words longer than the line length
        while(word_len > line_length)
        {
            // Find the hyphen within the allowed range
            int hyphen_index = find_hyphen(words[word_index], line_length - 1);
            if(hyphen_index > 0)
            {
                // Split the word into two parts, including the hyphen in the first part
                char *first_part = malloc(
                        (hyphen_index + 2) * sizeof(char)); // +1 for hyphen, +1 for '\0'
                if(first_part == NULL)
                {
                    fprintf(stderr, ERROR_MEMORY_ALLOCATION_FAILED);
                    error_flag = 1;
                    break;
                }
                strncpy(first_part, words[word_index], hyphen_index + 1);
                first_part[hyphen_index + 1] = '\0';

                // The second part
                char *second_part = strdup(&words[word_index][hyphen_index + 1]);
                if(second_part == NULL)
                {
                    fprintf(stderr, ERROR_MEMORY_ALLOCATION_FAILED);
                    free(first_part);
                    error_flag = 1;
                    break;
                }

                // Replace the current word with the second part
                free(words[word_index]);
                words[word_index] = second_part;
                word_len = strlen(words[word_index]);

                // Add the first part to the current line
                if(current_line_length + strlen(first_part) +
                   (current_word_count > 0 ? 1 : 0) > line_length)
                {
                    justify_line(current_line_words, current_word_count, line_length);
                    current_word_count = 0;
                    current_line_length = 0;
                }

                current_line_words[current_word_count ++] = first_part;
                current_line_length +=
                        strlen(first_part) + (current_word_count > 1 ? 1 : 0);
            } else
            {
                error_flag = 1;
                break;
            }
        }

        if(error_flag)
        {
            break;
        }

        // Check again
        if(word_len > line_length)
        {
            // Still too long and cannot be split
            error_flag = 1;
            break;
        }

        int extra_space = (current_word_count > 0) ? 1 : 0;

        if(current_line_length + extra_space + word_len <= line_length)
        {
            if(current_word_count > 0)
            {
                current_line_length += 1;
            }

            if(current_word_count >= current_line_capacity)
            {
                current_line_capacity *= 2;
                char **temp = realloc(current_line_words,
                                      current_line_capacity * sizeof(char *));
                if(temp == NULL)
                {
                    fprintf(stderr, ERROR_MEMORY_ALLOCATION_FAILED);
                    error_flag = 1;
                    break;
                }
                current_line_words = temp;
            }

            current_line_words[current_word_count ++] = words[word_index];
            current_line_length += word_len;
        } else
        {
            justify_line(current_line_words, current_word_count, line_length);
            current_line_length = 0;
            current_word_count = 0;

            current_line_words[current_word_count ++] = words[word_index];
            current_line_length += word_len;
        }
    }

    print_remaining_line(current_line_words, current_word_count, line_length);

    if(error_flag)
    {
        printf(ERROR_WORD_PROCESSOR);
    }

    free_words(words, word_count);
    free(current_line_words);

    return (error_flag ? 1 : 0);
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
 * - Split text into words
 * - Align both ends of the text
 * - Handle hyphenated words
 * - Error handling for long lines
 *
 * @param argc The number of arguments passed to the program.
 * @param argv An array of strings representing the arguments.
 *        argv[0] - the name of the executable
 *        argv[1] - the maximum number of characters per line (line_length)
 *        argv[2] - the input file name
 *
 * @return 0 on successful execution.
 *         1 if the number of arguments is incorrect or an error occurs.
 * */
int main(int argc, char *argv[])
{
    int line_length;
    char *content = NULL;

    // Process input arguments and read file content
    if(process_input(argc, argv, &line_length, &content) != 0)
    {
        return 1;
    }

    // Format and justify the text
    int result = format_text(content, line_length);

    free(content);
    return result;
}
