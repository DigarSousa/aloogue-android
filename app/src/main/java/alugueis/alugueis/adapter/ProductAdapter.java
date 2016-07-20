package alugueis.alugueis.adapter;

import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holder> {
    List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_list_adapter, parent, false);

        ProductClickListener productClickListener = new ProductClickListener() {
            @Override
            public void onProductClick(View v) {
                v.setBackgroundColor(v.getResources().getColor(R.color.colorPrimaryDark));
            }

            @Override
            public void onProductSelect(View v) {
                v.setBackgroundColor(v.getResources().getColor(R.color.colorPrimaryDark));
            }
        };

        return new Holder(view, productClickListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Product product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(product.getPrice() != null ? product.getPrice().toString() : "0.00");
        holder.productPeriod.setText(product.getRentType());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ProductClickListener productClickListener;

        Holder(View itemView, ProductClickListener productClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.productClickListener = productClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.productDescription)
        TextView productDescription;
        @BindView(R.id.productPrice)
        TextView productPrice;
        @BindView(R.id.productPeriod)
        TextView productPeriod;

        @Override
        public void onClick(View v) {
            productClickListener.onProductClick(v);
        }

        @Override
        public boolean onLongClick(View v) {
            productClickListener.onProductSelect(v);
            return true;
        }
    }


    interface ProductClickListener {
        void onProductClick(View v);

        void onProductSelect(View v);
    }
}
