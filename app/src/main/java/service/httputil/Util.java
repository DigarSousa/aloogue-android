package service.httputil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.List;

public class Util {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> Object fromJson(String json, Class<T> tClass, Class teh) throws JSONException, IOException {
        Object jsonToken = json.isEmpty() ? new JSONObject() : new JSONTokener(json).nextValue();

        if (jsonToken instanceof JSONObject) {
            return objectMapper.readValue(json, tClass);
        } else if (jsonToken instanceof JSONArray) {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, teh));
        }
        return null;
    }
}
