#include "SimpleThread.h"
#include "RaceStatus.h"
#include <unistd.h>
#include <stdlib.h>

SimpleThread::SimpleThread(RaceStatus *raceStatus) : Thread(this){
    this->raceStatus = raceStatus;
    this->curDistance = 0;
    this->shouldWait = false;
    this->raceStatus->addMe(this);
}

void SimpleThread::makeItWait(){ shouldWait = true; }

void SimpleThread::run(){
    for(int i = 0; i < 10000; i ++){
        usleep((useconds_t) ((rand() % 100) * 1000));
        curDistance ++;
        if(shouldWait){
            shouldWait = false;
            pthread_mutex_lock(&raceStatus->mutex);
            pthread_cond_wait(&raceStatus->cond, &raceStatus->mutex);
            pthread_mutex_unlock(&raceStatus->mutex);
        }
    }
}

int SimpleThread::getCount() const{ return curDistance; }

SimpleThread::~SimpleThread(){}
