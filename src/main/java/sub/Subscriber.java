package sub;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import util.Constants;
import util.Tribute;
import util.TributeDeserializer;

import java.time.Duration;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Subscriber implements Runnable{

    protected Consumer<String, Tribute> spiderCon;
    protected Collection<String> topics;
    private AtomicBoolean flag;
    private Properties props;

    public Subscriber(){
        flag = new AtomicBoolean(false);
    }

    public void configure(String name, String url, Collection<String> topics){
        this.topics = topics;

        props= new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, name);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TributeDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, name);
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        spiderCon = new KafkaConsumer<String, Tribute>(props);
        spiderCon.subscribe(topics);

    }

    @Override
    public void run() {

        try {
            while (!flag.get()) {

                ConsumerRecords<String, Tribute> records = spiderCon.poll(Duration.ofNanos(Long.MAX_VALUE));
                if (!records.isEmpty()) consume(records);

            }
        }catch(Exception e){
            spiderCon.close();
        }


    }

    public void stop(){
        flag.set(true);
    }

    protected abstract void consume(ConsumerRecords<String, Tribute> records);
}
