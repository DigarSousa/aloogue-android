package alugueis.alugueis.adapter;

import alugueis.alugueis.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import alugueis.alugueis.model.Product;
import butterknife.BindView;
import butterknife.ButterKnife;

class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private ProductClickListener productClickListener;
    private Product product;

    ProductHolder(View view) {
        this(view, null);
    }

    ProductHolder(View itemView, ProductClickListener productClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        if (productClickListener != null) {
            this.productClickListener = productClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        itemView.setTag(this);
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
        productClickListener.onProductClick(this, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        productClickListener.onProductSelect(this);
        return true;
    }

    void bindProduct(Product product) {
        this.product = product;
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(product.getPrice() != null ? product.getPrice().toString() : "0.00");
        productPeriod.setText(product.getRentType());
    }

    Product getProduct() {
        return product;
    }

}
