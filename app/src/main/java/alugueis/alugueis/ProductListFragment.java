package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.adapter.AdapterCallback;
import alugueis.alugueis.adapter.ProductAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.product.ProductService;
import alugueis.alugueis.util.StaticUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import alugueis.alugueis.services.StdService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends StandardFragment {
    private static final String TAG = "ProductListFragment";
    private static final Integer NEW_ITEM = 1;
    private static final Integer UPDATE_ITEM = 2;

    private Unbinder unbinder;
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_list_activity, container, false);
        unbinder = ButterKnife.bind(this, view);
        initValues();
        initComponents();
        loadProducts();
        return view;
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    private void initValues() {
        params = new Bundle();
        products = new ArrayList<>();

        AdapterCallback adapterCallback = new AdapterCallback() {
            @Override
            public void onAdapterClick(Bundle bundle) {
                Intent intent = new Intent(getContext(), ProductFormActivity.class);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        productAdapter = new ProductAdapter(products, adapterCallback);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productAdapter);

        try {
            place = (Place) StaticUtil.readObject(getContext(), StaticUtil.PLACE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductFormActivity.class);
                params.remove("product");
                params.putSerializable("place", place);
                intent.putExtras(params);
                startActivityForResult(intent, NEW_ITEM);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        setSelectionMode(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.produtct_toolbar_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                deleteProducts();
                break;
            case R.id.cancel_option:
                getActivity().onBackPressed();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                Log.e(TAG, "Load product error", t);
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


    ProductAdapter getProductAdapter() {
        return productAdapter;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
