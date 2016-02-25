package service.httputil;

import android.os.AsyncTask;
import android.util.Pair;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

import service.ConstantsService;


public class Service extends AsyncTask<Void, Void, String> {
    private OnFinishTask onFinishTask;
    private Gson gson;
    private Class T;
    private Object object;
    private String method;
    private Pair[] params;

    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String GET = "GET";

    public Service(OnFinishTask onFinishTask) {
        this.onFinishTask = onFinishTask;
    }

    @Override
    protected String doInBackground(Void... voids) {
        ConnectionManeger connection;
        gson = new Gson();
        String response = "";
        try {

            URLBuilder urlBuilder = new URLBuilder(T);
            if (method.equals(ConstantsService.GET)) {
                urlBuilder.putParams(params);
                connection = new ConnectionManeger(urlBuilder.build());
                connection.connect();
            } else {
                String json = gson.toJson(object, T);
                connection = new ConnectionManeger(urlBuilder.build(), method);
                OutputStreamWriter out = connection.getWriter();
                out.write(json);
                out.flush();
                out.close();
            }

            response = connection.getResponse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Service save(Object object, Class T) {
        this.T = T;
        this.object = object;
        this.method = POST;

        return this;
    }

    public Service saveCollection(Collection collection, Class T) {
        this.object = collection;
        this.T = T;
        return this;

    }

    public Service find(Class T, Pair... params) {
        this.T = T;
        this.params = params;
        this.method = GET;
        return this;
    }

    @Override
    protected void onPostExecute(String json) {
        if (method.equals(GET)) {
            try {
                object = Util.fromJsonArray(json,T);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            object = gson.fromJson(json, T);
        }

        onFinishTask.onFinishTask(object);
    }
}
