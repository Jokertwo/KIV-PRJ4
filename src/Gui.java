import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.LinkedBlockingQueue;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Gui {

	private BlockingQueue<String> q  = null;
	private ExecutorService ex = null;
	private Read d = null;
	private TakeFromQueue k = new TakeFromQueue();;
	
	

	
	public static Label sum,big,low,count,aver;
	
	
	
	
	
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
		
		low.textProperty().bind(k.Saver);
		
		box.getChildren().add(aver);
		
		return box;
	}
	
	public Node info(){
		HBox box = new HBox();
		
		box.getChildren().addAll(left(),center(),right());
		
		box.setSpacing(50);
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
			
			//spusteni nacitani ze souboru
			ex.execute(d);
			//spusteni tridy odebirajici polozky z fronty
			ex.execute(k);
			
			
			/**
			//spusteni tridy implementujici callable
			//konzument informaci ve fronte
			Future<Integer> future2 = ex.submit(k);
					
			//cekani na navratovou hodnotu
			//nacteni cele fronty
			try {
				Integer a = future2.get();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			
		});
		return bt;
	}
}
	
	
//////////////////////////////////////////testovani////////////////////////////	




