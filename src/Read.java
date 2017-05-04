



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * trida obstaravající ctení ze souboru
 * jmeno souboru se predava pri vytvareni objektu
 * 
 * @author Jokertwo
 * 
 */

public class Read extends Thread
{
	private String jmenoSouboru = new String();
	private FileReader fr;
	private BufferedReader in;
	
	static public boolean hotovo = false;
	
	static public String actual;
	
	
	public Read(String jmenoSouboru){
		this.jmenoSouboru = jmenoSouboru;
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
				actual = radka;
				try{
					Thread.sleep(10);
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