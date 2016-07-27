package alugueis.alugueis.adapter;

interface ProductClickListener {
    void onProductClick(ProductHolder productHolder, Integer position);

    void onProductSelect(ProductHolder productHolder, Integer position);
}
