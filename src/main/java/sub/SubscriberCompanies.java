package sub;

import com.google.gson.JsonObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;

import java.util.HashMap;
import java.util.Map;

public class SubscriberCompanies extends Subscriber {

    private Map<String, Integer> companies;

    public SubscriberCompanies() {
        super();
        companies = new HashMap<String, Integer>(Constants.companies.length);
        for(String company:Constants.companies)
            companies.put(company,0);
    }

    @Override
    protected void consume(ConsumerRecords<String, JsonObject> records) {
        for (ConsumerRecord<String, JsonObject> record : records) {
            boolean b = false;

            if(!record.value().get("type").equals("IPTU_ERROR") || !record.value().get("type").equals("COFINS_ERROR")
            ||!record.value().get("type").equals("ICMS_ERROR")){
                String company = record.value().get("company").getAsString();
                String value = record.value().get("tribute_value").getAsString();
                int tribute_value = companies.get(company);
                tribute_value += Integer.parseInt(value);
                companies.put(company, tribute_value);

                b =true;
            }

            if(b){
                System.out.println("The current tribute values of all companies...");
                for(String company:companies.keySet())
                    System.out.print("Company: " + company + " tribute value: " + companies.get(company) + " | ");
                System.out.println();
            }

        }
    }
}