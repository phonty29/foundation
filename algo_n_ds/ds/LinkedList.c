#include <stdio.h>
#include <stdlib.h>


// integer value implementation
typedef struct LinkedList {
    int value;
    struct LinkedList *next;
} LinkedList;


LinkedList *build_LinkedList(int *values, int length) {
    LinkedList *head = NULL;
    for (int i = length-1; i >= 0; i--) {
        struct LinkedList *node;    

        node = malloc(sizeof(*node));
        if (node == NULL) {
            printf("Error: memory allocation failed\n");
            exit(1);
        }
        node->value=*(values+i);
        node->next = head;

        head = node;
    }

    return head;
}


void print_LinkedList(LinkedList *head) {
    if (head == NULL) {
        printf("Error: head is empty\n");
        exit(1);
    }

    int counter = 1; 
    while (head != NULL) {
        if (head->next != NULL)
            printf("Linked List #%d: { value = %d, next = %p }\n", counter, head->value, head->next);
        else 
            printf("Linked List #%d: { value = %d, 'TAIL' }\n", counter, head->value);
        head = head->next;          
        counter++;
    }
}


void free_LinkedList(LinkedList *head) {
    LinkedList *temp_node;
    while (head != NULL) {
        temp_node = head;
        head = head->next;
        free(temp_node);
    }
}


int main(int argc, char *argv[]) {
    int values[] = {1, 2, 3, 4, 5};
    int length = sizeof(values)/sizeof(*values);

    LinkedList *head = build_LinkedList(values, length);
    print_LinkedList(head);
    free_LinkedList(head);
    return 0;
}