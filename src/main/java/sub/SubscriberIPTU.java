package sub;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;
import util.Tribute;

public class SubscriberIPTU extends Subscriber{

    public SubscriberIPTU(){
        super();
    }

    @Override
    protected void consume(ConsumerRecords<String, Tribute> records) {
        for(ConsumerRecord<String,Tribute> record:records){
            if(record.topic().equals(Constants.iptuTopic)){
                Tribute value = record.value();
                System.out.println("Printing IPTU information of any type (ERROR, WARNING and CORRECT) and any company... " + value.getType() + " | " +
                        value.getTitle() + " | " + value.getCompany());

            }
        }
    }
}
