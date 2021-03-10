package sub;

import com.google.gson.JsonObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;

public class SubscriberIPTU extends Subscriber{

    public SubscriberIPTU(){
        super();
    }

    @Override
    protected void consume(ConsumerRecords<String, JsonObject> records) {
        for(ConsumerRecord<String,JsonObject> record:records){
            if(record.topic().equals(Constants.iptuTopic)){
                JsonObject value = record.value();
                System.out.println("Printing IPTU information of any type (ERROR, WARNING and CORRECT) and any company... " + value.get("type").getAsString() + " | " +
                        value.get("title").getAsString() + " | " + value.get("company").getAsString());

            }
        }
    }
}
