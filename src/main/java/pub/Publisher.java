package pub;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Publisher implements Runnable{

    protected Producer<String, JsonObject> spiderProd;
    protected String topic;
    protected int uniqueID;

    public Publisher(){

    }

    public void configure(String name, String url, String topic, int uniqueID){

        this.topic = topic;
        this.uniqueID = uniqueID;

        Properties props= new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, name);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        //customized configurations for any sort of publisher are added on constructor parameters
        spiderProd = new KafkaProducer<String, JsonObject>(props);
    }

    @Override
    public void run() {
        //wait and produce
        //the wait condition will be removed on production phase
        Random r = new Random();
        try{
            TimeUnit.SECONDS.sleep(r.nextInt(3)+1); //waits 1 to 3 seconds
        }catch (Exception e){
            e.printStackTrace();
        }

        publish(mountJson());

        spiderProd.close();
    }

    //produce any sort of json
    protected abstract JsonObject mountJson();

    //submit a json to any topic using any customized submission strategy
    protected  abstract void publish(JsonObject json);


}
