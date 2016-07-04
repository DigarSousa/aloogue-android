package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.KeyTools;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.product.ProductRest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.StdService;

import java.util.List;

import static alugueis.alugueis.abstractiontools.ButterKnifeViewControls.ENABLED;

public class ProductFormActivity extends AppCompatActivity {
    private ProductRest productRest;
    private Product product;

    @BindView(R.id.edit_toolbar)
    Toolbar toolbar;

    @BindView(R.id.code_text)
    EditText code;
    @BindView(R.id.name_text)
    EditText name;
    @BindView(R.id.description_text)
    EditText description;
    @BindView(R.id.value_text)
    EditText value;
    @BindView(R.id.time_type_spinner)
    Spinner timeType;


    @BindViews({R.id.code_text, R.id.name_text, R.id.description_text, R.id.value_text, R.id.time_type_spinner})
    List<View> views;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_content);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        intComponents();
    }

    private void intComponents() {
        Spinner timeType = (Spinner) views.get(views.size() - 1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_type, android.R.layout.simple_spinner_dropdown_item);
        timeType.setAdapter(adapter);

        timeType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                KeyTools.hideInputMethod(ProductFormActivity.this, v);
            }
        });

        product = getIntent().getExtras().getParcelable("product");
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.productFormTitle));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        setEditMode(false);
        return true;
    }

    private void setEditMode(Boolean isInEditMode) {
        ButterKnife.apply(views, ENABLED, isInEditMode);
        if (isInEditMode) {
            KeyTools.visibleInputMethod(this, views.get(0), true);
        }
        menu.findItem(R.id.edit_action).setVisible(!isInEditMode);
        menu.findItem(R.id.done_action).setVisible(isInEditMode);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_action:
                setEditMode(true);
                break;
            case R.id.done_action:
                KeyTools.visibleInputMethod(this, getCurrentFocus(), false);
                saveProduct();
                setEditMode(false);
                break;
            default:
                return true;
        }
        return true;
    }

    private void saveProduct() {
        viewToObjcet();

        productRest = StdService.createService(ProductRest.class);
        Call<Product> call = productRest.save(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void viewToObjcet() {
        if (product == null) {
            product = new Product();
        }
        product.setCode(code.getText().toString());
        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        product.setValue(Double.valueOf(value.getText().toString()));
        product.setRentType((String) timeType.getSelectedItem());
    }
}
