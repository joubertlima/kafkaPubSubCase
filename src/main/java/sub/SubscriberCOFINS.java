package sub;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import util.Constants;
import util.Tribute;

public class SubscriberCOFINS extends Subscriber{

    public SubscriberCOFINS(){
        super();
    }

    @Override
    protected void consume(ConsumerRecords<String, Tribute> records) {
        for(ConsumerRecord<String,Tribute> record:records){
            //aqui eu aplico filtros quaisquer do meu domínio
            if(record.topic().equals(Constants.cofinsTopic)){
                Tribute value = record.value();
                System.out.println("Printing COFINS information of any type (ERROR, WARNING and CORRECT) and any company... " + value.getType() + " | " +
                        value.getTitle() + " | " + value.getCompany());

                //produce();

            }
        }
    }
}
