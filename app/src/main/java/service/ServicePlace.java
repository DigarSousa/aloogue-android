package service;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;

import alugueis.alugueis.model.Place;
import service.httputil.ConnectionManeger;
import service.httputil.URLBuilder;

public class ServicePlace extends AsyncTask<Void, Void, Place> {
    private Place place;
    private Context context;

    public ServicePlace(Context context, Place place) {
        this.context = context;
        this.place = place;
    }

    @Override
    protected Place doInBackground(Void... params) {
        OutputStreamWriter out;
        Gson gson = new Gson();
        String json = gson.toJson(place, Place.class);
        String response = "";
        try {
            ConnectionManeger connectionManeger;
            connectionManeger = new ConnectionManeger(new URLBuilder(Place.class).build(), ConstantsService.POST);
            out = connectionManeger.getWriter();
            out.write(json);
            out.flush();
            out.close();
            response = connectionManeger.getResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Place place) {
        //todo:abrir o deshboard da loja.
    }
}
