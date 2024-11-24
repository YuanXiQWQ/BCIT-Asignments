# Compiler
CC = gcc

# Compiler flags
CFLAGS = -Wall -Wextra -g

# Target executable
TARGET = circular_linkedlist

# Source files
SRCS = main.c circular_node.c

# Object files
OBJS = $(SRCS:.c=.o)

# Default rule
all: $(TARGET)

# Link object files to create the executable
$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS)

# Compile .c files to .o files
%.o: %.c circular_node.h
	$(CC) $(CFLAGS) -c $< -o $@

# Clean up generated files
clean:
	rm -f $(OBJS) $(TARGET)

.PHONY: all clean
