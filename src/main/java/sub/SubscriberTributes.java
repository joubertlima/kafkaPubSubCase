package sub;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;
import util.Tribute;

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
    protected void consume(ConsumerRecords<String, Tribute> records) {
        boolean b = false;
        for (ConsumerRecord<String, Tribute> record : records) {


            if (!record.value().getType().equals("IPTU_ERROR") || !record.value().getType().equals("COFINS_ERROR")
                    || !record.value().getType().equals("ICMS_ERROR")) {
                String tribute = record.topic();
                int value = record.value().getTributeValue();
                int tribute_value = tributes.get(tribute);
                tribute_value += value;
                tributes.put(tribute, tribute_value);

                b = true;
            }
        }

        if(b){
                System.out.println("The current tribute values...");
                for(String tribute:tributes.keySet())
                    System.out.print("Tribute: " + tribute + " tribute value: " + tributes.get(tribute) + " | ");
                System.out.println();

        }
    }
}
