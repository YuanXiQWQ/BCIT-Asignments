test: main.o array_utils.o

	gcc -o test main.o array_utils.o

%.o: %.c

	gcc -Wp,-MD,$@.d -c -o $@ $<

clean:

	rm *.o test