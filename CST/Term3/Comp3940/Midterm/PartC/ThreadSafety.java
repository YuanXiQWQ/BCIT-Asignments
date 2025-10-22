public class ThreadSafety implements Runnable {
    int shared = 0;
    public static void main(String[] args) {
        ThreadSafety ts = new ThreadSafety();
        Thread t1 = new Thread(ts, "T1");
        t1.start();
        Thread t2 = new Thread(ts, "T2");
        t2.start();
    }
    @Override
    public void run() {
        // 由于使用了 synchronized(this)，两个线程无法同时进入，执行是顺序化的，T1进入时持有该对象的锁，T2必须等锁释放
        synchronized(this) {
            // 让 T1 等待，直到 T2 完成后唤醒它
            if(Thread.currentThread().getName().contains("T1"))
            {
                try
                {
                    this.wait();
                } catch(InterruptedException e) {/**/}
            }
            int copy = shared;
            /*执行方法*/
            // T2 完成后通知等待的线程（即 T1）
            if(Thread.currentThread().getName().contains("T2")) this.notifyAll();
        }

    }
}
