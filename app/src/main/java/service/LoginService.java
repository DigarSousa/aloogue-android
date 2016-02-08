package service;

import alugueis.alugueis.util.ServerConnection;
import alugueis.alugueis.util.ServerUtil;

import android.os.AsyncTask;
import android.util.Pair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LoginService extends AsyncTask<Void, Boolean, Boolean> {
    private String email;
    private String password;

    public LoginService(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ServerConnection serviceConnection;
        String resposne = "";
        try {

            String url = ServerUtil.buildUrl(ConstantsService.USER,
                    new Pair<>("email", email),
                    new Pair<>("password", password));
            serviceConnection = new ServerConnection(url, ConstantsService.GET);
            serviceConnection.connect();
            resposne = serviceConnection.getResponse();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Boolean.valueOf(resposne);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}