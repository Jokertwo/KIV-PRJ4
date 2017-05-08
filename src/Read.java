
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * trida obstaravajici cteni ze souboru
 * jmeno souboru se predava pri vytvareni objektu
 * 
 * @author Jokertwo
 * 
 */

public class Read implements Runnable
{
	private FileReader fr;
	private BufferedReader in;
	private final BlockingQueue<String> fronta;
	private File file;
	public static boolean done = false;
	
	private CountDownLatch latch = null;
	
	/**
	 * kontruktor
	 * @param jmenoSouboru jmeno souboru ze ktereho se ma cist
	 * @param fronta fronta kam se budou nactene informace ukladat
	 */
	public Read(File file,BlockingQueue<String> fronta){
		this.file = file;
		this.fronta = fronta;
	}
	
	@Override
	public void run(){
		
		readFromFile();
		return;
	}
	
	public void setCountDownLatch(CountDownLatch latch){
		this.latch = latch;
	}
	
	/**
	 * cte ze souboru
	 */
	private void readFromFile(){
		String radka;
		
		try{
			fr = new FileReader("Data2.txt");
			in = new BufferedReader(fr);
			//cte dokud nedojde az na konec souboru
			while((radka = in.readLine()) != null){
				
				try{
					//ulozi do fronty
					fronta.put(radka);
				}
				catch(InterruptedException e){
					e.printStackTrace();
					done = true;
					fr.close();
				}
			}
			done = true;
			fr.close();
		}catch(IOException e){
			System.err.println(e);
		}
		latch.countDown();
	}
	
}