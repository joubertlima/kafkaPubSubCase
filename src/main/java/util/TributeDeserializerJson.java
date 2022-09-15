package util;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class TributeDeserializerJson implements Deserializer<Tribute> {

    @Override
    public Tribute deserialize(String topic, byte[] data) {

        Gson gson = new Gson();
        Tribute result = null;

        try {
            result = gson.fromJson(new String(data, "utf-8"), Tribute.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
