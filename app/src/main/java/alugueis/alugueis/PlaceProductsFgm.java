package alugueis.alugueis;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import alugueis.alugueis.adapter.ProductListAdapter;
import alugueis.alugueis.adapter.ProductListManageAdapter;
import alugueis.alugueis.model.Product;


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
        //todo: Buscar produtos do cliente aqui
        products = new ArrayList<Product>();
        productAdapter = new ProductListAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, products);
    }

    private void initializeComponents() {

    }


}
