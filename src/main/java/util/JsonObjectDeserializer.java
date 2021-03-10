package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class JsonObjectDeserializer implements Deserializer<JsonObject> {

    @Override
    public JsonObject deserialize(String topic, byte[] data) {

        ObjectMapper mapper = new ObjectMapper();
        JsonObject result = null;

        try {
            result = mapper.readValue(new String(data, "utf-8"), JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
