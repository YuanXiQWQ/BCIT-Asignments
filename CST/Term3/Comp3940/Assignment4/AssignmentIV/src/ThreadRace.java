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

class RaceStatus {
    private ArrayList<Thread> threads = null;
    private int maxDistance = 0;


    public RaceStatus(int maxDistance)
    {
        threads = new ArrayList<Thread>();
        this.maxDistance = maxDistance;
    }


    public void addMe(Thread thread)
    {
        threads.add(thread);
    }


    public void showRace()
    {
        int inFront = 0;
        for(int i = 0; i < threads.size(); i++)
        {
            if(((SimpleThread) threads.get(i)).getCount() >= inFront)
            {
                inFront = ((SimpleThread) threads.get(i)).getCount();
            }
            System.out.print("Thread #:" + (i + 1) + " at:" +
                    ((SimpleThread) threads.get(i)).getCount() + "   ");
        }
        System.out.println("");
        if(inFront >= maxDistance)
        {
            System.out.println("Race Over !!!");
            System.exit(0);
        }
    }
}
