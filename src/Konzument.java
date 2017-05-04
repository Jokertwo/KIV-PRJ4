import java.util.concurrent.BlockingQueue;

public class Konzument implements Runnable{

	private final BlockingQueue<String> fronta;
	
	public Konzument(BlockingQueue<String> fronta) {
		this.fronta = fronta;
	}
	
	@Override
	public void run() {
		try{
			while(Read.hotovo == false || fronta.isEmpty()){
				konzumuj(fronta.take());
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
	public void konzumuj(String radka){
		System.out.println(radka);
	}

}
