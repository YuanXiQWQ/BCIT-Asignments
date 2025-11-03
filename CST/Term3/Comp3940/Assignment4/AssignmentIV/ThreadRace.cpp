#include "RaceStatus.h"
#include "SimpleThread.h"
#include <unistd.h>

int main(){
    RaceStatus raceStatus(500);
    (new SimpleThread(&raceStatus))->start();
    (new SimpleThread(&raceStatus))->start();
    while(true){
        raceStatus.showRace();
        sleep(5);
    }
    return 0;
}
