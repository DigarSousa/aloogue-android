package service;

import alugueis.alugueis.util.ServerConnection;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;

public class LoginService extends AsyncTask<Void, Boolean, Boolean> {
    private String login;

    public LoginService(String email, String password) {
        this.login = email + ";" + password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ServerConnection serviceConnection;
        String resposne = "false";
        OutputStreamWriter out;
        InputStreamReader in;
        try {
            serviceConnection = new ServerConnection(ConstantsService.USER, ConstantsService.POST);
            in = serviceConnection.getReader();
            out = serviceConnection.getWriter();

            out.write(login);
            out.flush();
            out.close();
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