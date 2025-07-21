#include <unistd.h>
#include <string.h>

int main(){
    char buf[128];
    char rev[128];

    // Input
    ssize_t len = read(0, buf, 128);
    if(len <= 0){ return 0; }

    // Delete \n
    if(buf[len - 1] == '\n'){
        len --;
    }

    // Reverse
    ssize_t esi = 0;
    while (esi < len) {
        rev[esi] = buf[len - 1 - esi];
        esi++;
    }

    // Output
    write(1, rev, len);

    return 0;
}
