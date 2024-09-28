#include <stdio.h>
#include <stdlib.h>


// integer value implementation
struct LinkedList {
    int value;
    struct LinkedList *next;
};


struct LinkedList *build_LinkedList(int *values, int length) {
    struct LinkedList *head = NULL;
    for (int i = length-1; i >= 0; i--) {
        struct LinkedList *node; 

        node = malloc(sizeof(*node));
        node->value=*(values+i);
        node->next = head;

        head = node;
    }
}


void print_LinkedList(struct LinkedList *ll) {
    if (ll == NULL) {
        printf("Head is empty\n");
    }

    char hasNext = 1;
    int counter = 1; 
    while (hasNext) {
        printf("Linked List #%d: { ", counter);
        printf("value %d, ", ll->value);
        if (ll->next == NULL) {
            printf(", 'TAIL' }\n");
            hasNext = 0;
        } else {
            printf(", next = %p }\n", ll->next);
            ll = ll->next; 
            counter+=1;
        }
    }
}


int main(int argc, char *argv) {
    int values[] = {1, 2, 3, 4, 5};
    int length = sizeof(values)/sizeof(*values);

    struct LinkedList *ll = build_LinkedList(values, length);
    print_LinkedList(ll);
    return 0;
}
