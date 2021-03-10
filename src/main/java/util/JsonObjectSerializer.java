package util;

import com.google.gson.JsonObject;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonObjectSerializer implements Serializer<JsonObject> {

    @Override
    public byte[] serialize(String topic, JsonObject data) {
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
