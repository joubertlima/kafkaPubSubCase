package pub;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import util.Constants;
import util.LoadProperties;
import util.Tribute;
import util.TributeSerializerJson;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Publisher implements Runnable{

    protected Producer<String, Tribute> spiderProd;
    protected String topic;
    protected int uniqueID;
    protected Tribute oneTribute;

    public Publisher(){
        
    }

    public void configure(String name, String topic, int uniqueID){

        this.topic = topic;
        this.uniqueID = uniqueID;

        Properties props= LoadProperties.loadConfig("client.config");
        if( props == null) props = new Properties();
        //props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, url);

        props.put(ProducerConfig.CLIENT_ID_CONFIG, name);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, io.confluent.kafka.serializers.KafkaJsonSerializer.class);
        //props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10000);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, Integer.MAX_VALUE);



        //customized configurations for any sort of publisher are added on constructor parameters
        spiderProd = new KafkaProducer<String, Tribute>(props);

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

        oneTribute = new Tribute();
        publish(mountTribute());

        spiderProd.close();
    }

    //mount the common part of a tribute
    protected Tribute mountTribute(){
        Random r = new Random();
        int company = r.nextInt(Constants.companies.length);

        oneTribute.setCompany(Constants.companies[company]);
        oneTribute.setDate(java.time.LocalDateTime.now().toString());
        oneTribute.setJobID(r.nextInt(Integer.MAX_VALUE));
        oneTribute.setCorrect_error_warning_code(r.nextInt(1000));
        oneTribute.setTributeValue(r.nextInt());

        return oneTribute;
    }

    //submit a tribute object (iptu, icms, cofins or any other) to any topic using any customized submission strategy
    protected  abstract void publish(Tribute tribute);


}
