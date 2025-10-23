CC=gcc
CFLAGS=-O0 -Wall -g
BINS=event_loop

.PHONY: all clean

all: $(BINS)

%.o: %.c
	$(CC) $(CFLAGS) $^ -c

event_loop: event_loop.o server.o
	$(CC) $(LDFLAGS) $^ -o $@

clean:
	rm -rf *.o $(BINS) $(TESTS)