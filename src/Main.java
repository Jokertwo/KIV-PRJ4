import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.concurrent.TimeUnit;

public class Main {

	
	public static void main(String[] args) {
		
		//exekutor kde se nastavi pocet jader pouzivanych pro praci 
		ExecutorService ex = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		//fonta kam se ukladaji nactene hodnoty ye souboru
		BlockingQueue<String> q = new LinkedBlockingQueue<>();
		
		//trida ktera cte ze souboru
		Read d = new Read("Data2.txt",q);
		
		//trida ktera vybira ulozene veci ze souboru
		Konzument k = new Konzument(q);
		
		
		//spustine obou trid
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
