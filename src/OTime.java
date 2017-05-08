import java.util.Observable;

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
