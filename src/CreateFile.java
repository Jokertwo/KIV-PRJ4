
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Random;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	
	private boolean chooseFile(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Create file");
    
        fileChooser.getExtensionFilters().addAll(
        		 new FileChooser.ExtensionFilter("Text", "*.txt")
        		);
        
        file = fileChooser.showSaveDialog(Main.primaryStage);
        if(file != null){
        	return true;
        }
        return false;
	}
	
	/**
	 * vytvori soubor podle zadanych parametru
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
	private void alertDone(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Hotovo");
		alert.setHeaderText("Dokonceni souboru");
		alert.setContentText("Soubor " + file.getName() + " byl vytvoren.");
		alert.show();
	}
	
	@Override
	public void run() {	
		if(chooseFile()){
			if(getNumberOfLines()){
				create();
				alertDone();
			};
		}
		
		
		
	}

}