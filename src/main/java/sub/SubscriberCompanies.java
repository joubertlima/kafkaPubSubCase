package sub;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;
import util.Tribute;

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
    protected void consume(ConsumerRecords<String, Tribute> records) {
        for (ConsumerRecord<String, Tribute> record : records) {
            boolean b = false;

            if(!record.value().getType().equals("IPTU_ERROR") || !record.value().getType().equals("COFINS_ERROR")
            ||!record.value().getType().equals("ICMS_ERROR")){
                String company = record.value().getCompany();
                int value = record.value().getTributeValue();
                int tribute_value = companies.get(company);
                tribute_value += value;
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