package service.httputil;

import android.os.AsyncTask;
import android.util.Pair;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;

import service.ConstantsService;


public class Service extends AsyncTask<Void, Void, String> {
    private OnFinishTask onFinishTask;
    private Gson gson;
    private Class T;
    private Object object;
    private String method;
    private Pair[] params;

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
        this.method = ConstantsService.POST;

        return this;
    }

    public Service find(Class T, Pair... params) {
        this.T = T;
        this.params = params;
        this.method = ConstantsService.GET;
        return this;
    }

    public Service findAll(Class T) {
        this.T = T;
        this.method = ConstantsService.GET;
        return this;
    }

    @Override
    protected void onPostExecute(String str) {
        object = gson.fromJson(str, T);
        onFinishTask.onFinishTask(object);
    }
}
