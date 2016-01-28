package service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.common.eventbus.AsyncEventBus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import alugueis.alugueis.MapAct;
import alugueis.alugueis.model.UserApp;

/**
 * Created by edgar on 23/12/15.
 */
public class SignUpService extends AsyncTask<Void, Boolean, Void> {
    private UserApp userApp;
    private Context context;

    public SignUpService(UserApp userApp, Context context) {
        this.userApp = userApp;
        this.context = context;
    }


    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection connection;
        OutputStreamWriter out;
        InputStreamReader in;
        BufferedReader reader;
        Gson gson = new GsonBuilder().serializeNulls().create();

        URL url;
        try {
            userApp.setPicture(null);
            String json = gson.toJson(userApp, UserApp.class);

            url = new URL(ConstantsService.USER);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty(ConstantsService.CONTENT, ConstantsService.JSON);
            connection.setRequestMethod(ConstantsService.POST);

            out = new OutputStreamWriter(connection.getOutputStream());

            out.write(json);
            out.flush();
            out.close();

            String line;
            in = new InputStreamReader(connection.getInputStream());
            reader = new BufferedReader(in);
            StringBuilder sb = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Intent intent = new Intent(context, MapAct.class);
        context.startActivity(intent);
    }
}
