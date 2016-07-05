package alugueis.alugueis;

import alugueis.alugueis.adapter.ProductAdapter;
import alugueis.alugueis.model.Product;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends DashboardNavAct {

    private static final Integer NEW_ITEM = 1;
    private static final Integer UPDATE_ITEM = 2;

    @BindView(android.R.id.list)
    ListView listView;

    @BindView(R.id.addProductsButton)
    FloatingActionButton addProductButton;

    private List<Product> products;
    private ArrayAdapter<Product> productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.product_list_activity, frameLayout);
        ButterKnife.bind(this);

        mainToolbar.setTitle(getString(R.string.productsTitle));

        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, products);
        listView.setAdapter(productAdapter);
        initComponents();
    }

    private void initComponents() {
     /*   ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);
        viewGroup.addView(progressBar);
*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", i);
                bundle.putSerializable("product", productAdapter.getItem(i));
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                startActivityForResult(intent, UPDATE_ITEM, bundle);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                addProductButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                return true;
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                startActivityForResult(intent, NEW_ITEM);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProductListActivity.this, MapAct.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_ITEM) {
            products.add((Product) data.getExtras().getSerializable("product"));
            productAdapter.notifyDataSetChanged();
        }

        if (requestCode == UPDATE_ITEM) {
            products.add(requestCode, (Product) data.getExtras().getSerializable("product"));
            productAdapter.notifyDataSetChanged();
        }
    }
}
