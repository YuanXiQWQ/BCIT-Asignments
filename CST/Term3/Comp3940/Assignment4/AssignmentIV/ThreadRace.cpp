#include "RaceStatus.h"
#include "SimpleThread.h"
#include <unistd.h>

int main(){
    RaceStatus raceStatus(500);
    for(int i = 0; i < 3; ++ i){ (new SimpleThread(&raceStatus))->start(); }
    raceStatus.block(1);
    sleep(4);
    raceStatus.unblock();
    while(true){
        raceStatus.showRace();
        sleep(5);
    }
    return 0;
}
