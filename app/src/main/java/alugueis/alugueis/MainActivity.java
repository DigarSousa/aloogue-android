package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.DrawerActivity;
import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.util.MapsUtil;
import alugueis.alugueis.util.StaticUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.MenuItem;

import java.io.IOException;

public class MainActivity extends DrawerActivity {
    private static final String TAG = "MainActivity";
    private ProductListFragment productListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!MapsUtil.isPermissionGranted(this) && savedInstanceState != null) {
            Log.d(TAG, "Activity sate was reset");
            savedInstanceState.clear();
        }
        super.onCreate(savedInstanceState);
        hoverHomeItem();
    }

    @Override
    public StandardFragment startFragment() {
        MapFragmentView mapFragmentView = new MapFragmentView();
        mapFragmentView.setDrawerLayout(drawerLayout);
        return mapFragmentView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_aloogue:
                setFragment(startFragment());
                break;
            case (R.id.action_place):
                setFragment(new PlaceFragment());
            case (R.id.action_product_list):
                productListFragment = new ProductListFragment();
                productListFragment.setHomeAsUpEnabled(true).hasOptionMenu(true);
                productListFragment.setDrawerLayout(drawerLayout);
                setFragment(productListFragment);
                break;

            case (R.id.action_logout):
                logout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        try {
            StaticUtil.remove(this, StaticUtil.LOGGED_USER);
            StaticUtil.remove(this, StaticUtil.PLACE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, StartActivity.class));
        this.finish();
    }

    private void hoverHomeItem() {
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (isOpen(ProductListFragment.class)
                && productListFragment.getProductAdapter() != null
                && productListFragment.getProductAdapter().getSelectedItems().size() > 0) {
            productListFragment.getProductAdapter().clearSelections();
            return;
        }
        super.onBackPressed();
    }

    private void drawerLockMode(Integer lockMode) {
        drawerLayout.setDrawerLockMode(lockMode);
    }
}