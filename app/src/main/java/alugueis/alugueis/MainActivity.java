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
    protected void onStart() {
        super.onStart();
        MapsUtil.requestLocationPermition(this);
    }

    @Override
    public StandardFragment startFragment() {
        mapFragmentView = new MapFragmentView();
        return mapFragmentView;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults[0] != PackageManager.PERMISSION_GRANTED && shouldRequestAgain()) {
            new PermissionsDialog().show(getFragmentManager(), "PermissionsFragment");
            return;
        }

        mapFragmentView.startLocationSettings();
    }

    private Boolean shouldRequestAgain() {
        if (!MapsUtil.souldShowRequest(this)) {
            new PermissionsDenied().show(getFragmentManager(), "PermissionsDenied");
            return false;
        }
        return true;
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
