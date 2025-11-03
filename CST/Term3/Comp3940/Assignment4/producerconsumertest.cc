#include <pthread.h>
#include <unistd.h>
#include <stdio.h>

static pthread_mutex_t mutex;
static pthread_cond_t cond;
static bool available = false;

void * consumer(void * arg) {
  int* cubbyhole = (int *) arg;
  for (int i= 0; i < 10; i++) {
     pthread_mutex_lock(&mutex);
     while(!available)
        pthread_cond_wait(&cond, &mutex);
     printf("get %d\n", *cubbyhole);
     available = false;
     pthread_mutex_unlock(&mutex);
     pthread_cond_signal(&cond);
  }
}
void * producer(void * arg) {
  int*  cubbyhole = (int *) arg;
  for (int i= 0; i < 10; i++) {
     pthread_mutex_lock(&mutex);
     while(available)
        pthread_cond_wait(&cond, &mutex);
     *cubbyhole = i;
     printf("put %d\n", *cubbyhole);
     available = true;
     pthread_mutex_unlock(&mutex);
     pthread_cond_signal(&cond);
  }
}

main() {
  pthread_t tid;
  int *ret;
  int cubbyhole;
  pthread_create(&tid, NULL, producer, (void*) &cubbyhole);
  pthread_create(&tid, NULL, consumer, (void*) &cubbyhole);
  pthread_join(tid, (void **)&ret);
}
