import java.util.Observable;

public class ODisable extends Observable{
	
	private boolean value = false;
	
	public ODisable(boolean value) {
		// TODO Auto-generated constructor stub
		this.value = value;
		notifyObservers(value);
	}
	
	public boolean getValue(){
		return value;
	}
	
	public void setValue(boolean value){
		value = value;
		setChanged();
		notifyObservers(value);
	}

}
