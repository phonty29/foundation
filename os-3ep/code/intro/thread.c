#include <stdlib.h>
#include <stdio.h>
#include "common_threads.h"
#include "common.h"

int loops;
volatile int counter = 0;

void *worker(void *args) {
    int i;
    for (i=0; i<loops; i++) {
        counter++;
    }    
    return NULL;
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "usage: threads <loops>");
        exit(1);
    }

    loops = atoi(argv[1]);

    printf("Initial counter value is %d\n", counter);
    pthread_t pt1, pt2;
    Pthread_create(&pt1, NULL, worker, NULL);
    Pthread_create(&pt2, NULL, worker, NULL);
    Pthread_join(pt1, NULL);
    Pthread_join(pt2, NULL);
    printf("Final counter value is %d\n", counter);
    return 0;
}