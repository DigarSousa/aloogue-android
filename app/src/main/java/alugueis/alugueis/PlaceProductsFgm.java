package alugueis.alugueis;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import alugueis.alugueis.adapter.ProductListAdapter;
import alugueis.alugueis.adapter.ProductListManageAdapter;
import alugueis.alugueis.model.Product;
import service.httputil.Service;


public class PlaceProductsFgm extends Fragment {

    private View view;
    private ListView lvProducts;
    private ArrayList<Product> products;
    private ProductListAdapter productAdapter;
    private Context context;

    public PlaceProductsFgm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_place_products, container, false);

        initializeAttributes();
        initializeComponents();

        return view;
    }

    private void initializeAttributes() {
        Bundle bundle = this.getArguments();

        ArrayList<Product> products = new ArrayList<>();
        List<Product> prod = (List<Product>) bundle.get("products");
        products.addAll(prod);

        lvProducts = (ListView) view.findViewById(android.R.id.list);

        lvProducts.destroyDrawingCache();
        lvProducts.refreshDrawableState();
        productAdapter = new ProductListAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, products);
        lvProducts.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private void initializeComponents() {

    }


}
