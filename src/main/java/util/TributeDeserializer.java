package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class TributeDeserializer implements Deserializer<Tribute> {

    @Override
    public Tribute deserialize(String topic, byte[] data) {

        ObjectMapper mapper = new ObjectMapper();
        Tribute result = null;

        try {
            result = mapper.readValue(new String(data, "utf-8"), Tribute.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
