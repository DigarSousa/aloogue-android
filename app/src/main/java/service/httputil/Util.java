package service.httputil;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Object fromJson(String json, Class T) throws JSONException, IOException {
        Object jsonToken = json.isEmpty() ? new JSONObject() : new JSONTokener(json).nextValue();

        if (jsonToken instanceof JSONObject) {
            return objectMapper.readValue(json, T);
        } else if (jsonToken instanceof JSONArray) {
            JSONArray jsonArray = new JSONArray(json);
            List<Object> objects = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                objects.add(fromJsonObject(jsonArray.getJSONObject(i), T));
            }
            return objects;
        }
        return null;
    }

    public static Object fromJsonObject(JSONObject json, Class T) throws IOException {
        return objectMapper.readValue(json.toString(),T);
    }
}
