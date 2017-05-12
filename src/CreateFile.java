
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
	private final String DIALOG_TITLE = "Počet řádků";
	private final String DIALOG_HEADER_T = "Zadajte počet řádků, kolik chcete vygenerovat";
	private final String DIALOG_CONTENT_T = "Zadávejte pouze čísla:";
	
	private final String FILECHOOSER_TITLE = "Vytvoř soubor";
	
	private final String ALERT_TITLE = "Hotovo";
	private final String ALERT_HEADER_T = "Dokonceni souboru";
	private final String ALERT_CONTENT_1 = "Soubor ";
	private final String ALERT_CONTENT_2 = " byl vytvořen";
	
	
	
	
	private boolean getNumberOfLines(){
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(DIALOG_TITLE);
		dialog.setHeaderText(DIALOG_HEADER_T);
		dialog.setContentText(DIALOG_CONTENT_T);
		
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
        fileChooser.setTitle(FILECHOOSER_TITLE);
    
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
		alert.setTitle(ALERT_TITLE);
		alert.setHeaderText(ALERT_HEADER_T);
		alert.setContentText(ALERT_CONTENT_1 + file.getName() + ALERT_CONTENT_2);
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