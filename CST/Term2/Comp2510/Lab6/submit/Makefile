# Makefile for COMP2510 Lab6

CC = gcc
CFLAGS = -Wall -Wextra -std=c11
DEPS = student.h
OBJ = main.o student.o

# Default target
all: lab6

# Link the executable
lab6: $(OBJ)
	$(CC) $(CFLAGS) -o lab6 $(OBJ)

# Compile main.c
main.o: main.c $(DEPS)
	$(CC) $(CFLAGS) -c main.c

# Compile student.c
student.o: student.c $(DEPS)
	$(CC) $(CFLAGS) -c student.c

# Clean up build files
clean:
	rm -f *.o lab6
