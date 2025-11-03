#include "SimpleThread.h"
#include "RaceStatus.h"
#include <unistd.h>
#include <stdlib.h>

SimpleThread::SimpleThread(RaceStatus *raceStatus) : Thread(this){
    this->raceStatus = raceStatus;
    this->curDistance = 0;
    this->raceStatus->addMe(this);
}

void SimpleThread::run(){
    for(int i = 0; i < 10000; i ++){
        usleep((useconds_t) ((rand() % 100) * 1000));
        curDistance ++;
    }
}

int SimpleThread::getCount() const{ return curDistance; }

SimpleThread::~SimpleThread(){}
