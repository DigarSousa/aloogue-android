package alugueis.alugueis.location;

import alugueis.alugueis.dialogs.LocationDisabledDialog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.LOCATION_SERVICE;

public class LocationChangeListener {
    private AppCompatActivity activity;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LocationSimpleListener locationSimpleListener;
    private LocationDisabledDialog locationDisabledDialog;

    public LocationChangeListener(AppCompatActivity activity, LocationSimpleListener locationSimpleListener) {
        this.activity = activity;
        this.locationSimpleListener = locationSimpleListener;
        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);

    }

    public void startLocationListener() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        isAnyProviderEnabled();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, getLocationListener());
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, getLocationListener());

    }

    private void removeListeners() {
        if (locationListener == null || ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.removeUpdates(locationListener);
        locationListener = null;
    }

    private boolean isAnyProviderEnabled() {
        Boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsEnabled && !isNetworkEnabled) {
            locationDisabledDialog = new LocationDisabledDialog();
            locationDisabledDialog.setCancelable(false);
            return false;
        }
        return true;
    }

    private LocationListener getLocationListener() {
        if (locationListener != null) {
            return locationListener;
        }

        return locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationSimpleListener.onLocationChange(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
                isAnyProviderEnabled();
            }
        };
    }
}
