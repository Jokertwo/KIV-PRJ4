import java.util.Observable;

/**
 * Třída použitá jako prostřeník pro výměnu informací mezí Slider ze třídy Gui.java
 * a třídou TakeFromQueue
 * 
 * @author Jokertwo
 *
 */


public class OTime extends Observable{

	private int value = 0;
	
	public OTime(int initValue){
		this.value = initValue;
		notifyObservers(initValue);
	}
	
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int newValue){
		value = newValue;
		setChanged();
		notifyObservers(value);
	}
}
