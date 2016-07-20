package alugueis.alugueis.adapter;

import android.view.View;

interface ProductClickListener {
    void onProductClick(View v, Integer position);

    void onProductSelect(View v, Integer position);
}
