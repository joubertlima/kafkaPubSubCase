package util;

import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TributeSerializer implements Serializer<Tribute> {

    @Override
    public byte[] serialize(String topic, Tribute data) {
        byte[] retVal = null;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (IOException e) {
            System.err.println("Problem on JsonObjectSerializer");
            e.printStackTrace();
        }

        return retVal;
    }
}
