package service.httputil;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class Util {
    private static Gson gson = new Gson();

    public static Object fromJson(String json, Class T) throws JSONException {
        Object jsonToken = json.isEmpty() ? new JSONObject() : new JSONTokener(json).nextValue();

        if (jsonToken instanceof JSONObject) {
            return gson.fromJson(json, T);
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

    public static Object fromJsonObject(JSONObject json, Class T) {
        return gson.fromJson(json.toString(), T);
    }
}
