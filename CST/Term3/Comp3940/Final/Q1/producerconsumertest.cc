#include <pthread.h>
#include <unistd.h>
#include <stdio.h>

static pthread_mutex_t mutex;
static pthread_cond_t cond;
static bool available = false;

void * consumer(void * arg) {
  int* cubbyhole = (int *) arg;
  for (int i= 0; i < 10; i++) {
     pthread_mutex_lock(&mutex);            // 先加锁
     while(!available)
        pthread_cond_wait(&cond, &mutex);   // 如果没货，等
     printf("get %d\n", *cubbyhole);        // 有货后读取
     available = false;                     // 设置为没货
     pthread_mutex_unlock(&mutex);          // 解锁
     pthread_cond_signal(&cond);            // 通知生产者
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

// 修改后的 main
int main() {
    pthread_t prod_tid, cons_tid;
    int cubbyhole;

    pthread_create(&prod_tid, NULL, producer, &cubbyhole);
    pthread_create(&cons_tid, NULL, consumer, &cubbyhole);

    pthread_join(prod_tid, NULL);
    pthread_join(cons_tid, NULL);
    return 0;
}
