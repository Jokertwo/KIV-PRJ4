
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.LinkedBlockingQueue;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Gui {

	private Slide time = new Slide(0,200,50);
	public IntegerProperty speedOfThread = new SimpleIntegerProperty((int)time.getValue());
	
	private BlockingQueue<String> q  = null;
	private ExecutorService ex = null;
	private Read d = null;
	
	public OTime otime = new OTime((int)time.getValue());
	
	
	private TakeFromQueue k = new TakeFromQueue();;
	
	
	public static Label sum,big,low,count,aver,val;
	
	
	
	
	
	int i = 0;
	public boolean run = false; 
	
	private Node left(){
		VBox box = new VBox();

		sum = new Label();
		big = new Label();
		
		sum.textProperty().bind(k.Ssum);
		big.textProperty().bind(k.Shigh);
		
		box.setSpacing(5);
		
		box.getChildren().addAll(sum,big);
		
		return box;
	}
	
	private Node center(){
		VBox box = new VBox();
		
		low = new Label();
		count  = new Label();
		
		low.textProperty().bind(k.Slow);
		count.textProperty().bind(k.Scount);
		
		box.setSpacing(5);
		
		box.getChildren().addAll(low,count);
		return box;
	}
	private Node right(){
		VBox box = new VBox();
		
		aver = new Label();
		
		aver.textProperty().bind(k.Saver);
		
		box.getChildren().add(aver);
		
		return box;
	}
	
	/**
	 * Slider kterym se ovlada doba po kterou vlakno ceka
	 * @return
	 */
	public Node slide(){
		VBox box = new VBox();
		
		box.setAlignment(Pos.CENTER);
		VBox.setMargin(time, new Insets(20));
		
		val = new Label(Integer.toString((int)time.getValue()));
		
		
		
		time.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	                    val.setText(Integer.toString(new_val.intValue()));
	                    speedOfThread.set(new_val.intValue());
	                    otime.setValue(new_val.intValue());
	            }
		});
		
		otime.addObserver(time);
		
		//zobrazeni znacek na slideru
		time.setShowTickMarks(true);
		//zobrazeni hodnots na slideru
		time.setShowTickLabels(true);
		
		box.getChildren().addAll(time,val);
		
		return box;
	}
	
	public Node info(){
		HBox box = new HBox();
		
		box.getChildren().addAll(left(),center(),right());
		
		box.setSpacing(20);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
	
	/**
	 * Tlaciko pro spusteni testu
	 * 
	 * @return button
	 */
	public Node button(){
		Button bt = new Button("Test");
		bt.setOnAction(event ->{
			
			//fonta kam se ukladaji nactene hodnoty ye souboru
			q = new LinkedBlockingQueue<>();
		
			//exekutor kde se nastavi pocet jader pouzivanych pro praci 
			ex = Executors.newFixedThreadPool(4);
			
			//trida ktera cte ze souboru
			d = new Read("Data2.txt",q);
			
			//trida ktera vybira ulozene veci ze souboru
			k.setQueue(q);
			
			//priradi tridu Observer
			k.setobserver(otime);
			
			//spusteni nacitani ze souboru
			ex.execute(d);
			//spusteni tridy odebirajici polozky z fronty
			ex.execute(k);
			
		});
		return bt;
	}
}
	





