
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
	
	static public boolean hotovo = false;
	
	static public String actual;
	
	
	public Read(String jmenoSouboru,BlockingQueue<String> fronta){
		this.jmenoSouboru = jmenoSouboru;
		this.fronta = fronta;
	}
	static public String getActual(){
		return actual;
	}
	
	public void run(){
		String radka;
		
		try{
			fr = new FileReader(jmenoSouboru);
			in = new BufferedReader(fr);
			
			while((radka = in.readLine()) != null){
				
				try{
					fronta.put(radka);
				}
				catch(InterruptedException e){}
			}
			fr.close();
			hotovo = true;
		}catch(IOException e){
			System.err.println(e);
			hotovo  = true;
		}
	}
}