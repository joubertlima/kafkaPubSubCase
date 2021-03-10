package sub;

import com.google.gson.JsonObject;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import util.JsonObjectDeserializer;

import java.time.Duration;
import java.util.Collection;
import java.util.Properties;

public abstract class Subscriber implements Runnable{

    protected Consumer<String, JsonObject> spiderCon;
    protected Collection<String> topics;
    private boolean flag;

    public Subscriber(){
        flag = false;
    }

    public void configure(String name, String url, Collection<String> topics){
        this.topics = topics;

        Properties props= new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, name);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonObjectDeserializer.class);

        spiderCon = new KafkaConsumer<String, JsonObject>(props);
        spiderCon.subscribe(topics);
    }

    @Override
    public void run() {

        while(!flag){
            try{
                ConsumerRecords<String, JsonObject> records = spiderCon.poll(Duration.ZERO);
                if(!records.isEmpty()) consume(records);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        spiderCon.close();
    }

    protected abstract void consume(ConsumerRecords<String, JsonObject> records);
}
