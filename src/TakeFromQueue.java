import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Trida vybira vztvorenou frontu
 * 
 * @author Jokertwo
 *
 */

public class TakeFromQueue implements Callable<Integer>{

	private final BlockingQueue<String> fronta;
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
	
	private boolean done = false;
	
	
	
	
	//konstruktor
	public TakeFromQueue(BlockingQueue<String> fronta) {
		this.fronta = fronta;
	}
	/**
	 * pocita prumer nactenych polozek
	 */
	private void average(){
		this.average = sum/count; 
	}
	
	/**
	 * pokud je v argumentu predana nula nastavy
	 * 'globalni' promenou done na true 
	 * 
	 * @param sizeOfQueue int
	 */
	private void notYet(int sizeOfQueue){
		System.out.println(sizeOfQueue + " velikost");
		if(sizeOfQueue == 0){
			this.done = true;
		}
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
		count++;
	}
	
	@Override
	public Integer call() throws Exception {
		
		
		try{
			//vybira dokud fronta neni prazdna a zaroven je nacteny cely soubor
			while(Read.done != true || !fronta.isEmpty()){
				takeIt(fronta.take());	
				}
			
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		notYet(fronta.size());
		//tisk info
		printInfo();	
		return -1;
	}
	
	/**
	 * tisk informaci o 'globalnich' promenych
	 */
	private void printInfo(){
		System.out.println("Soucet je " + this.sum);
		System.out.println("Prumer je " + this.average);
		System.out.println("Nejvyssi cislo je " + this.highest);
		System.out.println("Nejnizsi cislo je " + this.lower);
		System.out.println("Celkove bylo cisel " + this.count);
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
			System.err.println("Neni to cislo");
		}
		
		
	}
	
	/**
	 * tiskne informaci o poloykach fronty
	 * @param radka string k tisku
	 * @param size  vilost dosavadni fronty
	 */
	private void konzumuj(String radka, int size){
		System.out.println(radka + "velikost fronty " + size);
	}
	
	/**
	 * zdrzovaci funkce
	 */
	private void waitt(int time){
		try{
			Thread.sleep(time);
		}catch(InterruptedException e){}
	}
	///////////////////////////////////////GETRY/////////////////////////////////////////////
	public boolean getDone(){
		return this.done;
	}
}
