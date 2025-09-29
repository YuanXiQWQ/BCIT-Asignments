CC = gcc
CFLAGS = -Wall -g
TARGET = tic_tac_toe
OBJS = main.o tic_tac_toe.o

all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS)

main.o: main.c tic_tac_toe.h
	$(CC) $(CFLAGS) -c main.c

tic_tac_toe.o: tic_tac_toe.c tic_tac_toe.h
	$(CC) $(CFLAGS) -c tic_tac_toe.c


tic_tac_toe.c:
clean:
	rm -f $(TARGET) $(OBJS)
