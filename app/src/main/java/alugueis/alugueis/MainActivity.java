package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.DrawerActivity;
import alugueis.alugueis.abstractiontools.StandardFragment;
import android.view.MenuItem;
public class MainActivity extends DrawerActivity {
    @Override
    public StandardFragment startFragment() {
        return new MapFragmentView();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
