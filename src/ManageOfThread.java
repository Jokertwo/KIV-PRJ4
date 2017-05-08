import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;




public class ManageOfThread {
	
	
	//fonta kam se ukladaji nactene hodnoty ye souboru
			private	BlockingQueue<String> q = null;
		
	//exekutor kde se nastavi pocet jader pouzivanych pro praci 
			private ExecutorService ex = Executors.newFixedThreadPool(4);
	//trida ktera nacita ze souboru
			private Read d = null;
			private OTime otime = null;
			private TakeFromQueue k = null;
			
			
		public ManageOfThread(OTime otime) {
			this.otime = otime;
		}	
			
	public void finish(){
		Shutdown sh = new Shutdown(ex);
		ex.execute(sh);
	}
		/**
		 * spusteni asymchornich vlaken starajici se o nacitani do fronty ze souboru
		 * a zaroven vybirani z fronty a nasledne zpracovani kazdeho prvku
		 */
	public void start(){
		
		
		
		//fonta kam se ukladaji nactene hodnoty ye souboru
		q = new LinkedBlockingQueue<>();

		
		//trida ktera cte ze souboru
		d = new Read("Data2.txt",q);
		
		k = new TakeFromQueue();
		
		
		
		//trida ktera vybira ulozene veci ze souboru
		k.setQueue(q);
		
		//priradi tridu Observer
		k.setobserver(otime);
		
		//spusteni nacitani ze souboru
		ex.execute(d);
		//spusteni tridy odebirajici polozky z fronty
		ex.execute(k);
		
		
		
		
	}
	
	
}
