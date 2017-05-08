import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class CreateFile implements Runnable{

	
	private int numberOfLines;
	private int numberOfErrors = 0;
	private String nameOfFile = "Data.txt";
	
	/**
	 * vytvori soubor podle zadanych parametru
	 * na kazdy radek zapise jedno cislo nebo pismeno(chyby)
	 * podle predanych parametru
	 * 
	 * @param numberOfLines pocet radku
	 * @param nameOfFile jmeno souboru ktery se ma vytvorit
	 */
	private void create(int numberOfLines,String nameOfFile,int numberOfErrors){		
		Random r = new Random();
		try{
		    PrintWriter writer = new PrintWriter(nameOfFile, "UTF-8");
		    for(int i = 1; i <= numberOfLines; i++){
		    
		    	writer.println(r.nextInt(i * 3));
		    }
		    writer.close();
		} catch (IOException e) {
		  System.err.println(e);
		}
	}
	
	
	@Override
	public void run() {
		create(this.numberOfLines, this.nameOfFile, this.numberOfErrors);		
	}

}
