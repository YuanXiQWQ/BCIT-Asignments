#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <limits.h>

// Function to perform logical addition using bitwise operations
int64_t logicaladder(int64_t a, int64_t b, int bitwidth){
    // Create a mask based on bitwidth
    uint64_t mask = (bitwidth >= 64) ? ~ 0ULL : ((1ULL << bitwidth) - 1);

    // Apply mask to both numbers to confine them within bitwidth
    uint64_t ua = a & mask;
    uint64_t ub = b & mask;

    // Perform addition using bitwise operations
    while(ub != 0){
        uint64_t carry = ua & ub;
        ua = ua ^ ub;
        ub = (carry << 1) & mask;
    }

    // Convert back to signed integer based on bitwidth
    int64_t sum;
    // If the sign bit is set, interpret as negative number
    if(bitwidth < 64 && (ua & (1ULL << (bitwidth - 1)))){
        sum = (int64_t)(ua | (~ mask));
    } else{
        sum = (int64_t) ua;
    }

    return sum;
}

// Function to detect overflow and perform right shift if necessary
int64_t detectoverflow(int64_t a, int64_t b, int64_t sum, int bitwidth, int shift_bits,
                       int *overflow_flag){
    *overflow_flag = 0;

    // Detect overflow based on sign bits
    if(((a >= 0 && b >= 0) && sum < 0) ||
       ((a < 0 && b < 0) && sum >= 0)){
        *overflow_flag = 1;
        // Perform arithmetic right shift
        sum = sum >> shift_bits;
    }

    return sum;
}

int main(int argc, char *argv[]){
    if(argc != 5){
        fprintf(stderr, "Usage: %s <bitwidth> <number1> <number2> <shift_bits>\n",
                argv[0]);
        return EXIT_FAILURE;
    }

    // Parse and validate bitwidth
    int bitwidth = atoi(argv[1]);
    if(bitwidth != 8 && bitwidth != 16 && bitwidth != 32 && bitwidth != 64){
        fprintf(stderr, "Error: Bitwidth must be one of 8, 16, 32, or 64.\n");
        return EXIT_FAILURE;
    }

    // Parse number1
    char *endptr;
    long long temp_num1 = strtoll(argv[2], &endptr, 10);
    if(*endptr != '\0'){
        fprintf(stderr, "Error: Invalid input for number1.\n");
        return EXIT_FAILURE;
    }

    // Parse number2
    long long temp_num2 = strtoll(argv[3], &endptr, 10);
    if(*endptr != '\0'){
        fprintf(stderr, "Error: Invalid input for number2.\n");
        return EXIT_FAILURE;
    }

    // Parse shift_bits
    int shift_bits = atoi(argv[4]);
    if(shift_bits < 0){
        fprintf(stderr, "Error: Shift bits must be non-negative.\n");
        return EXIT_FAILURE;
    }

    // Cast numbers to int64_t
    int64_t num1 = (int64_t) temp_num1;
    int64_t num2 = (int64_t) temp_num2;

    // Perform logical addition
    int64_t sum = logicaladder(num1, num2, bitwidth);

    // Detect overflow
    int overflow_flag = 0;
    int64_t final_result = detectoverflow(num1, num2, sum, bitwidth, shift_bits,
                                          &overflow_flag);

    // Display results based on overflow
    if(overflow_flag){
        printf("Result after right shift: %lld\n", final_result);
        printf("Overflow detected within the specified bitwidth.\n");
    } else{
        printf("Result of addition: %lld\n", final_result);
    }

    return EXIT_SUCCESS;
}
