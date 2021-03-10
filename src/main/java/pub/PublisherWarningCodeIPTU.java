package pub;

import com.google.gson.JsonObject;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import util.Constants;

import java.util.Random;
import java.util.concurrent.Future;

public class PublisherWarningCodeIPTU extends Publisher{

    public PublisherWarningCodeIPTU(){
        super();
    }

    @Override
    protected JsonObject mountJson() {
        Random r = new Random();
        int city = r.nextInt(Constants.cities.length);
        int company = r.nextInt(Constants.companies.length);
        JsonObject json = new JsonObject();
        json.addProperty("type", "IPTU_WARNING");
        json.addProperty("title","IPTU of city" + Constants.cities[city]);
        json.addProperty("spider", "spider IPTU - " + Constants.cities[city]);
        json.addProperty("company", Constants.companies[company]);
        json.addProperty("date", java.time.LocalDateTime.now().toString());
        json.addProperty("job_id", r.nextInt(Integer.MAX_VALUE));//internal spider identification
        json.addProperty("warning_code", r.nextInt(1000));
        json.addProperty("warning_message", "Warning " + r.nextInt(1000));
        json.addProperty("tribute_value", r.nextInt());
        //any other json content is appended here...
        return json;
    }

    @Override
    protected void publish(JsonObject json) {
        ProducerRecord<String,JsonObject> record = new ProducerRecord<String,JsonObject>(topic, "WARNING:" + Constants.customizedKeyTagIptu+ ":" + uniqueID, json);
        Future<RecordMetadata> ack = spiderProd.send(record);
        try {
            RecordMetadata metadata = ack.get();
            System.out.println("WARNING json produced: " + metadata.topic() + " | " + metadata.offset() + " | " + metadata.partition());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
