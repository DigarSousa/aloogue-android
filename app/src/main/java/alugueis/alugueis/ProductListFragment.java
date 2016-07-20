package alugueis.alugueis;


import alugueis.alugueis.adapter.ProductAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.product.ProductRest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.StdService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductListFragment extends Fragment {

    private View view;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private List<Product> products;
    private ProductAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_list_view_content, container, false);

        products = new ArrayList<>();
        productAdapter = new ProductAdapter(products);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);


        Product product1 = new Product();
        product1.setName("produto1");
        product1.setDescription("adfsafsafasfsfsfsfss");
        Product product2 = new Product();
        product2.setName("produto2");
        product2.setDescription("adfsafsafasfsfsfsfss");
        Product product3 = new Product();
        product3.setName("produto3");
        product3.setDescription("adfsafsafasfsfsfsfss");
        Product product4 = new Product();
        product4.setName("produto4");
        product4.setDescription("adfsafsafasfsfsfsfss");
        Product product5 = new Product();
        product5.setName("produto4");
        product5.setDescription("adfsafsafasfsfsfsfss");
        Product product6 = new Product();
        product6.setName("produto4");
        product6.setDescription("adfsafsafasfsfsfsfss");
        Product product7 = new Product();
        product7.setName("produto4");
        product7.setDescription("adfsafsafasfsfsfsfss");
        products.addAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7));

        productAdapter.notifyDataSetChanged();

        //initializeAttributes();
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
