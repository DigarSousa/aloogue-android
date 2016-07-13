package alugueis.alugueis;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alugueis.alugueis.adapter.ProductAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.product.ProductRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.StdService;


public class ProductListFragment extends Fragment {

    private View view;
    ListView listView;
    private List<Product> products;
    private ArrayAdapter<Product> productAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_list_view_content, container, false);
        listView = (ListView) container.findViewById(android.R.id.list);
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), products);
        listView.setAdapter(productAdapter);
        initializeAttributes();
        return view;
    }

    private void initializeAttributes() {
        Bundle bundle = this.getArguments();
        Place place = (Place) bundle.getSerializable("place");
        loadProducts(place);

    }


    private void loadProducts(final Place place) {
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
                Toast.makeText(getContext(), getString(R.string.loadProductError), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }


}
