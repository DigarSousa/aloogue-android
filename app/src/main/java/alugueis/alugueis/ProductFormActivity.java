package alugueis.alugueis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductFormActivity extends AppCompatActivity {
    @BindView(R.id.edit_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_content);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.productFormTitle));
        }
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
                break;
            case R.id.done_action:
                break;
            default:
                return true;
        }
        return true;
    }
}
