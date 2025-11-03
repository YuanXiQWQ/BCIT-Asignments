#pragma once

#include <vector>
#include <pthread.h>

class SimpleThread;

class RaceStatus{
    friend class SimpleThread;

public:
    explicit RaceStatus(int maxDistance);

    ~RaceStatus();

    void addMe(SimpleThread *thread);

    void showRace();

    void block(int loserThread);

    void unblock();

private:
    std::vector<SimpleThread *> threads;
    int maxDistance;
    pthread_mutex_t mutex;
    pthread_cond_t cond;
};
