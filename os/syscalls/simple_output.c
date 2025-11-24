#include <unistd.h>
#include <sys/syscall.h> // For system call numbers
#include <stdio.h>

int main() {
	// Example: Using syscall to get the process ID (getpid)
	long pid = syscall(SYS_getpid); 
	printf("Process ID: %ld\n", pid);

	// Example: Using syscall to write to standard output (write)
	const char *message = "Hello, syscall!\n";
	syscall(SYS_write, 1, message, 16); // fd 1 is stdout, 16 is length

	return 0;
}
