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
        return new MapFragmentView();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
