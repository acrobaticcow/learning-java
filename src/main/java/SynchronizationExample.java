
import java.util.concurrent.Executors;

class SharedResource {
	private int counter = 0;

	public synchronized void increment() {
		counter++;
	}

	public int getCounter() {
		return counter;
	}
}

public class SynchronizationExample {
	public static void main(String[] args) {
		SharedResource resource = new SharedResource();

		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				resource.increment();
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				resource.increment();
			}
		});

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Counter value: " + resource.getCounter());
	}
}
