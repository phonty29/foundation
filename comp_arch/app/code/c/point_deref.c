#include <stdio.h>


long exchange(long *xp, long y) {
    long x = *xp;
    printf("&x = %p, x = %ld\n", &x, x);
    printf("xp = %p, *xp = %ld\n", xp, *xp);
    *xp = y;
    printf("&y = %p, y = %ld\n", &y, y);
    printf("xp = %p, *xp = %ld\n", xp, *xp);
    return x;
}


int main(int argc, char *argv) {
   long x = 10;
   long y = exchange(&x, 20);
   printf("x = %ld, xp = %p, y = %ld, yp = %p\n", x, &x, y, &y);
}
