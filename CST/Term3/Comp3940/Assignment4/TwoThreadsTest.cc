#include <pthread.h>
#include <unistd.h>
#include <stdio.h>

void * doit(void * arg) {
  char* msg = (char *) (arg);
  for (int i= 0; i < 10; i++) {
     printf("%s\n", msg);
     sleep(1);
  }
}

main() {
  pthread_t tid;
  int *ret;
  pthread_create(&tid, NULL, doit, (void*)"Jamaica");
  pthread_create(&tid, NULL, doit, (void*)"Fiji");
  pthread_join(tid, (void **)&ret);
}
