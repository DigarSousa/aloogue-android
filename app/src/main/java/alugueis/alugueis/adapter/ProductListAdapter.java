package alugueis.alugueis.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;

public class ProductListAdapter extends ArrayAdapter<Product>{

    private ArrayList<Product> productList;
    private Context context;
    private Activity fromActivity;

    public ProductListAdapter(Context context, int textViewResourceId, ArrayList<Product> productList) {
        super(context, textViewResourceId, productList);
        this.productList = productList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final int pos = position;

        //if (v == null) {
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = vi.inflate(R.layout.adapter_product_list, null);

        //holder = new ViewHolder();

        if (productList.size() > 0) {
            //Product name
            final Product product = productList.get(position);
            final TextView productName = (TextView) v.findViewById(R.id.productName);

            try {
                if (productName != null)
                    productName.setText(String.valueOf(product.getDescription()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //}
        return v;
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }
}

