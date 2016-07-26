package alugueis.alugueis.adapter;

import alugueis.alugueis.R;
import alugueis.alugueis.model.Product;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
    private List<Product> productList;
    private AdapterCallback adapterCallback;
    private Bundle args;
    private List<ProductHolder> selectedItems;

    public ProductAdapter(List<Product> productList) {
        this(productList, null);
    }

    public ProductAdapter(List<Product> productList, AdapterCallback adapterClickCallback) {
        this.productList = productList;
        this.adapterCallback = adapterClickCallback;
        selectedItems = new ArrayList<>();
        args = new Bundle();
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_list_item, parent, false);

        return getHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, int position) {
        Product product = productList.get(position);
        holder.bindProduct(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private ProductHolder getHolder(View view) {
        if (adapterCallback != null) {
            ProductClickListener productClickListener = new ProductClickListener() {
                @Override
                public void onProductClick(ProductHolder productHolder, Integer position) {
                    productClickTrigger(productHolder, position);
                }

                @Override
                public void onProductSelect(ProductHolder productHolder) {
                    productLongClickTrigger(productHolder);
                }
            };
            return new ProductHolder(view, productClickListener);
        }
        return new ProductHolder(view);
    }


    private void productClickTrigger(ProductHolder holder, Integer position) {
        if (!selectedItems.isEmpty()) {
            productLongClickTrigger(holder);
        } else {
            args.putInt("position", position);
            args.putSerializable("product", productList.get(position));
            adapterCallback.onAdapterClick(args);
        }
    }


    private void productLongClickTrigger(ProductHolder holder) {
        if (selectedItems.contains(holder)) {
            selectedItems.remove(holder);
            holder.itemView.setBackground(null);
        } else {
            selectedItems.add(holder);
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.under_white));
        }
        doAdapterCallBack();
    }

    public List<Product> getSelectedItems() {
        List<Product> selectedProducts = new ArrayList<>();
        for (ProductHolder holder : selectedItems) {
            selectedProducts.add(holder.getProduct());
        }
        return selectedProducts;
    }

    public void removeSelectedItens() {
        for (ProductHolder holder : selectedItems) {
            productList.remove(holder.getProduct());
        }
        notifyDataSetChanged();
        selectedItems.clear();
        doAdapterCallBack();
    }

    public void clearSelections() {
        for (ProductHolder holder : selectedItems) {
            holder.itemView.setBackground(null);
        }
        selectedItems.clear();
        doAdapterCallBack();
    }

    private void doAdapterCallBack() {
        args.clear();
        args.putInt("selectionsSize", selectedItems.size());
        adapterCallback.onAdapterSelectChange(args);
    }
}
