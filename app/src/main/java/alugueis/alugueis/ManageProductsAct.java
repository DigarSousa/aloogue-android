package alugueis.alugueis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import alugueis.alugueis.adapter.ProductListAdapter;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.util.Util;

/**
 * Created by Pedreduardo on 19/01/2016.
 */
public class ManageProductsAct extends DashboardNavAct implements View.OnClickListener {

    private Context context;
    private UserApp loggedUser;
    private EditText nameText;
    private ListView lvProducts;
    private ArrayList<Product> products;
    private RelativeLayout productsArea;
    private ProductListAdapter productAdapter;
    private FloatingActionButton saveProductsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utilizado para levar o layout da activity para o pai (nav drawer)
        getLayoutInflater().inflate(R.layout.activity_manage_products, frameLayout);

        initializeAttributes();


        getLogged();

        initializeToolbar();
        initializeComponents();
        getProductList();
    }

    private void getProductList() {
        Intent it = getIntent();
        Bundle extras = it.getExtras();

        if (extras != null) {
            products = (ArrayList<Product>)extras.get("products");
            loadProductList();
        }
    }

    private void loadProductList() {
        if (products.size() == 0) {
            productsArea.setVisibility(View.INVISIBLE);
        } else {

            //lvProducts.setAdapter(null);
            lvProducts.destroyDrawingCache();
            lvProducts.refreshDrawableState();
            lvProducts.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }

    }

    private void initializeAttributes() {
        context = getApplicationContext();
        loggedUser = new UserApp();
        //todo: Buscar produtos do cliente aqui
        products = new ArrayList<Product>();
        productAdapter = new ProductListAdapter(context, android.R.layout.simple_list_item_1, products, this);
    }

    private void getLogged() {
        try {
            loggedUser = (UserApp) StaticUtil.readObject(context, StaticUtil.LOGGED_USER);
        } catch (Exception ex) {
        }
    }

    private void initializeToolbar() {
        mainToolbar.setTitle("Seus produtos");
    }

    private void initializeComponents() {

        lvProducts = (ListView) findViewById(android.R.id.list);
        productsArea = (RelativeLayout) findViewById(R.id.productsArea);

        nameText = (EditText) findViewById(R.id.nameText);
        nameText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= nameText.getRight() - nameText.getTotalPaddingRight()) {

                        if (validateEmptyProductName()) {

                            Product productToAdd = new Product();
                            String productName = nameText.getText().toString().trim();
                            productToAdd.setDescription(productName);
                            products.add(productToAdd);
                            loadProductList();
                        }

                        event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
                        return false;
                    }
                }
                return false;
            }

        });

        saveProductsButton = (FloatingActionButton) findViewById(R.id.saveProductsButton);
        saveProductsButton.setOnClickListener(this);
    }

    private boolean validateEmptyProductName() {
        String productName = nameText.getText().toString().trim();
        if (productName.isEmpty()) {
            Util.createToast(context, getResources().getString(R.string.emptyProductName));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(saveProductsButton)) {
            try {
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    public ProgressDialog pd;

                    @Override
                    protected void onPreExecute() {
                        pd = new ProgressDialog(ManageProductsAct.this);
                        pd.setMessage("Atualizando seus produtos");
                        pd.setCancelable(false);
                        pd.setIndeterminate(true);
                        pd.show();
                    }

                    @Override
                    protected Void doInBackground(Void... arg0) {
                        //Saving place products
                        for (Product p : products) {
                            p.save();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        if (pd != null) {
                            pd.dismiss();
                            Util.createToast(context, "Produtos atualizados com sucesso! (:");
                        }
                    }
                };
                task.execute((Void[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
