import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class Main {

	
	public static void main(String[] args) {
		
		ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		BlockingQueue<String> q = new LinkedBlockingQueue<>();
		
		Read d = new Read("Data2.txt",q);
		Konzument k = new Konzument(q);
		
		
		
		ex.execute(d);
		ex.execute(k);
		
		
		
		
		try {
		    System.out.println("attempt to shutdown executor");
		    ex.shutdown();
		    ex.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!ex.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    ex.shutdownNow();
		    System.out.println("shutdown finished");
		}

		
	}
}
