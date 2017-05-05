import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;



public class ManageOfThread {
	
	
	
	
	
	
	
	//fonta kam se ukladaji nactene hodnoty ye souboru
			public	BlockingQueue<String> q = new LinkedBlockingQueue<>();
		
	//exekutor kde se nastavi pocet jader pouzivanych pro praci 
			public ExecutorService ex = Executors.newFixedThreadPool(4);
	
	public void otestuj(){
		test();
		
		finish();
	}
	
	public void test(){
				//trida ktera cte ze souboru
				Read d = new Read("Data2.txt",q);
				
				//trida ktera vybira ulozene veci ze souboru
				TakeFromQueue k = new TakeFromQueue(q);
				
			
				
				//spusteni nacitani ze souboru
				ex.execute(d);
				
				
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
	
	
	
	/**
	 * ukonci bezici vlakna
	 */
	public void finish(){
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

	
	
	
	public void setHight(TakeFromQueue take){
		Gui.big.setText(Gui.HIGHEST + take.getHigh());
	}
	public void setLow(TakeFromQueue take){
		Gui.big.setText(Gui.LOWEST + take.getLow());
	}
	public void setAver(TakeFromQueue take){
		Gui.aver.setText(Gui.AVERAGE + take.getAver());
	}
	public void setCount(TakeFromQueue take){
		Gui.count.setText(Gui.COUNT + take.getCount());
	}
	public void setSum(TakeFromQueue take){
		Gui.sum.setText(Gui.SUM + take.getSum());
	}
}
