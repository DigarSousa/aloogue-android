package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.DrawerActivity;
import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.dialogs.PermissionsDialog;
import alugueis.alugueis.util.MapsUtil;

import android.content.pm.PackageManager;

import android.support.annotation.NonNull;

import android.view.MenuItem;

public class MainActivity extends DrawerActivity implements PermissionsDialog.PermissionDialogListener {
    private static final String TAG = "MainActivity";
    private MapFragmentView mapFragmentView;

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
        if (permissions.length != 0) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                PermissionsDialog permissionsDialog = new PermissionsDialog();
                permissionsDialog.setCancelable(false);
                permissionsDialog.show(getFragmentManager(), "PermissionsFragment");
                return;
            }
            mapFragmentView.startLocationSettings();
        }
    }

    @Override
    public void onPositiveClick(PermissionsDialog dialog) {
        if (!MapsUtil.souldShowRequest(this)) {
            MapsUtil.callApplicationPermissionsSettings(this);
        } else {
            MapsUtil.requestLocationPermition(this);
        }
    }
}
