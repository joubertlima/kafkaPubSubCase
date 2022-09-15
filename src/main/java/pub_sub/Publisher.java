package pub_sub;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import util.Constants;
import util.LoadProperties;

public class Publisher extends Thread{

    private Producer<String, Message> syncProd;
    private String topic;
    private String user;
    private AtomicBoolean canPub;
    private int count;
    
    public Publisher(){
        
    }

    public void configure(AtomicBoolean canPub, String user, String topic){

        this.canPub = canPub;
    	this.topic = topic;
        this.user = user;

        Properties props= LoadProperties.loadConfig("client.config");
        if( props == null) props = new Properties();
        //props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);

        props.put(ProducerConfig.CLIENT_ID_CONFIG, user+"pub");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaJsonSerializer.class);
        //props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10000);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, Integer.MAX_VALUE);



        //customized configurations for any sort of publisher are added on constructor parameters
        syncProd = new KafkaProducer<String, Message>(props);
        
    }

    @Override
    public void run() {
    	Random r = new Random();
    	count = 0;
    	try{
	    	while (true){
	    		
	    		try{
	                TimeUnit.SECONDS.sleep(r.nextInt(3)+1); //waits 1 to 3 seconds
	            }catch (Exception e){
	                e.printStackTrace();
	            }
	    		
	    		if(canPub.get()==false){
	    			
    				count++;
    			
    				publish(mountMessageAcq(count));
	    			
	    			
	    			canPub.set(true);
	    		}
	    		
	    	}    
    	}catch (Exception e){
    		syncProd.close();
    	}

        
    }

   
    public Message mountMessageAcq(int count){
    	Message msg = new Message();
    	msg.setContent("acquire:"+user+":"+count);
    	
        return msg;
    }
    
    public Message mountMessageRel(int count){
    	Message msg = new Message();
    	msg.setContent("release:"+user+":"+count);
    	
    	return msg;
    }

    public void publish(Message message){
    	Random r = new Random();
    	
    	try {
            ProducerRecord<String,Message> record = new ProducerRecord<String,Message>(topic, r.nextInt(Constants.numPartitions), message.getContent(), message);
            Future<RecordMetadata> ack = syncProd.send(record);

            RecordMetadata metadata = ack.get();
            System.out.println("topic INFO: " + metadata.topic() + " | " + metadata.offset() + " | " + metadata.partition());
            System.out.println("Published Message: " + message.getContent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void close(){
    	syncProd.close();
    }

}
