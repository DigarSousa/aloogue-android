package service;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbRequest;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import alugueis.alugueis.MapAct;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.ServerConnection;
import alugueis.alugueis.util.StaticUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;


public class ServiceSignup extends AsyncTask<Void, Void, UserApp> {
    private UserApp userApp;
    private Context context;

    public ServiceSignup(UserApp userApp, Context context) {
        this.userApp = userApp;
        this.context = context;
    }

    @Override
    protected UserApp doInBackground(Void... params) {
        ServerConnection serverConnection;
        Gson gson = new Gson();
        OutputStreamWriter out;
        String json = gson.toJson(userApp, UserApp.class);
        String response = "";
        try {
            serverConnection = new ServerConnection(ConstantsService.USER, ConstantsService.POST);

            out = serverConnection.getWriter();

            out.write(json);
            out.flush();
            out.close();
            response = serverConnection.getResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gson.fromJson(response, UserApp.class);
    }

    @Override
    protected void onPostExecute(UserApp createdUser) {
        try {
            StaticUtil.setOject(context, StaticUtil.LOGGED_USER, createdUser);
            Intent intent = new Intent(context, MapAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        } catch (IOException e) {
            Toast.makeText(context, "Houve uma falha ao realizar seu cadastro. :( ", Toast.LENGTH_SHORT).show();
        }
    }
}
