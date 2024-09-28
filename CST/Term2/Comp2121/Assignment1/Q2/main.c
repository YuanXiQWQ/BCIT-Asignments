#include <stdio.h>

int main(void) {
    int counter = 100;
    int n = 50;
    for(int i = 4; i <= n + 3; i++) {
        counter += 11;
        for(int j = i + 1; j <= 3 * n + 15; j++) {
            counter += 22;
            for(int k = j + 1; k <= n + 8; k++) {
                counter += 33;
            }
        }
    }
    printf("%d\n", counter);
    return 0;
}
