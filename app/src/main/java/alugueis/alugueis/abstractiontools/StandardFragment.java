package alugueis.alugueis.abstractiontools;

import alugueis.alugueis.R;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

public abstract class StandardFragment extends Fragment implements DrawerActionTool {
    private DrawerLayout drawerLayout;
    private Boolean hasOptionMenu;
    private Boolean homeAsUpEnabled;

    {
        hasOptionMenu = false;
        homeAsUpEnabled = false;
    }

    /**
     * Enables or disables the option menu of the toolbar
     *
     * @param hasOptionMenu
     * @return
     */
    public StandardFragment hasOptionMenu(Boolean hasOptionMenu) {
        this.hasOptionMenu = hasOptionMenu;
        return this;
    }

    public StandardFragment setHomeAsUpEnabled(Boolean homeAsUpEnabled) {
        this.homeAsUpEnabled = homeAsUpEnabled;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasOptionMenu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAppCompatActivity().setSupportActionBar(getToolBar());
        setUpActionBar();
    }

    /**
     * Sets a drawer toggle to the toolbar if the drawer layout and toolbar is not null
     */
    private void setUpActionBar() {
        if (getAppCompatActivity().getSupportActionBar() != null) {
            getAppCompatActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
            getAppCompatActivity().getSupportActionBar().setDisplayShowHomeEnabled(homeAsUpEnabled);
        }

        if (!homeAsUpEnabled && drawerLayout != null && getToolBar() != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    getActivity(),
                    drawerLayout, getToolBar(),
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    /**
     * Sets a drawer layout to gives it a toggle listener
     *
     * @param drawerLayout
     */
    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }


    protected AppCompatActivity getAppCompatActivity() {
        return ((AppCompatActivity) getActivity());
    }

}
