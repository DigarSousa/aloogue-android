package alugueis.alugueis.abstractiontools;

import alugueis.alugueis.R;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Getter
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @Getter
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @Getter
    @Setter
    private String backStackFragmentClassName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_activity);
        ButterKnife.bind(this);
        initComponents();
        setFragment(startFragment());
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
     * and put it on backstack mapped by the name of fragment class
     *
     * @param fragment
     */

    protected void setFragment(StandardFragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .addToBackStack(fragment.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    /**
     * Checks if the class name of the current fragment on the screen are equals of the class received
     *
     * @param fragmentClass
     * @return
     */
    protected Boolean isOpen(Class fragmentClass) {
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.main_fragment);
        return currentFragment.getClass().getName().equals(fragmentClass.getName());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else if (backStackFragmentClassName != null) {
            getFragmentManager().popBackStack(backStackFragmentClassName, 0);
            KeyTools.hideInputMethod(this, getCurrentFocus());
        } else {
            super.onBackPressed();
        }
    }
}
