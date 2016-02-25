package service.httputil;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<Object> fromJsonArray(String json, Class T) throws JSONException {
        Gson gson = new Gson();
        JSONArray jsonArray = new JSONArray(json);

        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            objects.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), T));
        }
        return objects;
    }


}
