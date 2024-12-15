#include <malloc.h>


int *p = (int *)malloc(5 * sizeof(int));
if (p == NULL) {
printf("Memory allocation failed.");
return 1;
}
