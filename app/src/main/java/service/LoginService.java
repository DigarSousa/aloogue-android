package service;

import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.model.UserApp;

import android.os.AsyncTask;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by edgar on 23/12/15.
 */
public class LoginService extends AsyncTask<Void, Boolean, Void> {
    private String httpUrl;

    public LoginService(String httpUrl) {
        this.httpUrl = httpUrl;
    }


    @Override
    protected Void doInBackground(Void... params) {
        HttpURLConnection connection;
        OutputStreamWriter out;
        InputStreamReader in;
        BufferedReader reader;

        URL url;
        try {

            url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty(ConstantsService.CONTENT, ConstantsService.JSON);
            connection.setRequestMethod(ConstantsService.POST);

            out = new OutputStreamWriter(connection.getOutputStream());

            UserApp userApp = new UserApp();
            userApp.setEmail("digarsg@gmail.com");
            Gson jason = new Gson();
            String s = jason.toJson(userApp,UserApp.class);
            out.write(s);
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
}