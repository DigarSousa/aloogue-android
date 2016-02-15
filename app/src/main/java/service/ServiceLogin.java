package service;

import alugueis.alugueis.MapAct;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.ServerConnection;
import alugueis.alugueis.util.ServerUtil;
import alugueis.alugueis.util.StaticUtil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

public class ServiceLogin extends AsyncTask<Void, Boolean, UserApp> {
    private String email;
    private String password;
    private Context context;

    public ServiceLogin(Context context, String email, String password) {
        this.context = context;
        this.email = email;
        this.password = password;
    }

    @Override
    protected UserApp doInBackground(Void... params) {
        ServerConnection serviceConnection;
        Gson gson = new Gson();
        String resposne = "";
        try {

            String url = ServerUtil.buildUrl(ConstantsService.USER,
                    new Pair<>("email", email),
                    new Pair<>("password", password));
            serviceConnection = new ServerConnection(url);
            serviceConnection.connect();
            resposne = serviceConnection.getResponse();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return gson.fromJson(resposne, UserApp.class);
    }

    @Override
    protected void onPostExecute(UserApp loggedUser) {
        if (loggedUser != null) {
            try {
                StaticUtil.setOject(context, StaticUtil.LOGGED_USER, loggedUser);
                Intent intent = new Intent(context, MapAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            } catch (IOException e) {
                Toast.makeText(context, "Houve uma falha ao realizar o login. :( ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Email ou senha inválidos. :( ", Toast.LENGTH_SHORT).show();
        }
    }
}