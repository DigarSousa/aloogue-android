package service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import alugueis.alugueis.MapAct;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.StaticUtil;

import java.io.IOException;


public class SignUpService extends AsyncTask<Void, Void, Void> {
    private UserApp userApp;
    private Context context;

    public SignUpService(UserApp userApp, Context context) {
        this.userApp = userApp;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    @Override
    protected void onPostExecute(Void aBoolean) {
        try {
            StaticUtil.setOject(context, StaticUtil.LOGGED_USER, userApp);
            Intent intent = new Intent(context, MapAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        } catch (IOException e) {
            Toast.makeText(context, "Houve uma falha ao realizer seu cadastro :( ", Toast.LENGTH_SHORT).show();
        }
    }
}
