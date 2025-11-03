#include "RaceStatus.h"
#include "SimpleThread.h"
#include <iostream>
#include <cstdlib>

RaceStatus::RaceStatus(int maxDistance) : maxDistance(maxDistance){}

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
