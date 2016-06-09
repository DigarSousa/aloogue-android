package alugueis.alugueis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
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
    private ProductListManageAdapter productAdapter;
    private FloatingActionButton saveProductsButton;
    private ProgressDialog progressDialog;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_manage_products, frameLayout);

        initializeComponents();
        initializeToolbar();
    }

    private void loadProductList() {
        if (products != null) {
            //lvProducts.destroyDrawingCache();
            //lvProducts.refreshDrawableState();
            productAdapter = new ProductListManageAdapter(context, products, this);
            lvProducts.setAdapter(productAdapter);
            //productAdapter.notifyDataSetChanged();
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

        progressDialog = new ProgressDialog(this);
        context = getApplicationContext();
        products = new ArrayList<Product>();

        lvProducts = (ListView) findViewById(android.R.id.list);

        nameText = (EditText) findViewById(R.id.nameText);
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        saveProductsButton = (FloatingActionButton) findViewById(R.id.saveProductsButton);
        saveProductsButton.setOnClickListener(this);

        //loadProductList();

        Place place = getPlace();
        if (place != null) {
            progressDialog.setMessage("Carregando produtos...");
            new Service(this, progressDialog).find(Product.class, new Pair<String, Object>("id", place.getId())).execute();
        }
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
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Salvando produtos...");

            new Service(this, progressDialog).save(this.products, Product.class).execute();
        } else if (view.equals(addButton)) {
            if (validateEmptyProductName()) {

                Product product = new Product();
                String productName = nameText.getText().toString().trim();
                product.setDescription(productName);
                product.setPlace(getPlace());
                products.add(product);
                productAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onFinishTask(Object result) {

        this.products = (List<Product>) result;

        try {
            StaticUtil.setOject(this, StaticUtil.PRODUCT_LIST, products);
            List removedProducts = productAdapter != null ? productAdapter.getRemovedProducts() : new ArrayList();
            if (!removedProducts.isEmpty()) {
                new Service(new OnFinishTask() {
                    @Override
                    public void onFinishTask(Object result) {
                        progressDialog.dismiss();
                    }
                }, progressDialog).delete(removedProducts, Product.class).execute();
            }else{
                progressDialog.dismiss();
            }
            if(this.lvProducts.getAdapter()== null){
                loadProductList();
            }
            else{
                productAdapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ManageProductsAct.this, MapAct.class);
        finish();
        startActivity(intent);
    }

}
