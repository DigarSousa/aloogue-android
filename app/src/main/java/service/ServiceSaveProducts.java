package service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import alugueis.alugueis.model.Product;
import alugueis.alugueis.util.ServerConnection;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.util.URLBuilder;
import alugueis.alugueis.util.Util;

public class ServiceSaveProducts extends AsyncTask<Void, Void, List<Product>> {
    private ProgressDialog pd;
    private Context context;
    private List<Product> products;

    public ServiceSaveProducts(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setMessage("Atualizando seus produtos");
        pd.setCancelable(false);
        pd.setIndeterminate(true);
        pd.show();
    }

    @Override
    protected List<Product> doInBackground(Void... arg0) {
        OutputStreamWriter out;
        Gson gson = new Gson();
        String json = gson.toJson(products);
        String url = new URLBuilder(Product.class).build();

        ServerConnection serverConnection = new ServerConnection(url, ConstantsService.POST);
        String response = "";
        try {
            out = serverConnection.getWriter();
            out.write(json);
            out.flush();
            out.close();
            response = serverConnection.getResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type iterable = new TypeToken<ArrayList<Product>>() {
        }.getType();
        List<Product> products = gson.fromJson(response, iterable);
        return products;
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        try {
            StaticUtil.setOject(context, StaticUtil.PRODUCT_LIST, products);
            if (pd != null) {
                pd.dismiss();
                Util.createToast(context, "Produtos atualizados com sucesso! (:");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}