package util;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class TributeSerializerJson implements Serializer<Tribute> {

    @Override
    public byte[] serialize(String topic, Tribute data) {
        byte[] retVal = null;

        Gson gson = new Gson();

        try {
            String temp = gson.toJson(data);
            retVal = temp.getBytes(StandardCharsets.UTF_8);

        } catch (Exception e) {
            System.err.println("Problem on TributeSerializerJson");
            e.printStackTrace();
        }

        return retVal;
    }
}
