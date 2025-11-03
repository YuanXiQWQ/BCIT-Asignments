#pragma once

#include <vector>

class SimpleThread;

class RaceStatus{
public:
    explicit RaceStatus(int maxDistance);

    void addMe(SimpleThread *thread);

    void showRace();

private:
    std::vector<SimpleThread *> threads;
    int maxDistance;
};
