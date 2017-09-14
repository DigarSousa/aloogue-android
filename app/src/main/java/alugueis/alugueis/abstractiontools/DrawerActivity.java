package alugueis.alugueis.abstractiontools;

import alugueis.alugueis.R;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    private String backStackFragmentClassName;

    private StandardFragment startFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity);
        ButterKnife.bind(this);
        initComponents();
        setStartFragment(startFragment());
    }

    private void initComponents() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Expect a fragment layout as a return to set it on screen when the activity is launched
     *
     * @return Fragment
     */
    public abstract StandardFragment startFragment();

    /**
     * Receive a StandardFragment object and place it in the activity
     *
     * @param fragment
     */

    private void setStartFragment(StandardFragment fragment) {
        startFragment = fragment;
        getFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }


    public void setFragment(StandardFragment fragment) {
        //The start fragment is hided, so you don't set it, you have show it
        if (fragment.getClass().getName().equals(startFragment.getClass().getName())) {
            detachCurrentFragment(true);
            return;
        }

        if (!isOpen(fragment.getClass())) {
            detachCurrentFragment(false);

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment, fragment)
                    .hide(startFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
    }

    private void detachCurrentFragment(Boolean showStartFragment) {
        if (!isStartFragmentOpen()) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(getCurrentFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    if(showStartFragment)fragmentTransaction.show(startFragment);
                    fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getCurrentFragment().equals(startFragment)) {
            finish();
        } else if (!isStartFragmentOpen()) {
            detachCurrentFragment(true);
            KeyTools.hideInputMethod(this, getCurrentFocus());
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Checks if the class name of the current fragment on the screen are equals of the class received
     *
     * @param fragmentClass
     * @return
     */
    protected Boolean isOpen(Class fragmentClass) {
        return getCurrentFragment().getClass().getName().equals(fragmentClass.getName());
    }

    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.main_fragment);
    }


    private boolean isStartFragmentOpen() {
        return getCurrentFragment().getClass().getName().equals(startFragment.getClass().getName());
    }
}
