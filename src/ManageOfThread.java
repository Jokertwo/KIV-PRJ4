import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



public class ManageOfThread {
	
	
	//fonta kam se ukladaji nactene hodnoty ye souboru
			private	BlockingQueue<String> q = null;
		
	//exekutor kde se nastavi pocet jader pouzivanych pro praci 
			private ExecutorService ex = null;
	//trida ktera nacita ze souboru
			private Read d = null;
			private OTime otime = null;
			private TakeFromQueue k = null;
			
		public ManageOfThread(TakeFromQueue k,OTime otime) {
			this.otime = otime;
			this.k = k;
		}	
			
	
		/**
		 * spusteni asymchornich vlaken starajici se o nacitani do fronty ze souboru
		 * a zaroven vybirani z fronty a nasledne zpracovani kazdeho prvku
		 */
	public void start(){
		//fonta kam se ukladaji nactene hodnoty ye souboru
		q = new LinkedBlockingQueue<>();
	
		//exekutor kde se nastavi pocet jader pouzivanych pro praci 
		ex = Executors.newFixedThreadPool(4);
		
		//trida ktera cte ze souboru
		d = new Read("Data2.txt",q);
		
		//trida ktera vybira ulozene veci ze souboru
		k.setQueue(q);
		
		//priradi tridu Observer
		k.setobserver(otime);
		
		//spusteni nacitani ze souboru
		ex.execute(d);
		//spusteni tridy odebirajici polozky z fronty
		ex.execute(k);
		
		
	}
	
	/**
	public void test(){
				//trida ktera cte ze souboru
				Read d = new Read("Data2.txt",q);
				
				//trida ktera vybira ulozene veci ze souboru
				TakeFromQueue k = new TakeFromQueue();
				
			
				
				//spusteni nacitani ze souboru
				ex.execute(d);
				
				
				/**
				//spusteni tridy implementujici callable
				//konzument informaci ve fronte
				Future<Integer> future2 = ex.submit(k);
						
				//cekani na navratovou hodnotu
				//nacteni cele fronty
				try {
					Integer a = future2.get();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				q.clear();
	}
	
	*/
	
	/**
	 * ukonci bezici vlakna
	 */
	private void finish(){
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
