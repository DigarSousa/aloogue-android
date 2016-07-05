package alugueis.alugueis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.io.IOException;

import alugueis.alugueis.model.Place;
import alugueis.alugueis.util.StaticUtil;

public class DashboardNavAct extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    protected Toolbar mainToolbar;
    protected FrameLayout frameLayout;
    private Context context;
    private MenuItem manageProducts;
    private MenuItem createPlace;
    private MenuItem editPlace;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_nav);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        hideItems();

        context = getApplicationContext();
        initializeToolbar();
    }

    protected void hideItems() {
        try {
            Menu menu = this.navigationView.getMenu();
            this.manageProducts = menu.findItem(R.id.nav_manage_products);
            this.createPlace = menu.findItem(R.id.nav_add_shop);
            this.editPlace = menu.findItem(R.id.nav_edit_shop);

            Place place = (Place) StaticUtil.readObject(this, StaticUtil.PLACE);

            boolean placeExists = place == null || place.getId() == null;
            createPlace.setVisible(placeExists);
            manageProducts.setVisible(!placeExists);
            editPlace.setVisible(!placeExists);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeToolbar() {

        toggle = new ActionBarDrawerToggle(this, drawer, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        drawer.setDrawerListener(toggle);
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_shop) {
            Intent intent = new Intent(DashboardNavAct.this, CreatePlaceAct.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage_products) {
            Intent intent = new Intent(DashboardNavAct.this, ProductListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_edit_shop) {
            Intent intent = new Intent(DashboardNavAct.this, EditPlaceAct.class);
            startActivity(intent);
        } else if (id == R.id.nav_change_data) {
            Intent intent = new Intent(DashboardNavAct.this, EditProfileAct.class);
            intent.putExtra("source", "changeData");
            startActivity(intent);
        } else if (id == R.id.logout) {
            try {
                StaticUtil.remove(context, StaticUtil.LOGGED_USER);
            } catch (Exception ex) {
            }
            Intent intent = new Intent(DashboardNavAct.this, MainAct.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
