package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.KeyTools;
import alugueis.alugueis.model.Product;
import alugueis.alugueis.services.ProductService;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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
    @BindView(R.id.edit_toolbar)
    Toolbar toolbar;

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
        ProductService productService = StdService.createService(ProductService.class);

        Call<Product> call = productService.getProduct();
        call.enqueue(new Callback<Product>() {

            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product p = response.body();
                p.getCode();
                response.message();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
