import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.TimeUnit;

public class Main {
	//fonta kam se ukladaji nactene hodnoty ye souboru
		static	BlockingQueue<String> q = new LinkedBlockingQueue<>();
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		//exekutor kde se nastavi pocet jader pouzivanych pro praci 
		ExecutorService ex = Executors.newFixedThreadPool(2);
		
		
		
		//trida ktera cte ze souboru
		Read d = new Read("Data2.txt",q);
		
		//trida ktera vybira ulozene veci ze souboru
		TakeFromQueue k = new TakeFromQueue(q);
		
		//spustine obou trid
		ex.execute(d);
		
		//spusteni tridy implementujici callable
		Future<Integer> future2 = ex.submit(k);
		
		//cekani na navratovou hodnotu
		//nacteni cele fronty
		Integer a = future2.get();
		
		
		
		
		
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
