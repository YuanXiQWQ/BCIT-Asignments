#include <stdio.h>

void q2(int n){
    int time = 1000;
    for(int i = 1; i <= n; i ++){
        time += 9;
        for(int k = 5; k <= i + 6; k ++){
            time += 12 * k;
            time += 4;
        }
    }
    printf("time = %d\n", time);
}

void q3(int n){
    int time = 1000;
    for(int i = 1; i <= n; i ++){
        time += 9;
        for(int k = 14; k <= i + 6; k ++){
            time += 12 * k;
        }
    }
    printf("time = %d\n", time);
}

int main(){
    int n = 50;
    q3(n);
    return 0;
}