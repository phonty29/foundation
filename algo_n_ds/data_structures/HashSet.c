// Online C compiler to run C program online
#include <stdio.h>
#include <stdlib.h>

struct HashSet {
    int *values;
    int size;
    int _alloc_size;
};


static struct HashSet new_HashSet() {
    struct HashSet hs;
    hs._alloc_size = 10;
    hs.values = (int*) malloc(hs._alloc_size * sizeof(int));
    if (hs.values == NULL) {
         printf("Error: failed to allocate memory for HashSet\n");
         exit(1);
    }
    return hs;
}


static void HashSet_add(struct HashSet *hs, int in_val) {
    for (int i = 0; i < hs->size; i++) {
        if (hs->values[i] == in_val)
            return;
    }
        
    
    if (hs->size == hs->_alloc_size) {
        hs->_alloc_size *= 2;
        hs->values = realloc(hs->values, hs->_alloc_size * sizeof(int));
    }
    hs->values[hs->size] = in_val;
    hs->size += 1;
}


static void HashSet_print(struct HashSet *hs) {
    if (hs == NULL) {
        printf("Error: provided HashSet is null\n");
        exit(1);
    }
    printf("HashSet values are: [ ");
    for (int i = 0; i < hs->size; i++) {
        if (i == hs->size - 1) 
            printf("%d ]\n", hs->values[i]);
        else 
            printf("%d, ", hs->values[i]);
    }
}

static void HashSet_free(struct HashSet *hs) {
    free(hs->values);
    hs->values = NULL;
}


int main() {
    struct HashSet hs = new_HashSet();
    
    HashSet_add(&hs, 0);
    HashSet_add(&hs, 1);
    HashSet_add(&hs, 2);
    HashSet_add(&hs, 3);
    HashSet_add(&hs, 4);
    HashSet_add(&hs, 5);
    HashSet_add(&hs, 6);
    HashSet_add(&hs, 4);
    HashSet_add(&hs, 5);
    HashSet_add(&hs, 6);    
    HashSet_add(&hs, 7);
    HashSet_print(&hs);
    HashSet_free(&hs);
    return 0;
}
