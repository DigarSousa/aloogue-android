package alugueis.alugueis;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import alugueis.alugueis.adapter.ProductListManageAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.util.Util;
import service.httputil.OnFinishTask;
import service.httputil.Service;

public class ManageProductsAct extends DashboardNavAct implements View.OnClickListener, OnFinishTask {

    private Context context;
    private EditText nameText;
    private ListView lvProducts;
    private List<Product> products;
    private RelativeLayout productsArea;
    private ProductListManageAdapter productAdapter;
    private FloatingActionButton saveProductsButton;
    private ProgressDialog progressDialog;
    private Boolean findProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utilizado para levar o layout da activity para o pai (nav drawer)
        getLayoutInflater().inflate(R.layout.activity_manage_products, frameLayout);

        initializeToolbar();
        initializeComponents();
        initializeAttributes();

    }

    private void loadProductList() {
        if (products.isEmpty()) {
            productsArea.setVisibility(View.INVISIBLE);
        } else {

            //lvProducts.setAdapter(null);
            lvProducts.destroyDrawingCache();
            lvProducts.refreshDrawableState();
            productAdapter = new ProductListManageAdapter(context, android.R.layout.simple_list_item_1, products, this);
            lvProducts.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }
    }


    private void initializeAttributes() {
        progressDialog = new ProgressDialog(this);
        context = getApplicationContext();

        try {
            products = (List<Product>) StaticUtil.readObject(this, StaticUtil.PRODUCT_LIST);
            if (products == null || products.isEmpty()) {
                products = new ArrayList<>();

                Place place = getPlace();

                if (place != null) {
                    progressDialog.setMessage("Carregando produtos...");
                    new Service(this, progressDialog).find(Product.class, new Pair<String, Object>("id", place.getId())).execute();
                }

            }
            loadProductList();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Place getPlace() {
        try {
            return (Place) StaticUtil.readObject(context, StaticUtil.PLACE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
        if (view.equals(saveProductsButton)
                && products != null
                && !products.isEmpty()) {

            progressDialog.setMessage("Salvando produtos...");

            Place place = getPlace();
            for (Product produto : products) {
                produto.setPlace(place);

            }
            new Service(this, progressDialog).save(products, Product.class).execute();
        }
    }

    @Override
    public void onFinishTask(Object result) {

        products = (List<Product>) result;

        try {
            StaticUtil.setOject(this, StaticUtil.PRODUCT_LIST, products);
            List removedProducts = productAdapter != null ? productAdapter.getRemovedProducts() : new ArrayList();
            new Service(new OnFinishTask() {
                @Override
                public void onFinishTask(Object result) {
                    progressDialog.dismiss();
                }
            }, progressDialog).delete(removedProducts, Product.class).execute();
            loadProductList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
