package alugueis.alugueis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import alugueis.alugueis.adapter.ProductListManageAdapter;
import alugueis.alugueis.listener.OnItemProductClickListener;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.util.StaticUtil;
import service.httputil.OnFinishTask;
import service.httputil.Service;

public class ManageProductsAct extends DashboardNavAct implements View.OnClickListener, OnFinishTask {

    private ListView lvProducts;
    private List<Product> products;
    private List<Product> deleted;
    private ProductListManageAdapter productAdapter;
    private FloatingActionButton addProductButton;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_manage_products, frameLayout);

        mainToolbar.setTitle("Seus produtos");
        progressDialog = new ProgressDialog(this);
        products = new ArrayList<>();
        initializeComponents();
    }

    private void loadProductList() {

        if (products != null) {
            productAdapter = new ProductListManageAdapter(this, products, this);
            lvProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    deleted.add(products.get(position));
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    addProductButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    return true;
                }
            });
            lvProducts.setAdapter(productAdapter);
        }
    }

    private Place getPlace() {

        try {
            return (Place) StaticUtil.readObject(this, StaticUtil.PLACE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void initializeComponents() {

        lvProducts = (ListView) findViewById(android.R.id.list);
        addProductButton = (FloatingActionButton) findViewById(R.id.addProductsButton);
        addProductButton.setOnClickListener(this);

        try {
            Place place = (Place) StaticUtil.readObject(this, StaticUtil.PLACE);
            if (place != null) {
                progressDialog.setMessage("Carregando produtos...");
                new Service(this, progressDialog).find(Product.class, new Pair<String, Object>("id", place.getId())).execute();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        if (view.equals(addProductButton)) {
            //chamar a sua activity aqui
        }
    }

    @Override
    public void onFinishTask (Object result) {

        this.products = (List<Product>) result;

        this.products = new ArrayList<>();
        //MOCK
        for(int i = 0; i < 20; i++){
            Product p = new Product();
            p.setDescription("Nome do Produto");
            this.products.add(p);
        }

        try {
            StaticUtil.setOject(this, StaticUtil.PRODUCT_LIST, products);
            progressDialog.dismiss();
            if (this.lvProducts.getAdapter() == null) {
                loadProductList();
            } else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            products = (List<Product>) data.getExtras().get("products");
            loadProductList();
        }
    }
}
