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
import butterknife.BindView;
import butterknife.ButterKnife;
import service.httputil.OnFinishTask;
import service.httputil.Service;

public class ManageProductsAct extends DashboardNavAct implements View.OnClickListener {

    @BindView(android.R.id.list)
    ListView lvProducts;
    private List<Product> products;
    private ProductListManageAdapter productAdapter;
    @BindView(R.id.addProductsButton)
    FloatingActionButton addProductButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_manage_products, frameLayout);
        ButterKnife.bind(this);

        mainToolbar.setTitle(getString(R.string.productsTitle));
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
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    addProductButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    return true;
                }
            });
            lvProducts.setAdapter(productAdapter);
        }
    }

    private void initializeComponents() {
        addProductButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.equals(addProductButton)) {
            this.products = new ArrayList<>();
            //MOCK
            for (int i = 0; i < 20; i++) {
                Product p = new Product();
                p.setDescription("Nome do Produto");
                this.products.add(p);
                loadProductList();
            }
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
