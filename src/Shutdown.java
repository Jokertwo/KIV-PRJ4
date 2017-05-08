
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Shutdown implements Runnable{

	
	
	//exekutor kde se nastavi pocet jader pouzivanych pro praci 
	private ExecutorService ex = null;
	
	
	
	
	public Shutdown(ExecutorService ex) {
		this.ex = ex;
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {			
		finish();	
	}

	
	private void finishNow(){
		if (!ex.isTerminated()) {
	        System.err.println("cancel non-finished tasks");
	    }
	    ex.shutdownNow();
	    System.out.println("shutdown finished");
	}
	
	
	
	/**
	 * ukonci bezici vlakna
	 */
	private void finish(){
		try {
		    System.out.println("attempt to shutdown executor");
		    ex.shutdown();
		    ex.awaitTermination(1, TimeUnit.SECONDS);
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
