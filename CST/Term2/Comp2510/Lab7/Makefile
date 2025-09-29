# Makefile for Lab7

CC = gcc
CFLAGS = -Wall -Wextra -std=c11 -g
TARGET = lab7
SRCS = main.c student.c
HEADERS = student.h
OBJS = $(SRCS:.c=.o)

all: $(TARGET)
$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS)

%.o: %.c $(HEADERS)
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJS) $(TARGET)

.PHONY: all clean