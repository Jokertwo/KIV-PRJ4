import java.util.concurrent.BlockingQueue;


import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Trida vybira vztvorenou frontu
 * 
 * @author Jokertwo
 *
 */

public class TakeFromQueue implements Runnable{

	
	private OTime time;
	
	private BlockingQueue<String> fronta;
	//celkovy soucet
	private long sum = 0;
	//nejvyssi nalezeny
	private int highest = Integer.MIN_VALUE;
	//nejnizzsi nalezeny
	private int lower = Integer.MAX_VALUE;
	//celkovy pocet
	private int count = 0;
	//prumer
	private double average = 0;
	
	
	private static final String HIGHEST = "Nejvetsi : ";
	private static final String LOWEST = "Nejmensi : ";
	private static final String SUM = "Soucet : ";
	private static final String AVERAGE = "Prumer : ";
	private static final String COUNT = "Pocet : ";
	
	
	public StringProperty Ssum = new SimpleStringProperty(Long.toString(sum));
	public StringProperty Shigh = new SimpleStringProperty(Integer.toString(highest));
	public StringProperty Slow = new SimpleStringProperty(Integer.toString(lower));
	public StringProperty Scount = new SimpleStringProperty(Integer.toString(count));
	public StringProperty Saver = new SimpleStringProperty(Double.toString(average));
	
	
	
	public void setQueue(BlockingQueue<String> fronta){
		this.fronta = fronta;
	}
	
	
	
	public void setobserver(OTime time){
		this.time = time;
	}
	
	/**
	 * pocita prumer nactenych polozek
	 */
	private void average(){
		this.average = sum/count; 
		
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
	
	@Override
	public void run() {
			
		try{
			//vybira dokud fronta neni prazdna a zaroven je nacteny cely soubor
			while(Read.done != true || !fronta.isEmpty()){
				takeIt(fronta.take());
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						Scount.set(COUNT + Integer.toString(count));
						Slow.set(LOWEST + Integer.toString(lower));
						Shigh.set(HIGHEST + Integer.toString( highest));
						Ssum.set(SUM + Long.toString(sum));
						Saver.set(AVERAGE + Double.toString(average));
					}
				});
				
				try{
					Thread.sleep(time.getValue());
					System.out.println(time.getValue());;
				}catch(InterruptedException ex){}
				
				}
			//vyresetovat promenou pro pripad opetovneho spusteni
			Read.done = false;
			
		}catch(InterruptedException e){
			e.printStackTrace();
		}	
	}
	
	/**
	 * tisk informaci o 'globalnich' promenych
	 * metoda pouzita pri testovani
	
	private void printInfo(){
		System.out.println("Soucet je " + this.sum);
		System.out.println("Prumer je " + this.average);
		System.out.println("Nejvyssi cislo je " + this.highest);
		System.out.println("Nejnizsi cislo je " + this.lower);
		System.out.println("Celkove bylo cisel " + this.count);
	}
	 */
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
			System.err.println("Neni to cislo");
		}
		
		
	}
}
