


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
	private final String MS = " ms";
	private final String DESCRIPTION = "Cas po kterou bude vlakno 'spat'";
	//slider
	private Slide time = new Slide(0,200,50);
	//observer pro ulozeni hodnoty
	public OTime otime = new OTime((int)time.getValue());
	//popisi jednotlivych akci
	private Label sum,big,low,count,aver,val,err,descri;
	//buton---tlacitko
	public ButtonStart bt = new ButtonStart("Spust");
	//trida starajici se o vlakna
	private ManageOfThread man = new ManageOfThread(otime);
	public ODisable dis = new ODisable(false);
	
	
	
	private Node left(){
		VBox box = new VBox();

		sum = new Label();
		big = new Label();
		
		sum.textProperty().bind(TakeFromQueue.Ssum);
		big.textProperty().bind(TakeFromQueue.Shigh);
		
		box.setSpacing(5);
		
		box.getChildren().addAll(sum,big);
		
		return box;
	}
	
	private Node center(){
		VBox box = new VBox();
		
		low = new Label();
		aver = new Label();
		
		
		low.textProperty().bind(TakeFromQueue.Slow);
		aver.textProperty().bind(TakeFromQueue.Saver);
		
		
		box.setSpacing(5);
		
		box.getChildren().addAll(low,aver);
		return box;
	}
	private Node right(){
		VBox box = new VBox();
		
		count  = new Label();
		err = new Label();
		
		count.textProperty().bind(TakeFromQueue.Scount);
		err.textProperty().bind(TakeFromQueue.Serror);
		
		box.getChildren().addAll(count,err);
		
		return box;
	}
	private Node createButton(){
		Button create = new Button("Create file");
		create.setOnAction(event->{
			man.create();
		});
		return create;
	}
	/**
	 * Slider kterym se ovlada doba po kterou vlakno ceka
	 * @return
	 */
	public Node slide(){
		VBox box = new VBox();
		
		box.setAlignment(Pos.CENTER);
		VBox.setMargin(time, new Insets(20));
		
		val = new Label(Integer.toString((int)time.getValue())+ MS);
		descri = new Label(DESCRIPTION);	
		//nastaveni listener na slider
		time.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	                    val.setText(Integer.toString(new_val.intValue())+ MS);
	                    otime.setValue(new_val.intValue());
	            }
		});
		
		otime.addObserver(time);
		
		//zobrazeni znacek na slideru
		time.setShowTickMarks(true);
		//zobrazeni hodnots na slideru
		time.setShowTickLabels(true);
		
		box.getChildren().addAll(descri,time,val);
		
		return box;
	}
	
	/**
	 * Posklada infomacni label a vlozi do HBoxu
	 * @return vraci HBox
	 */
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
	private Node button(){
		
		bt.setOnAction(event ->{
			
			//spusteni vlaken na pozadi
			man.start();		
			});
		dis.addObserver(bt);
		
		bt.disableProperty().bind(TakeFromQueue.Sdisable);		
		return bt;
	}
	
	public Node buttons(){
		HBox box = new HBox();
		
		box.getChildren().addAll(button(),createButton());
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		return box;
	}
}
	





