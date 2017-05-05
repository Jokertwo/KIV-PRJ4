import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Gui {

	
	
	public static Label sum,big,low,count,aver;
	Label text;
	
	public static final String HIGHEST = "Nejvetsi : ";
	public static final String LOWEST = "Nejmensi : ";
	public static final String SUM = "Soucet : ";
	public static final String AVERAGE = "Prumer";
	public static final String COUNT = "Pocet";
	
	int i = 0;
	public boolean run = false; 
	
	private Node left(){
		VBox box = new VBox();

		sum = new Label(SUM);
		big = new Label(HIGHEST);
		
		box.setSpacing(5);
		
		box.getChildren().addAll(sum,big);
		
		return box;
	}
	
	private Node center(){
		VBox box = new VBox();
		
		low = new Label(LOWEST);
		count  = new Label(COUNT);
		
		box.setSpacing(5);
		
		box.getChildren().addAll(low,count);
		return box;
	}
	private Node right(){
		VBox box = new VBox();
		
		aver = new Label(AVERAGE);
		
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
			//ManageOfThread start = new ManageOfThread();
			//start.otestuj();
			startTask();
		});
		return bt;
	}
	
	
//////////////////////////////////////////testovani////////////////////////////	
	public Node stopButton(){
		Button bt = new Button("Stop");
		bt.setOnAction(event->{
			this.run = true;
		});
		return bt;
	}
	public Node test(){
		text = new Label("Start");
		return text;
	}
	public void startTask(){
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				setText();
				
			}
		};
		Thread backround = new Thread(task);
		backround.setDaemon(true);
		backround.start();
	}
	
	public void setText()
	{
		for(;; i++)
		{
			try
			{
				final String status = "Pocitadlo : " + i;
				Platform.runLater(new Runnable()
				{	
					@Override
					public void run() {
						// TODO Auto-generated method stub
						text.setText(status);
					}
				});
				Thread.sleep(100);
			}catch(InterruptedException ex){
				ex.printStackTrace();
			}
			if(run == true){
				break;
			}
		}
		run = false;
	}

	public Node naPozadi(){
		HBox b = new HBox();
		b.getChildren().addAll(button(),stopButton());
		b.setSpacing(10);
		
		VBox x = new VBox();
		x.getChildren().addAll(b,test());
		x.setSpacing(20);
		
		return x;
	}
	
	
}

