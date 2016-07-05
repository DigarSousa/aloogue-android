package alugueis.alugueis.adapter;

import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> objects) {
        super(context, R.layout.product_list_adapter, objects);
        this.productList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Product product = getItem(position);
        Holder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.product_list_adapter, parent, false);

            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //MOCK
        holder.productName.setText(product.getName());
        holder.productDescription.setText("Lorem Ipsum a vida toda asiuhaas");
        holder.productPrice.setText("R$10");
        holder.productPeriod.setText("SEMANA");

        return convertView;
    }

    @Override
    public int getCount() {
        return this.productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class Holder {
        Holder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.productDescription)
        TextView productDescription;
        @BindView(R.id.productPrice)
        TextView productPrice;
        @BindView(R.id.productPeriod)
        TextView productPeriod;
    }
}

