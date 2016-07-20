package alugueis.alugueis.adapter;

import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    private List<Product> productList;
    private List<Integer> selectedPositions;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_list_adapter, parent, false);

        ProductClickListener productClickListener = new ProductClickListener() {
            @Override
            public void onProductClick(View v, Integer position) {
                productClickTrigger(v, position);
            }

            @Override
            public void onProductSelect(View v, Integer position) {
                productLongClickTrigger(v, position);
            }
        };
        return new ProductHolder(view, productClickListener);
    }


    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
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


    private void productClickTrigger(View view, Integer position) {

    }


    private void productLongClickTrigger(View view, Integer position) {
    }
}
