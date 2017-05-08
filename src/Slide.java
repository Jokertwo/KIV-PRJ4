
import java.util.Observer;

import javafx.scene.control.Slider;

public class Slide extends Slider implements Observer{

	
	public Slide(double min,double max,double value){
		super(min ,max, value);
	}
	
	@Override
	public void update(java.util.Observable arg0, Object oo) {
		setValue((Integer)oo);
		
	}
	
	
	

}
