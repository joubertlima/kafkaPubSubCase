package pub;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import util.Constants;
import util.Tribute;

import java.util.Random;
import java.util.concurrent.Future;

public class PublisherErrorCodeICMS extends Publisher{

    public PublisherErrorCodeICMS(){
        super();
    }

    @Override
    protected Tribute mountTribute() {
        Random r = new Random();
        int city = r.nextInt(Constants.cities.length);
        oneTribute.setType("ICMS_ERROR");
        oneTribute.setTitle("ICMS of city " + Constants.cities[city]);
        oneTribute.setSpider("spider ICMS - " + Constants.cities[city]);

        return super.mountTribute();
    }

    @Override
    protected void publish(Tribute tribute) {
        ProducerRecord<String,Tribute> record = new ProducerRecord<String,Tribute>(topic, "ERROR:" + Constants.customizedKeyTagIcms+ ":" + uniqueID, tribute);
        Future<RecordMetadata> ack = spiderProd.send(record);
        try {
            RecordMetadata metadata = ack.get();
            System.out.println("ERROR tribute produced: " + metadata.topic() + " | " + metadata.offset() + " | " + metadata.partition());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
