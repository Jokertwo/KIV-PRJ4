
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * trida obstaravajici cteni ze souboru
 * jmeno souboru se predava pri vytvareni objektu
 * 
 * @author Jokertwo
 * 
 */

public class Read implements Runnable
{
	private String jmenoSouboru = new String();
	private FileReader fr;
	private BufferedReader in;
	private final BlockingQueue<String> fronta;
	
	
	/**
	 * kontruktor
	 * @param jmenoSouboru jmeno souboru ze ktereho se ma cist
	 * @param fronta fronta kam se budou nactene informace ukladat
	 */
	public Read(String jmenoSouboru,BlockingQueue<String> fronta){
		this.jmenoSouboru = jmenoSouboru;
		this.fronta = fronta;
	}
	
	@Override
	public void run(){
		
		readFromFile();
	}
	
	/**
	 * cte ze souboru
	 */
	private void readFromFile(){
		String radka;
		
		try{
			fr = new FileReader(jmenoSouboru);
			in = new BufferedReader(fr);
			//cte dokud nedojde az na konec souboru
			while((radka = in.readLine()) != null){
				
				try{
					//ulozi do fronty
					fronta.put(radka);
				}
				catch(InterruptedException e){}
			}
			fr.close();
		}catch(IOException e){
			System.err.println(e);
		}
	}
	
}