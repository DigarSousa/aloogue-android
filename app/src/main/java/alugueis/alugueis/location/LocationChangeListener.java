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
    private LocationListener gpsListener;
    private LocationListener netWorkListener;
    private LocationSimpleListener locationSimpleListener;
    private LocationDisabledDialog locationDisabledDialog;

    public LocationChangeListener(AppCompatActivity activity, LocationSimpleListener locationSimpleListener) {
        this.activity = activity;
        this.locationSimpleListener = locationSimpleListener;
        locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
    }

    public void startGpsListener() {
        startLocationListener(LocationManager.GPS_PROVIDER, 0L, 0f);
    }

    public void startNetWorkListener() {
        startLocationListener(LocationManager.NETWORK_PROVIDER, 0L, 0f);
    }

    private void startLocationListener(String provider, long minTime, float minChangeDistance) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(provider, minTime, minChangeDistance, getGpsLocationListener());
        }
        locationManager.requestLocationUpdates(provider, minTime, minChangeDistance, getNetWorkLocationListener());
    }

    private LocationListener getGpsLocationListener() {
        return gpsListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationSimpleListener.onLocationChange(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                enableListeners();
            }

            @Override
            public void onProviderDisabled(String s) {
                isAnyProviderEnabled();
            }
        };
    }

    private LocationListener getNetWorkLocationListener() {
        return netWorkListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationSimpleListener.onLocationChange(location);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                enableListeners();
            }

            @Override
            public void onProviderDisabled(String s) {
                isAnyProviderEnabled();
            }
        };
    }

    public void removeGpsListener() {
        if (gpsListener != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(gpsListener);
        }
    }

    public void removeNetWorkListener() {
        if (netWorkListener != null) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(netWorkListener);
        }

    }

    private void enableListeners() {
        Boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGpsEnabled) {
            startGpsListener();
        }
        if (isNetworkEnabled) {
            startNetWorkListener();
        }
    }

    private void isAnyProviderEnabled() {
        Boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGpsEnabled) {
            removeGpsListener();
        }
        if (!isNetworkEnabled) {
            removeNetWorkListener();
        }

        if (!isGpsEnabled && !isNetworkEnabled && (locationDisabledDialog == null || !locationDisabledDialog.isVisible())) {
            locationDisabledDialog = new LocationDisabledDialog();
            locationDisabledDialog.show(activity.getFragmentManager(), "LocationDisabledDialog");
        }
    }
}
