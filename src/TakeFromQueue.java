import java.util.concurrent.BlockingQueue;

/**
 * Trida vybira vztvorenou frontu
 * 
 * @author Jokertwo
 *
 */

public class TakeFromQueue implements Runnable{

	private final BlockingQueue<String> fronta;
	
	//konstruktor
	public TakeFromQueue(BlockingQueue<String> fronta) {
		this.fronta = fronta;
	}
	
	@Override
	public void run() {
		//pockani nez se aspon trochu naplni fronta
		//neni uplne nejstatnejsi
		waitt(10);
		long startime = System.currentTimeMillis();
		try{
			//vybira dokod fronta neni prazdna
			while(!fronta.isEmpty()){
				konzumuj(fronta.take(),fronta.size());
				
				}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		long endtime = System.currentTimeMillis();
		
		System.out.println("cas zpracovani je " + (endtime - startime));
		
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

}
