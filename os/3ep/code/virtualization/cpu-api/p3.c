#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
	printf("Hello, I am parent program with pid %d\n", (int) getpid());
	int rc = fork();
	if (rc < 0) {
		fprintf(stderr, "fork failed\n");
		exit(1);
	} else if (rc == 0) {
		printf("Hello, I am child process with pid %d\n", (int) getpid());
		char *myArgs[3];
		myArgs[0] = strdup("wc");
		myArgs[1] = strdup("p3.c");
		myArgs[2] = NULL;
		execvp(myArgs[0], myArgs);
		printf("this shouldn't print out");
	} else {
		int wc = wait(NULL);
		printf("I'm parent again, my child has pid %d, and I have pid %d. And my wc is %d\n", rc, (int) getpid(), wc);
	}
}
