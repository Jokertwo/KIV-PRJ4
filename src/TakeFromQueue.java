import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Trida vybira vztvorenou frontu
 * 
 * @author Jokertwo
 *
 */

public class TakeFromQueue implements Runnable{

	//Observable trida ve ktere se uklada hodnota po kterou bude vlakno 
	//spat
	private OTime time;
	
	private BlockingQueue<String> fronta;
	//celkovy soucet
	private long sum;
	//nejvyssi nalezeny
	private int highest;
	//nejnizzsi nalezeny
	private int lower;
	//celkovy pocet
	private int count;
	//prumer
	private double average;
	//pocet znaku ktere nebyly cisla
	private int errors;
	//pocet radku v souboru
	private float numberOfLines;
	//pocitadlo
	private CountDownLatch latch = null;
	
	private static final String HIGHEST = "Největší : ";
	private static final String LOWEST = "Nejmenší : ";
	private static final String SUM = "Součet : ";
	private static final String AVERAGE = "Průměr : ";
	private static final String COUNT = "Počet čísel : ";
	private static final String ERROR = "Počet chyb : ";
	
	
	
	//promene pro aktualizovani labelu s informacemi
	public static StringProperty Ssum = new SimpleStringProperty(SUM + "---");
	public static StringProperty Shigh = new SimpleStringProperty(HIGHEST + "---");
	public static StringProperty Slow = new SimpleStringProperty(LOWEST + "---");
	public static StringProperty Scount = new SimpleStringProperty(COUNT + "---");
	public static StringProperty Saver = new SimpleStringProperty(AVERAGE + "---");
	public static StringProperty Serror = new SimpleStringProperty(ERROR + "---");
	//promena pro nastaveni disable tlacitka
	public static BooleanProperty Sdisable = new SimpleBooleanProperty(false);
	//promena pro progresBar
	public static FloatProperty Sprogres = new SimpleFloatProperty(0);
	/**
	 * nastavi frontu ze ktere se bude cist
	 * @param fronta BlockingQueue
	 */
	public void setQueue(BlockingQueue<String> fronta){
		this.fronta = fronta;
	}
	
	public void setCountDownLatch(CountDownLatch latch){
		this.latch = latch;
	}
	
	/**
	 * trida s casem spanku vlakna
	 * @param time OTime trida
	 */
	public void setobserver(OTime time){
		this.time = time;
	}
	
	
	/**
	 * pocita prumer nactenych polozek
	 */
	private void average(){
		this.average = sum/count; 
		
	}
	
	public boolean isAlive(){
		return Thread.currentThread().isAlive();
	}
	
	/**
	 * pocita pocet chyb
	 */
	private void errors(){
		this.errors++;
	}
	
	/**
	 * scita hodnotu pradanou parametrem s 'globalni' promenou sum
	 * @param actual int 
	 */
	private void sum(int actual){
		this.sum += actual;
		
	}
	/**
	 * pokud je hodnota predana argumentem vetsi nez 
	 * v 'globalni' promene highest, ulozi ji do ni
	 * @param actual int
	 */
	private void highest(int actual){
		if(this.highest < actual){
			this.highest = actual;
			
			
		}
	}
	/**
	 * pokud je hodnota predana argumentem mensi nez 
	 * v 'globalni' promene lower, ulozi ji do ni
	 * @param actual int
	 */
	private void lower(int actual){
		if(this.lower > actual){
			this.lower = actual;
			
		}
	}
	/**
	 * zvysi pocitadlo hodnot
	 */
	private void count(){
		this.count++;
		
	}
	
	/**
	 * reset pred kazdym spustenim
	 */
	private void reset(){
		this.sum = 0;
		this.average = 0;
		this.count = 0;
		this.highest = Integer.MIN_VALUE;
		this.lower = Integer.MAX_VALUE;
		this.errors = 0;
		this.numberOfLines = 0;
	}
	
	@Override
	public void run() {
		
		Thread k = Thread.currentThread();
		k.setName("Citac z fronty");
		
		//disable tlacitka
			Sdisable.set(true);
		try{
			//vynuluje hodnoty
			reset();
			
			numberOfLines = Integer.parseInt(fronta.take());
			//vybira dokud fronta neni prazdna a zaroven je nacteny cely soubor
			while(Read.done != true || !fronta.isEmpty()){
				
				//zpracovani prvku z fronty
				takeIt(fronta.take());
				
				//aktualizace informaci v GUI
				//pomoci dalsiho vlakna
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						Scount.set(COUNT + Integer.toString(count));
						Slow.set(LOWEST + Integer.toString(lower));
						Shigh.set(HIGHEST + Integer.toString( highest));
						Ssum.set(SUM + Long.toString(sum));
						Saver.set(AVERAGE + Double.toString(average));
						Serror.set(ERROR + Integer.toString(errors));
						Sprogres.set(count/numberOfLines);
						
					}
				});
				
				try{
					//uspani vlakna na promenou dobu
					Thread.sleep(time.getValue());
					
				}catch(InterruptedException ex){
					ex.printStackTrace();
					
					//pri predcasnem ukonceni vlakna
					//vycisti zbyvajici prvky ve fronte
					fronta.clear();
					
					//promena jez ridi disable tlacitka
					Sdisable.set(false);
					
					//vyresetovat promenou pro pripad opetovneho spusteni
					Read.done = false;
					
				}			
			}			
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		//vyresetovat promenou pro pripad opetovneho spusteni
		Read.done = false;
		
		//promena jez ridi disable tlacitka
		Sdisable.set(false);
		
		
		latch.countDown();
		
	}
	
	/**
	 * provede nekolik atomickych operaci s kazdym vybranym prvkem
	 * 
	 * @param stack string
	 */
	private void takeIt(String stack){
		int actual;
		
		try{
			actual = Integer.parseInt(stack);
			//soucet
			sum(actual);
			//nejvyssi
			highest(actual);
			//nejnizsi
			lower(actual);
			//pocet
			count();
			//prumer
			average();
			
				
		}catch(NumberFormatException e){
			//chyby
			errors();
		}		
	}
}
