package alugueis.alugueis;

import alugueis.alugueis.adapter.AdapterCallback;
import alugueis.alugueis.adapter.ProductAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.product.ProductService;
import alugueis.alugueis.util.StaticUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import alugueis.alugueis.services.StdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private static final Integer NEW_ITEM = 1;
    private static final Integer UPDATE_ITEM = 2;

    private List<Product> products;
    private ProductAdapter productAdapter;
    private Place place;
    private Bundle params;

    @BindView(R.id.reduced_toolbar)
    Toolbar toolbar;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.addProductsButton)
    FloatingActionButton addProductButton;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initValues();
        initComponents();
        loadProducts();
    }


    private void initValues() {
        params = new Bundle();
        products = new ArrayList<>();

        AdapterCallback adapterCallback = new AdapterCallback() {
            @Override
            public void onAdapterClick(Bundle bundle) {
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, UPDATE_ITEM);
            }

            @Override
            public void onAdapterSelectChange(Bundle bundle) {
                Integer selectionsSize = bundle.getInt("selectionsSize");
                if (selectionsSize > 0) {
                    setSelectionMode(true);

                } else {
                    setSelectionMode(false);
                }
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productAdapter = new ProductAdapter(products, adapterCallback);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productAdapter);

        try {
            place = (Place) StaticUtil.readObject(ProductListActivity.this, StaticUtil.PLACE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                params.remove("product");
                params.putSerializable("place", place);
                intent.putExtras(params);
                startActivityForResult(intent, NEW_ITEM);
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        setSelectionMode(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.produtct_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                deleteProducts();
                break;
            case R.id.cancel_option:
                onBackPressed();
                break;
            default:
                return true;
        }
        return true;
    }

    private void setSelectionMode(Boolean isInSelectionMode) {
        if (isInSelectionMode) {
            addProductButton.setVisibility(View.INVISIBLE);
        } else {
            addProductButton.setVisibility(View.VISIBLE);
        }
        menu.findItem(R.id.delete_action).setVisible(isInSelectionMode);
        menu.findItem(R.id.cancel_option).setVisible(isInSelectionMode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NEW_ITEM) {
            Product product = (Product) data.getExtras().getSerializable("product");
            if (product != null) {
                products.add(product);
                productAdapter.notifyItemInserted(products.size() - 1);
            }
        }

        if (requestCode == UPDATE_ITEM) {
            Product product = (Product) data.getExtras().getSerializable("product");
            if (product != null) {
                products.remove(resultCode);
                products.add(resultCode, product);
                productAdapter.notifyItemChanged(resultCode);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (productAdapter.getSelectedItems().size() > 0) {
            productAdapter.clearSelections();
            return;
        }
        super.onBackPressed();
    }

    private void loadProducts() {
        ProductService productService = StdService.createService(ProductService.class);
        Call<List<Product>> call = productService.get(place.getId());
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products.addAll(response.body());
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductListActivity.this, getString(R.string.loadProductError), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void deleteProducts() {
        ProductService productService = StdService.createService(ProductService.class);

        Call<ResponseBody> call = productService.delete(productAdapter.getSelectedItems());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                productAdapter.removeSelectedItems();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
