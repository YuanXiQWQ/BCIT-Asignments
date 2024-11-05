#include <stdio.h>

int q2(int n){
    int time = 1000;
    for(int i = 0; i < n; i ++){
        time += 9;
        for(int k = 5; k <= i + 6; k ++){
            time += 12 * k;
            time += 4;
        }
    }
    return time;
}

int q3(int n){
    int time = 1000;
    for(int i = 0; i < n; i ++){
        time += 9;
        for(int k = 14; k <= i + 6; k ++){
            time += 12 * k;
        }
    }
    return time;
}

int main(){
    int n = 50;
    printf("q2 result when n = %d: %d\n", n, q2(n));
    printf("q3 result when n = %d: %d\n", n, q3(n));
    return 0;
}