public class SimpleThread extends Thread {
	public SimpleThread(String str) {super(str);}

    @Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i + " " + getName());
			try {
				sleep((long)(Math.random() * 1000));
			} catch (InterruptedException e) {/*忽略*/}
		}
		System.out.println("DONE! " + getName());
	}
}
