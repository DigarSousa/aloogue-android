package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.DrawerActivity;
import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.util.MapsUtil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends DrawerActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!MapsUtil.isPermissionGranted(this) && savedInstanceState != null) {
            Log.d(TAG, "Activity sate was reset");
            savedInstanceState.clear();
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public StandardFragment startFragment() {
        MapFragmentView mapFragmentView = new MapFragmentView();
        mapFragmentView.setDrawerLayout(drawerLayout);
        return mapFragmentView;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_aloogue:
                if (!isOpen(MapFragmentView.class)) {
                    setFragment(startFragment());
                }
        }
        return true;
    }
}
