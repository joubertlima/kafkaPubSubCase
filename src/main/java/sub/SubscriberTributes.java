package sub;

import com.google.gson.JsonObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;

import java.util.HashMap;
import java.util.Map;

public class SubscriberTributes extends Subscriber{

    private Map<String, Integer> tributes;

    public SubscriberTributes() {
        super();
        tributes = new HashMap<String, Integer>(Constants.tributes.length);
        for(String tribute:Constants.tributes)
            tributes.put(tribute,0);
    }

    @Override
    protected void consume(ConsumerRecords<String, JsonObject> records) {
        for (ConsumerRecord<String, JsonObject> record : records) {
            boolean b = false;

            if(!record.value().get("type").equals("IPTU_ERROR") || !record.value().get("type").equals("COFINS_ERROR")
                    ||!record.value().get("type").equals("ICMS_ERROR")){
                String tribute = record.topic();
                String value = record.value().get("tribute_value").getAsString();
                int tribute_value = tributes.get(tribute);
                tribute_value += Integer.parseInt(value);
                tributes.put(tribute, tribute_value);

                b =true;
            }

            if(b){
                System.out.println("The current tribute values of all companies...");
                for(String tribute:tributes.keySet())
                    System.out.print("Tribute: " + tribute + " tribute value: " + tributes.get(tribute) + " | ");
                System.out.println();
            }

        }
    }
}
