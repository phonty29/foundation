#include <stdio.h>
#include <stdlib.h>


// Implementation for integer values
struct ArrayList {
    int *values;
    int size;
    int _alloc_size;
};


static void add(int in_val, struct ArrayList *arr_l) {
    if (arr_l->size == arr_l->_alloc_size) {
        arr_l->_alloc_size *= 2;
        arr_l->values = realloc(arr_l->values, arr_l->_alloc_size*sizeof(int));
        if (arr_l->values == NULL) {
            printf("Error: failed to reallocate memory\n");
            exit(1);
        }
    }
    arr_l->values[arr_l->size] = in_val;
    arr_l->size += 1;
}


static void print_ArrayList(struct ArrayList *arr_l) {
    int size = arr_l->size;
    printf("ArrayList: [ ");
    for (int i = 0; i < size; i++) {
        if (i == size-1)
            printf("%d", arr_l->values[i]);
        else
            printf("%d, ", arr_l->values[i]);
    } 
    printf(" ]\n");
}


static struct ArrayList build_ArrayList() {
    struct ArrayList arr_l = { NULL, 0, 10 };
    arr_l.values = (int*) malloc(arr_l._alloc_size * sizeof(int));
    if (arr_l.values == NULL) 
        printf("Error: failed to allocate memory for ArrayList");
    return arr_l;
}


static void free_ArrayList(struct ArrayList *arr_l) {
    free(arr_l->values);
    arr_l->values = NULL;
}


int main(int argc, char *argv[]) {
    struct ArrayList arr_l = build_ArrayList();

    add(1, &arr_l); 
    add(2, &arr_l); 
    add(3, &arr_l); 
    print_ArrayList(&arr_l);
}
