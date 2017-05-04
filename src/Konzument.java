import java.util.concurrent.BlockingQueue;

public class Konzument implements Runnable{

	private final BlockingQueue<String> fronta;
	
	public Konzument(BlockingQueue<String> fronta) {
		this.fronta = fronta;
	}
	
	@Override
	public void run() {
		waitt();
		try{
			while(!fronta.isEmpty()){
				konzumuj(fronta.take(),fronta.size());
				
				}
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
	}
	
	public void konzumuj(String radka, int size){
		System.out.println(radka + "velikost fronty " + size);
	}
	
	public void waitt(){
		try{
			Thread.sleep(10);
		}catch(InterruptedException e){}
	}

}
