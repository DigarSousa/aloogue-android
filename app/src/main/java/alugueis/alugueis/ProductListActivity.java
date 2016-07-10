package alugueis.alugueis;

import alugueis.alugueis.adapter.ProductAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.product.ProductRest;
import alugueis.alugueis.util.StaticUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.StdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private static final Integer NEW_ITEM = 1;
    private static final Integer UPDATE_ITEM = 2;

    private List<Product> products;
    private ArrayAdapter<Product> productAdapter;
    private Place place;

    @BindView(R.id.reduced_toolbar)
    Toolbar toolbar;

    @BindView(android.R.id.list)
    ListView listView;

    @BindView(R.id.addProductsButton)
    FloatingActionButton addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        initListView();
        initComponents();
        initPlaceTest();
        initPlace();
        loadProducts();
    }

    private void initListView() {
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(this, products);
        listView.setAdapter(productAdapter);

    }

    private void initPlace() {
        try {
            place = (Place) StaticUtil.readObject(ProductListActivity.this, StaticUtil.PLACE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initPlaceTest() {
        Place place = new Place();
        place.setId(20l);
        try {
            StaticUtil.setOject(this, StaticUtil.PLACE, place);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProducts() {
        ProductRest productRest = StdService.createService(ProductRest.class);
        Call<List<Product>> call = productRest.get(place.getId());
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

    private void initComponents() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (listView.isSelected()) {
                    setRowSelected(view, position);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putSerializable("product", productAdapter.getItem(position));
                    Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, UPDATE_ITEM);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                parent.setSelected(true);
                setRowSelected(view, position);
                addProductButton.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProductFormActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("place", place);
                intent.putExtras(bundle);
                startActivityForResult(intent, NEW_ITEM);
            }
        });
    }

    private void setRowSelected(View view, Integer position) {
        listView.setItemChecked(position, true);
        view.setBackgroundColor(getResources().getColor(R.color.pressed_color));
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
            Product product = (Product) data.getExtras().getSerializable("product");
            if (product != null) {
                products.add(product);
                productAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == UPDATE_ITEM) {
            Product product = (Product) data.getExtras().getSerializable("product");
            if (product != null) {
                products.remove(resultCode);
                products.add(resultCode, product);
                productAdapter.notifyDataSetChanged();
            }
        }
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
                deleteSelections();
                break;
            default:
                return true;
        }
        return true;
    }

    private void deleteSelections() {
        listView.setSelected(false);
        listView.getCheckedItemPositions().clear();
        productAdapter.notifyDataSetChanged();
        addProductButton.setVisibility(View.VISIBLE);
    }
}
