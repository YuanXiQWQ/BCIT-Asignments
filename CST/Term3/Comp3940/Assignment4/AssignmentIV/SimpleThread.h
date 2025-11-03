#pragma once

#include "Thread.h"

class RaceStatus;

class SimpleThread : public Thread{
public:
    explicit SimpleThread(RaceStatus *raceStatus);

    void run();

    ~SimpleThread();

    int getCount() const;

private:
    RaceStatus *raceStatus;
    int curDistance;
};
