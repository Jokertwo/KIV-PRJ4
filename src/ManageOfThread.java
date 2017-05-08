import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;




public class ManageOfThread {
	
	
	//fonta kam se ukladaji nactene hodnoty ye souboru
			private	BlockingQueue<String> q = null;		
	//exekutor kde se nastavi pocet jader pouzivanych pro praci 
			private ExecutorService ex = Executors.newFixedThreadPool(4);
	//trida ktera nacita ze souboru
			private Read d = null;
	//Observer integer s ulozenou hodnotou 
			private OTime otime = null;
	//trida ktera vybira prvky z fronty
			private TakeFromQueue k = null;
	//pocitadlo bezicich vlaken	
			private CountDownLatch latch;
			
			
			//kontruktor
		public ManageOfThread(OTime otime) {
			this.otime = otime;
		}
	
		/**
		 * spusti vlakno ktere vytvori soubor
		 * ze ktereho pujde cist
		 */
	public void create(){	
		CreateFile cr = new CreateFile();	
		Platform.runLater(cr);		
	}
	
		/**
		 * spusteni asymchornich vlaken starajici se o nacitani do fronty ze souboru
		 * a zaroven vybirani z fronty a nasledne zpracovani kazdeho prvku
		 */
	public void start(){
		//pocitadlo
		latch = new CountDownLatch(2);
		
		//fonta kam se ukladaji nactene hodnoty ye souboru
		q = new LinkedBlockingQueue<>();
		
		//trida ktera cte ze souboru
		d = new Read("Data2.txt",q);
		
		//trida ktera vybira ulozene veci z fronty
		k = new TakeFromQueue();
		
		//preda pocitadlo
		d.setCountDownLatch(latch);
		
		//priradi z ktere fronty se ma vybirat
		k.setQueue(q);
		
		//preda pocitadlo
		k.setCountDownLatch(latch);
		
		//priradi tridu Observer
		k.setobserver(otime);
			
		//spusteni nacitani ze souboru
		ex.execute(d);
			
		//spusteni tridy odebirajici polozky z fronty
		ex.execute(k);
	
		//po dobehnuti obou vlaken ukoneci ExecutorService
		ex.execute(new Runnable() {
			
			@Override
			public void run() {
				try{
					latch.await();
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
				finish();
			}
		});
	
	}	
	
	/**
	 * ukonci bezici vlakna
	 */
	private void finish(){
		try {
		    System.out.println("attempt to shutdown executor");
		    ex.shutdown();
		    ex.awaitTermination(2, TimeUnit.SECONDS);
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
