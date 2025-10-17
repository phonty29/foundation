#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main (int argc, char *argv[]) {
    printf("current (pid:%d)\n", (int) getpid());
    int rc = fork();
    if (rc < 0) {
        fprintf(stderr, "fork failed\n");
        exit(1);
    } else if (rc == 0) {
        printf("i am a child process (pid: %d)\n", (int) getpid());
        sleep(1);
    } else {
        int wc = wait(NULL);
        printf("i am a parent of process %d (wc: %d) (pid: %d)\n", rc, wc, (int) getpid());
    }
    return 0;
}