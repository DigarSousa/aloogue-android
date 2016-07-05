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

    @BindView(android.R.id.list)
    ListView listView;

    @BindView(R.id.addProductsButton)
    FloatingActionButton addProductButton;

    private List<Product> products;
    private ArrayAdapter<Product> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.product_list_activity, frameLayout);
        ButterKnife.bind(this);

        mainToolbar.setTitle(getString(R.string.productsTitle));

        products = new ArrayList<>();
        arrayAdapter = new ProductAdapter(this, products);
        listView.setAdapter(arrayAdapter);
        initComponents();
    }

    private void loadProductList() {
        if (products != null) {
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    addProductButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    return true;
                }
            });
        }
    }

    private void initComponents() {
     /*   ProgressBar progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        listView.setEmptyView(progressBar);
        viewGroup.addView(progressBar);
*/

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                startActivityForResult(intent, 1);
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
        if (requestCode == 1) {
            products.add((Product) data.getExtras().getParcelable("product"));
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
