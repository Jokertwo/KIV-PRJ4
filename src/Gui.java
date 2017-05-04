import javafx.scene.Node;
import javafx.scene.control.Button;

public class Gui {

	
	/**
	 * Tlaciko pro spusteni testu
	 * 
	 * @return button
	 */
	public Node button(){
		Button bt = new Button("Test");
		bt.setOnAction(event ->{
			ManageOfThread start = new ManageOfThread();
			start.otestuj();
			
		});
		return bt;
	}
}
