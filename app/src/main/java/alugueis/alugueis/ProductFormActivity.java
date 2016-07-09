package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.KeyTools;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.product.ProductRest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
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
    private Product product;
    private Integer position;
    private Place place;

    @BindView(R.id.edit_toolbar)
    Toolbar toolbar;
    @BindView(R.id.code_text)
    EditText code;
    @BindView(R.id.name_text)
    EditText name;
    @BindView(R.id.description_text)
    EditText description;
    @BindView(R.id.value_text)
    EditText price;
    @BindView(R.id.time_type_spinner)
    Spinner rentType;

    @BindViews({R.id.code_text, R.id.name_text, R.id.description_text, R.id.value_text, R.id.time_type_spinner})
    List<View> views;

    private Menu menu;
    private Boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_content);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        startSpinner();
        startActivityState();
    }

    private void startSpinner() {
        final Spinner timeType = (Spinner) views.get(views.size() - 1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_type, android.R.layout.simple_spinner_dropdown_item);
        timeType.setAdapter(adapter);

        timeType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    KeyTools.hideInputMethod(ProductFormActivity.this, v);
                    timeType.performClick();
                }
            }
        });
    }

    private void startActivityState() {
        if (getIntent().getExtras() != null && getIntent().getSerializableExtra("product") != null) {
            product = (Product) getIntent().getSerializableExtra("product");
            position = getIntent().getExtras().getInt("position");
            productToView();
            isEditMode = Boolean.FALSE;
        } else {
            position = 0;
            place = (Place) getIntent().getSerializableExtra("place");
            isEditMode = Boolean.TRUE;
        }
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
        setEditMode(isEditMode);
        return true;
    }

    private void setEditMode(Boolean isInEditMode) {
        ButterKnife.apply(views, ENABLED, isInEditMode);
        if (isInEditMode) {
            KeyTools.showInputMethod(this, views.get(0));
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
                viewToObjcet();
                saveProduct();
                setEditMode(false);
                break;
            case android.R.id.home:
                onBackPressed();
            default:
                return true;
        }
        return true;
    }

    private void saveProduct() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.saveProduct));
        progressDialog.show();

        ProductRest productRest = StdService.createService(ProductRest.class);
        Call<Product> call = productRest.save(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                product = null; //dont put the object on list view

                progressDialog.dismiss();
                Toast.makeText(ProductFormActivity.this, getString(R.string.saveProductError), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void viewToObjcet() {
        if (product == null) {
            product = new Product();
            product.setPlace(place);
        }
        product.setCode(code.getText().toString());
        product.setName(name.getText().toString());
        product.setDescription(description.getText().toString());
        product.setRentType(rentType.getSelectedItem() != null ? rentType.getSelectedItem().toString() : "");
        Double price = Double.valueOf(this.price.getText().toString().isEmpty() ? "0" : this.price.getText().toString());
        product.setPrice(price);
    }

    private void productToView() {
        code.setText(product.getCode());
        name.setText(product.getName());
        description.setText(product.getDescription());
        price.setText(product.getPrice().toString());
        setSpinnerSelectedItem();
    }

    private void setSpinnerSelectedItem() {
        for (int i = 0; i < rentType.getCount(); i++) {
            if (product.getRentType().equals(rentType.getItemAtPosition(i))) {
                rentType.setSelection(i);
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        result.putExtras(bundle);
        setResult(position, result);
        finish();
    }
}
