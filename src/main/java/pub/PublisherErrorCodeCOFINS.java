package pub;

import com.google.gson.JsonObject;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import util.Constants;

import java.util.Random;
import java.util.concurrent.Future;

public class PublisherErrorCodeCOFINS extends Publisher{

    public PublisherErrorCodeCOFINS(){
        super();
    }

    @Override
    protected JsonObject mountJson() {
        Random r = new Random();
        int city = r.nextInt(Constants.cities.length);
        int company = r.nextInt(Constants.companies.length);
        JsonObject json = new JsonObject();
        json.addProperty("type", "COFINS_ERROR");
        json.addProperty("title","COFINS of city" + Constants.cities[city]);
        json.addProperty("spider", "spider COFINS - " + Constants.cities[city]);
        json.addProperty("company", Constants.companies[company]);
        json.addProperty("date", java.time.LocalDateTime.now().toString());
        json.addProperty("job_id", r.nextInt(Integer.MAX_VALUE));//internal spider identification
        json.addProperty("error_code", r.nextInt(1000));
        json.addProperty("error_message", "Error " + r.nextInt(1000));
        //any other json content is appended here...
        return json;
    }

    @Override
    protected void publish(JsonObject json) {

        ProducerRecord<String,JsonObject> record = new ProducerRecord<String,JsonObject>(topic, "ERROR:" + Constants.customizedKeyTagCofins+ ":" + uniqueID, json);
        Future<RecordMetadata> ack = spiderProd.send(record);
        try {
            RecordMetadata metadata = ack.get();
            System.out.println("ERROR json produced: " + metadata.topic() + " | " + metadata.offset() + " | " + metadata.partition());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
