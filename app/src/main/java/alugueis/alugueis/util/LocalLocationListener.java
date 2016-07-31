package alugueis.alugueis.util;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocalLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
