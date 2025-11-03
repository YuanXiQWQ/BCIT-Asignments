#include "RaceStatus.h"
#include "SimpleThread.h"
#include <iostream>
#include <cstdlib>

RaceStatus::RaceStatus(int maxDistance) : maxDistance(maxDistance){
    pthread_mutex_init(&mutex, NULL);
    pthread_cond_init(&cond, NULL);
}

RaceStatus::~RaceStatus(){
    pthread_cond_destroy(&cond);
    pthread_mutex_destroy(&mutex);
}

void RaceStatus::addMe(SimpleThread *thread){ threads.push_back(thread); }

void RaceStatus::showRace(){
    int inFront = 0;
    for(size_t i = 0; i < threads.size(); ++ i){
        int cnt = threads[i]->getCount();
        if(cnt >= inFront){ inFront = cnt; }
        std::cout << "Thread #:" << (i + 1) << " at:" << cnt << "   ";
    }
    std::cout << std::endl;
    if(inFront >= maxDistance){
        std::cout << "Race Over !!!" << std::endl;
        std::exit(0);
    }
}

void RaceStatus::block(int loserThread){
    if(loserThread >= 0 && (size_t) loserThread < threads.size()){
        threads[loserThread]->makeItWait();
    }
}

void RaceStatus::unblock(){
    pthread_mutex_lock(&mutex);
    pthread_cond_broadcast(&cond);
    pthread_mutex_unlock(&mutex);
}
