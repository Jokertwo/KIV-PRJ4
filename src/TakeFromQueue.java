import java.util.concurrent.BlockingQueue;


import javafx.application.Platform;
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
	
	
	private static final String HIGHEST = "Nejvetsi : ";
	private static final String LOWEST = "Nejmensi : ";
	private static final String SUM = "Soucet : ";
	private static final String AVERAGE = "Prumer : ";
	private static final String COUNT = "Pocet cisel : ";
	private static final String ERROR = "Pocet chyb : ";
	
	
	//promene pro aktualizovani lablu s informacemi
	public StringProperty Ssum = new SimpleStringProperty(SUM + "---");
	public StringProperty Shigh = new SimpleStringProperty(HIGHEST + "---");
	public StringProperty Slow = new SimpleStringProperty(LOWEST + "---");
	public StringProperty Scount = new SimpleStringProperty(COUNT + "---");
	public StringProperty Saver = new SimpleStringProperty(AVERAGE + "---");
	public StringProperty Serror = new SimpleStringProperty(ERROR + "---");
	
	
	/**
	 * nastavi frontu ze ktere se bude cist
	 * @param fronta BlockingQueue<String>
	 */
	public void setQueue(BlockingQueue<String> fronta){
		this.fronta = fronta;
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
	}
	
	@Override
	public void run() {
			
		try{
			reset();
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
					}
				});
				
				try{
					//uspani vlakna na promenou dobu
					Thread.sleep(time.getValue());
					
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
