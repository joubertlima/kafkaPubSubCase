package pub_sub;

import java.time.Duration;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;
import util.LoadProperties;

public class Subscriber extends Thread{

    protected Consumer<String, Message> syncCon;
    protected Collection<String> topics;
    private AtomicBoolean canPub;
    private String user;
    private Set<Message> messages;
    private int numAcquires;
    private int count;

    public Subscriber(){
        messages = new TreeSet<Message>();
    }

    public void configure(AtomicBoolean canPub, String user, Collection<String> topics){
        this.topics = topics;
        this.user = user;
        this.canPub = canPub;
        Properties props;
        props= LoadProperties.loadConfig("client.config");
        //props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, user+"sub");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaJsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, user);
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, Message.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        syncCon = new KafkaConsumer<String, Message>(props);
        syncCon.subscribe(topics);

    }

    @Override
    public void run() {
   	
        try {
            while (true) {
            	ConsumerRecords<String, Message> records = syncCon.poll(Duration.ofNanos(Long.MAX_VALUE));
               
            	if(canPub.get()==true)            		
            		if (!records.isEmpty()) consume(records);                    
            	     	
            	
            }
        }catch(Exception e){
            syncCon.close();
        }


    }

    protected void consume(ConsumerRecords<String, Message> records){
    	
    	for(ConsumerRecord<String,Message> record:records)
    		messages.add(record.value());
    	
    	for(Message msg:messages){
			if(msg.getContent().contains(user) && msg.getContent().contains("acquire")){
				String[] splitedMsg = msg.getContent().split(":");
				numAcquires = Integer.parseInt(splitedMsg[2]);
				if(numAcquires>count){
					canPub.set(false);
					count++;
					System.out.println("Consumed..." + msg.getContent());
					Publisher producer = new Publisher();
					producer.configure(canPub, user, topics.iterator().next());
					
					producer.publish(producer.mountMessageRel(count));
					producer.close();
				}
			}    		
    	}
    }
}
