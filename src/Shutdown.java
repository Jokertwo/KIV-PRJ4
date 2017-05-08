import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Shutdown implements Runnable{

	
	//fonta kam se ukladaji nactene hodnoty ye souboru
	private	BlockingQueue<String> q = null;
	//exekutor kde se nastavi pocet jader pouzivanych pro praci 
	private ExecutorService ex = null;
	
	
	
	public Shutdown(ExecutorService ex,BlockingQueue<String> q) {
		this.ex = ex;
		this.q = q;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		
			while(Read.done != true && !q.isEmpty()){
				try{
					Thread.sleep(100);
					System.out.println("not yet");
				}catch(InterruptedException es){}
			}
		finish();	
	}

	
	private void finish2(){
		try{
			ex.shutdown();
			ex.awaitTermination(5,TimeUnit.SECONDS);
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
	
	
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
		q.clear();
	}
}
