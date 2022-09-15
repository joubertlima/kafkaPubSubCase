package pub_sub;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class SyncPeer {
	private AtomicBoolean canPub;
	private static String user;
	
	public SyncPeer(){
		canPub = new AtomicBoolean();
		canPub.set(false);
		Publisher producer = new Publisher();
		producer.configure(canPub, user, "sync");
		
		
		Subscriber consumer = new Subscriber();
		consumer.configure(canPub, user, Arrays.asList("sync"));
		
		producer.start();
		consumer.start();
		
		try {
			producer.join();
			consumer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			user = args[0];
		}catch (Exception e){
			user = "default";
		}
		new SyncPeer();
	}

}
