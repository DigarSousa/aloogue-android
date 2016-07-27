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
    private List<Integer> selectedPositions;

    public ProductAdapter(List<Product> productList) {
        this(productList, null);
    }

    public ProductAdapter(List<Product> productList, AdapterCallback adapterClickCallback) {
        this.productList = productList;
        this.adapterCallback = adapterClickCallback;
        selectedPositions = new ArrayList<>();
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
        holder.bindProduct(productList.get(position));
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
                public void onProductSelect(ProductHolder productHolder, Integer position) {
                    productLongClickTrigger(productHolder, position);
                }
            };
            return new ProductHolder(view, productClickListener);
        }
        return new ProductHolder(view);
    }


    private void productClickTrigger(ProductHolder holder, Integer position) {
        if (!selectedPositions.isEmpty()) {
            productLongClickTrigger(holder, position);
        } else {
            args.putInt("position", position);
            args.putSerializable("product", productList.get(position));
            adapterCallback.onAdapterClick(args);
        }
    }


    private void productLongClickTrigger(ProductHolder holder, Integer position) {
        if (productList.get(position).getSelected()) {
            productList.get(position).setSelected(false);
            selectedPositions.remove(position);
        } else {
            holder.getProduct().setSelected(true);
            selectedPositions.add(position);
        }

        notifyItemChanged(position);
        doAdapterCallBack();
    }

    public List<Product> getSelectedItems() {
        List<Product> selectedProducts = new ArrayList<>();
        for (int position : selectedPositions) {
            selectedProducts.add(productList.get(position));
        }
        return selectedProducts;
    }

    public void removeSelectedItems() {
        for (Product product : getSelectedItems()) {
            productList.remove(product);
        }
        selectedPositions.clear();

        notifyDataSetChanged();
        doAdapterCallBack();
    }

    public void clearSelections() {
        for (int position : selectedPositions) {
            productList.get(position).setSelected(false);
        }
        notifyDataSetChanged();
        selectedPositions.clear();
        doAdapterCallBack();
    }

    private void doAdapterCallBack() {
        args.clear();
        args.putInt("selectionsSize", selectedPositions.size());
        adapterCallback.onAdapterSelectChange(args);
    }

}
