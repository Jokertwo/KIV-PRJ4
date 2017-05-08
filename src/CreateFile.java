import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Random;


import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

public class CreateFile implements Runnable{

	
	private int numberOfLines;
	private File file = null;
	
	
	
	
	
	private boolean getNumberOfLines(){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Pocet radku");
		dialog.setHeaderText("Zadajte pocet radku kolik chcete vygenerovat");
		dialog.setContentText("Zadavejte pouze cisla:");
		
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(number -> {
			try{
				numberOfLines = Integer.parseInt(number);
				
			}catch(NumberFormatException ex){
				numberOfLines = -1;
			}
		});
		if(numberOfLines > 0){
			return true;
		}
		return false;
	}
	
	private void chooseFile(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
            );
        fileChooser.getExtensionFilters().addAll(
        		 new FileChooser.ExtensionFilter("Text", "*.txt")
        		);
        
        file = fileChooser.showSaveDialog(Main.primaryStage);
	}
	
	/**
	 * vytvori soubor podle zadanych parametru
	 * na kazdy radek zapise jedno cislo nebo pismeno(chyby)
	 * podle predanych parametru
	 * 
	 * @param numberOfLines pocet radku
	 * @param nameOfFile jmeno souboru ktery se ma vytvorit
	 */
	private void create(){	
		if(file != null){
			Random r = new Random();
			try{
			    PrintWriter writer = new PrintWriter(file, "UTF-8");
			    writer.println(numberOfLines);
			    for(int i = 1; i <= numberOfLines; i++){
			    
			    	writer.println(r.nextInt(i * 3));
			    }
			    writer.close();
			} catch (IOException e) {
			  System.err.println(e);
			}
		}
	}
	
	
	@Override
	public void run() {
		chooseFile();
		if(getNumberOfLines()){
			create();
		};
		
		
	}

}
