import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class VirtualThreadApp {
	public static void main(String... args) throws Exception {
		runTwoVirtualThreadsAtTheSameTime();
	}

	static void runTwoVirtualThreadsAtTheSameTime() {
		final var virtualThreadfactory = Thread.ofVirtual().factory();
		try (var executor = Executors.newThreadPerTaskExecutor(virtualThreadfactory)) {
			executor.submit(() -> {
				try {
					print(fetchURL().get());
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			executor.submit(() -> {
				try {
					print(fetchURL().get());
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
	}

	static void print(String result) {
		System.out.println("Thread:" + Thread.currentThread() + " - Result: " + result);
	}

	static java.util.concurrent.CompletableFuture<String> fetchURL() {
		return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(1000); // Simulate network delay
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "Fetched Data";
		});
	}
}
