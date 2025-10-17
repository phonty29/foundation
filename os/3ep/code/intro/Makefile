all: cpu memo thread io

clean:
	rm -f cpu memo thread io

cpu: cpu.c common.h
	gcc -o cpu cpu.c -Wall

memo: memo.c common.h
	gcc -o memo memo.c -Wall

thread: thread.c common.h common_threads.h
	gcc -o thread thread.c -Wall -pthread

io: io.c common.h
	gcc -o io io.c -Wall