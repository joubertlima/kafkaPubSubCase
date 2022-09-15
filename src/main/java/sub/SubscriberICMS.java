package sub;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;
import util.Tribute;

public class SubscriberICMS extends Subscriber{

    public SubscriberICMS(){
        super();
    }

    @Override
    protected void consume(ConsumerRecords<String, Tribute> records) {
        for(ConsumerRecord<String,Tribute> record:records){
            if(record.topic().equals(Constants.icmsTopic)){
                Tribute value = record.value();
                System.out.println("Printing ICMS information of any type (ERROR, WARNING and CORRECT) and any company... " + value.getType() + " | " +
                        value.getTitle() + " | " + value.getCompany());

            }
        }
    }
}
