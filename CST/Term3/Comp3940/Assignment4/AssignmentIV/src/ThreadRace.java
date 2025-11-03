import java.util.ArrayList;
import java.io.*;

public class ThreadRace {
    public static void main(String[] args)
    {
        RaceStatus raceStatus = new RaceStatus(500);
        new SimpleThread(raceStatus).start();
        new SimpleThread(raceStatus).start();
        while(true)
        {
            raceStatus.showRace();
            try
            {
                Thread.sleep((long) (5000));
            } catch(InterruptedException e)
            {
            }
        }
    }
}

class SimpleThread extends Thread {
    private int curDistance = 0;

    public SimpleThread(RaceStatus raceStatus)
    {
        raceStatus.addMe(this);
    }

    public int getCount()
    {
        return curDistance;
    }

    @Override
    public void run()
    {
        for(int i = 0; i < 10000; i++)
        {
            try
            {
                sleep((long) (Math.random() * 100));
            } catch(InterruptedException e)
            {
            }
            curDistance++;
        }
    }
}
