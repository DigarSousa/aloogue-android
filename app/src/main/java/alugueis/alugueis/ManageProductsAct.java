package alugueis.alugueis;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import alugueis.alugueis.adapter.ProductListAdapter;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.model.User;
import alugueis.alugueis.util.UserUtil;
import alugueis.alugueis.util.Util;

/**
 * Created by Pedreduardo on 19/01/2016.
 */
public class ManageProductsAct extends DashboardNavAct{

    private Context context;
    private User loggedUser;
    private EditText nameText;
    private ListView lvProducts;
    private ArrayList<Product> products;
    private RelativeLayout productsArea;
    private ProductListAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utilizado para levar o layout da activity para o pai (nav drawer)
        getLayoutInflater().inflate(R.layout.activity_manage_products, frameLayout);

        initializeAttributes();

        getLogged();

        initializeToolbar();
        initializeComponents();
    }

    private void loadProductList() {
        if(products.size() == 0){
            productsArea.setVisibility(View.INVISIBLE);
        }
        else{

            //lvProducts.setAdapter(null);
            lvProducts.destroyDrawingCache();
            lvProducts.refreshDrawableState();
            lvProducts.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();
        }

    }

    private void initializeAttributes() {
        context = getApplicationContext();
        loggedUser = new User();
        //todo: Buscar produtos do cliente aqui
        products = new ArrayList<Product>();
        productAdapter = new ProductListAdapter(context, android.R.layout.simple_list_item_1, products);
    }

    private void getLogged() {
        try {
            loggedUser = (User) UserUtil.getLogged(context);
        }catch(Exception ex){}
    }

    private void initializeToolbar() {
        mainToolbar.setTitle("Produtos");
    }

    private void initializeComponents() {

        lvProducts = (ListView) findViewById(android.R.id.list);
        productsArea = (RelativeLayout) findViewById(R.id.productsArea);

        nameText = (EditText) findViewById(R.id.nameText);
        nameText.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= nameText.getRight() - nameText.getTotalPaddingRight()){

                        if(validateEmptyProductName()){

                            Product productToAdd = new Product();
                            String productName = nameText.getText().toString().trim();
                            productToAdd.setDescription(productName);
                            products.add(productToAdd);
                            loadProductList();
                        }

                        event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
                        return false;
                    }
                }
                return false;
            }

       });
    }

    private boolean validateEmptyProductName() {
        String productName = nameText.getText().toString().trim();
        if(productName.equals("")){
            Util.createToast(context, getResources().getString(R.string.emptyProductName));
            return false;
        }
        return true;
    }
}
