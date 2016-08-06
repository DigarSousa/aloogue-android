package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.DrawerActivity;
import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.dialogs.PermissionsDenied;
import alugueis.alugueis.dialogs.PermissionsDialog;
import alugueis.alugueis.util.MapsUtil;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.MenuItem;

public class MainActivity extends DrawerActivity implements PermissionsDialog.PermissionDialogListener {

    private MapFragmentView mapFragmentView;

    @Override
    public StandardFragment startFragment() {
        mapFragmentView = new MapFragmentView();
        MapsUtil.requestLocationPermition(this);
        return mapFragmentView;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                if (!MapsUtil.souldShowRequest(this)) {
                    new PermissionsDenied().show(getFragmentManager(), "PermissionsDenied");
                    return;
                }
                new PermissionsDialog().show(getFragmentManager(), "PermissionsFragment");
                return;
            }
        }
        mapFragmentView.startLocationSettings();
    }

    @Override
    public void onPositiveClick(PermissionsDialog dialog) {
        MapsUtil.requestLocationPermition(this);
    }

    @Override
    public void onNegativeClick(PermissionsDialog dialog) {
        this.finish();
    }
}
