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
    private List<Integer> selectedPositions;
    private AdapterCallback adapterCallback;
    private Bundle args;
    private Boolean isClearSelectionMode;

    public ProductAdapter(List<Product> productList) {
        this(productList, null);
    }

    public ProductAdapter(List<Product> productList, AdapterCallback adapterClickCallback) {
        this.productList = productList;
        this.adapterCallback = adapterClickCallback;
        selectedPositions = new ArrayList<>();
        args = new Bundle();
        isClearSelectionMode = false;
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
        if (isClearSelectionMode) {
            holder.clearViewSelection();
        }
        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productPrice.setText(product.getPrice() != null ? product.getPrice().toString() : "0.00");
        holder.productPeriod.setText(product.getRentType());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private ProductHolder getHolder(View view) {
        if (adapterCallback != null) {
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
        return new ProductHolder(view);
    }


    private void productClickTrigger(View view, Integer position) {
        if (!selectedPositions.isEmpty()) {
            productLongClickTrigger(view, position);
        } else {
            args.putInt("position", position);
            args.putSerializable("product", productList.get(position));
            adapterCallback.onAdapterClick(args);
        }
    }


    private void productLongClickTrigger(View view, Integer position) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position);
            view.setBackgroundColor(view.getResources().getColor(R.color.white));
        } else {
            if (!selectedPositions.contains(position)) {
                selectedPositions.add(position);
            }
            view.setBackgroundColor(view.getResources().getColor(R.color.under_white));
        }
        doAdapterCallBack();
    }

    public List<Product> getSelectedItens() {
        List<Product> selectedItens = new ArrayList<>();
        for (Integer position : selectedPositions) {
            selectedItens.add(productList.get(position));
        }
        return selectedItens;
    }

    public void removeSelectedItens() {
        for (int position : selectedPositions) {
            productList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void cleanSelections() {
        isClearSelectionMode = true;
        for (int position : selectedPositions) {
            notifyItemChanged(position);
        }
        selectedPositions.clear();
        doAdapterCallBack();
    }

    private void doAdapterCallBack() {
        args.clear();
        args.putInt("selectionsSize", selectedPositions.size());
        adapterCallback.onAdapterSelectChange(args);
    }
}
