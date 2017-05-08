import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.stage.FileChooser;




public class ManageOfThread {
	
	
	//fonta kam se ukladaji nactene hodnoty ye souboru
			private	BlockingQueue<String> q = null;		
	//exekutor kde se nastavi pocet jader pouzivanych pro praci 
			private ExecutorService ex = Executors.newFixedThreadPool(10);
	//trida ktera nacita ze souboru
			private Read d = null;
	//Observer integer s ulozenou hodnotou 
			private OTime otime = null;
	//trida ktera vybira prvky z fronty
			private TakeFromQueue k = null;
	//pocitadlo bezicich vlaken	
			private CountDownLatch latch;
	//soubor ze ktereho se ma ist
			private File file = null;
			
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
		//vyber souoru ze ktereho se bude cist
		if(chooseFile()){
			//pocitadlo
			latch = new CountDownLatch(2);
			
			//fonta kam se ukladaji nactene hodnoty ye souboru
			q = new LinkedBlockingQueue<>();
			
		
			//trida ktera cte ze souboru
			d = new Read(file,q);
			
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
				
				//cekani na dobehnuti vlaken
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
	}	
	
	/**
	 * ukonci bezici vlakna
	 */
	private void finish(){
		try {
		    ex.shutdown();
		    ex.awaitTermination(1, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    ex.shutdownNow();
		}
	}
	
	/**
	 * vybere soubor ze ktereho se ma cist
	 * @return vraci true pokud byl zvolen soubor
	 */
	private boolean chooseFile(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Vyber soubor");
        
        fileChooser.getExtensionFilters().addAll(
        		 new FileChooser.ExtensionFilter("Text", "*.txt")
        		);
        
        file = fileChooser.showOpenDialog(Main.primaryStage);
        
        if(file != null){
        	return true;
        }
        return false;
	}
}
