package alugueis.alugueis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

import alugueis.alugueis.model.Product;

public class EditProductAct extends DashboardNavAct implements View.OnTouchListener {

    private Context context;
    private EditText productName;
    private ArrayList<Product> products;
    private Button finishEdit;
    private Button cancelEdit;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utilizado para levar o layout da activity para o pai (nav drawer)
        getLayoutInflater().inflate(R.layout.activity_edit_product, frameLayout);

        initializeAttributes();
        getProductList();
        initializeComponents();
    }

    private void initializeAttributes() {
        context = getApplicationContext();
        products = new ArrayList<Product>();
    }

    private void getProductList() {
        Intent it = getIntent();
        Bundle extras = it.getExtras();

        if (extras != null) {
            products = (ArrayList<Product>)extras.get("products");
            position = extras.getInt("position");
        }
    }
    private void initializeComponents() {
        productName = (EditText) findViewById(R.id.productName);
        productName.setText(products.get(position).getDescription());

        finishEdit = (Button) findViewById(R.id.buttonOk);
        cancelEdit = (Button) findViewById(R.id.buttonCancel);
        finishEdit.setOnTouchListener(this);
        cancelEdit.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
            if(view.equals(finishEdit)){
                Intent it = new Intent(EditProductAct.this, ManageProductsAct.class);
                products.get(position).setDescription(productName.getText().toString());
                it.putExtra("products", products);
                setResult(1,it);
                finish();
            }

            else if(view.equals(finishEdit)){
                Intent it = new Intent(EditProductAct.this, ManageProductsAct.class);
                startActivity(it);
                finish();
            }
        return true;
    }
}
