import java.util.Observer;

import javafx.scene.control.Button;

public class ButtonStart extends Button implements Observer{

	
	public ButtonStart(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(java.util.Observable arg0, Object oo) {
		setDisable((Boolean)oo);
		
	}
	
}
