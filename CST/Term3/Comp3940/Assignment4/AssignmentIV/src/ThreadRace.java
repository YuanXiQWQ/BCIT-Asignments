import java.util.ArrayList;
import java.io.*;

public class ThreadRace {

    public static void main(String[] args)
    {

        RaceStatus raceStatus = new RaceStatus(500);

        for(int i = 0; i < 3; i++)
        {
            new SimpleThread(raceStatus).start();
        }

        raceStatus.block(1);
        try
        {
            Thread.sleep((long) (4000));
        } catch(InterruptedException e)
        {
        }
        raceStatus.unblock();
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

    public void block(int loserThread)
    {
        if(loserThread >= 0 && loserThread < threads.size())
        {
            ((SimpleThread) threads.get(loserThread)).makeItWait();
        }
    }

    public void unblock()
    {
        synchronized(this)
        {
            try
            {
                this.notifyAll();
            } catch(Exception e)
            {
            }
        }
    }
}

class SimpleThread extends Thread {
    private RaceStatus raceStatus = null;
    private int curDistance = 0;
    private boolean shouldWait = false;

    public SimpleThread(RaceStatus raceStatus)
    {
        this.raceStatus = raceStatus;
        raceStatus.addMe(this);
    }

    public int getCount()
    {
        return curDistance;
    }

    public void makeItWait() {shouldWait = true;}

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
            if(shouldWait)
            {
                shouldWait = false;
                synchronized(raceStatus)
                {
                    try
                    {
                        raceStatus.wait();
                    } catch(Exception e)
                    {
                    }
                }
            }
        }
    }
}
