import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public class Main {

	
	public static void main(String[] args) {
		
		ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		BlockingQueue<String> q = new SynchronousQueue<>();
		
		Read d = new Read("data.txt",q);
		Konzument k = new Konzument(q);
		
		Thread a = new Thread(d);
		Thread b = new Thread(k);
		
		
		
		ex.submit(a,b);
		
	}
}
