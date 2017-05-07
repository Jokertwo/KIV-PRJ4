import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	
	
	private Stage primaryStage;
	private Gui gui = new Gui();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Inventar obuvi");
		this.primaryStage.setScene (tabulka());
		this.primaryStage.setMinHeight(350);
		this.primaryStage.setMinWidth(450);
		this.primaryStage.show();	
		
	}
	
	public Scene tabulka() {
		Scene scene = new Scene(tab(), 200, 200);
		
		return scene;
	}
	/**
	 * usporadani tabulky
	 * @return
	 */
	public Parent tab() {
		BorderPane rootPane = new BorderPane();
		rootPane.setTop(gui.info());
		rootPane.setCenter(gui.button());
		
		
		return rootPane;
	}
	
	
	
 public static void main(String[] args){
		
	 launch(args);
	 
		
					
	}


}
