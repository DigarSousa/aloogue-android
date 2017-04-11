package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.DrawerActivity;
import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.UserApp;
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
    private Bundle args;
    private ProductListFragment productListFragment;
    private PlaceFragment placeFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!MapsUtil.isPermissionGranted(this) && savedInstanceState != null) {
            Log.d(TAG, "Activity sate was reset");
            savedInstanceState.clear();
        }
        super.onCreate(savedInstanceState);
        args = new Bundle();
        hoverHomeItem();
    }


    @Override
    protected void onResume() {
        super.onResume();
        invalidadeProductListVisibility();
    }

    public void invalidadeProductListVisibility() {
        if (getPlace() != null && !navigationView.getMenu().findItem(R.id.action_product_list).isVisible()) {
            navigationView.getMenu().findItem(R.id.action_product_list).setVisible(true);
        }
    }

    @Override
    public StandardFragment startFragment() {
        MapFragmentView mapFragmentView = new MapFragmentView();
        mapFragmentView.setDrawerLayout(drawerLayout);
        return mapFragmentView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        args.clear();

        int id = item.getItemId();
        switch (id) {
            case R.id.action_aloogue:
                setFragment(startFragment());
                break;

            case (R.id.action_place):
                startPlaceFragment();
                break;

            case (R.id.action_product_list):
                startProductListFragment();
                break;

            case (R.id.action_logout):
                logout();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startProductListFragment() {
        args.putSerializable("place", getPlace());
        productListFragment = new ProductListFragment();
        productListFragment.setHomeAsUpEnabled(true).hasOptionMenu(true);
        productListFragment.setDrawerLayout(drawerLayout);
        setFragment(productListFragment);
    }

    private void startPlaceFragment() {
        args.putSerializable("user", getUser());
        args.putSerializable("place", getPlace());

        placeFragment = new PlaceFragment();
        placeFragment.setHomeAsUpEnabled(true).hasOptionMenu(true);
        placeFragment.setDrawerLayout(drawerLayout);
        placeFragment.setArguments(args);
        setFragment(placeFragment);
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


    private UserApp getUser() {
        try {
            return (UserApp) StaticUtil.readObject(this, StaticUtil.LOGGED_USER);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Place getPlace() {
        try {
            return (Place) StaticUtil.readObject(this, StaticUtil.PLACE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void hoverHomeItem() {
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void drawerLockMode(Integer lockMode) {
        drawerLayout.setDrawerLockMode(lockMode);
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
}