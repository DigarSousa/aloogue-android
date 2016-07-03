package alugueis.alugueis.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import alugueis.alugueis.R;
import alugueis.alugueis.listener.OnItemProductClickListener;
import alugueis.alugueis.model.Product;

public class ProductListManageAdapter extends BaseAdapter {

    private List<Product> productList;
    private List<Product> removedProducts;
    private Context context;
    private Activity fromActivity;
    private LayoutInflater vi;
    private OnItemProductClickListener onItemClick;

    public ProductListManageAdapter(Context context,
                                    List<Product> productList,
                                    Activity fromActivity) {

        this.productList = productList;
        this.context = context;
        this.fromActivity = fromActivity;
        removedProducts = new ArrayList<>();
        this.onItemClick = onItemClick;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            this.vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.adapter_product_list, null);

            holder.productName = (TextView) convertView.findViewById(R.id.productName);
            holder.productDescription = (TextView) convertView.findViewById(R.id.productDescription);
            holder.productPrice = (TextView) convertView.findViewById(R.id.productPrice);
            holder.productPeriod = (TextView) convertView.findViewById(R.id.productPeriod);
            holder.productName.setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        //MOCK
        holder.productName.setText(productList.get(position).getDescription());
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

    public class Holder {

        TextView productName;
        TextView productDescription;
        TextView productPrice;
        TextView productPeriod;
    }
}

