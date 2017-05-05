import javafx.application.Platform;

public class AktualizeInform implements Runnable{

	/**
	private Label sum,big,low,count,aver;
	
	public AktualizeInform(Label sum,Label big,Label low,Label count,Label aver) {
		this.sum = sum;
		this.big = big;
		this.low = low;
		this.count = count;
		this.aver = aver;
	}
	*/
	private TakeFromQueue take;
	
	public AktualizeInform(TakeFromQueue take) {
		this.take = take;
	}
	
	@Override
	public void run() {
		
		try{
			while(true){
				Platform.runLater(()->{
									setHight();
									setLow();
									setAver();
									setCount();
									setSum();
				});
						
				Thread.sleep(1);
			}
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
		
	}
	
	public void setHight(){
		Gui.big.setText(Gui.HIGHEST + take.getHigh());
	}
	public void setLow(){
		Gui.big.setText(Gui.LOWEST + take.getLow());
	}
	public void setAver(){
		Gui.aver.setText(Gui.AVERAGE + take.getAver());
	}
	public void setCount(){
		Gui.count.setText(Gui.COUNT + take.getCount());
	}
	public void setSum(){
		Gui.sum.setText(Gui.SUM + take.getSum());
	}
}
