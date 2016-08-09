package alugueis.alugueis.location;

import alugueis.alugueis.dialogs.LocationDisabledDialog;
import alugueis.alugueis.util.MapsUtil;

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
    private LocationSimpleListener locationSimpleListener;
    private LocationDisabledDialog locationDisabledDialog;
    private LocationListener gpsListener;
    private LocationListener netWorkListener;
    private Boolean isListing;

    public LocationChangeListener(AppCompatActivity activity, LocationSimpleListener locationSimpleListener) {
        this.activity = activity;
        this.locationSimpleListener = locationSimpleListener;
        this.locationManager = (LocationManager) activity.getSystemService(LOCATION_SERVICE);
    }

    public void startLocationListener() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            MapsUtil.requestLocationPermition(activity);
            return;
        }

        isAnyProviderEnabled();
        if (isListing == null || !isListing) {
            isListing = true;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, getGpsListener());
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, getNetWorkListener());
        }
    }

    private boolean isAnyProviderEnabled() {
        Boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGpsEnabled && !isNetworkEnabled && activity.getFragmentManager().findFragmentByTag("LocationDisabledDialog") == null) {
            locationDisabledDialog = new LocationDisabledDialog();
            locationDisabledDialog.setCancelable(false);
            locationDisabledDialog.show(activity.getFragmentManager(), "LocationDisabledDialog");
            return false;
        }
        return true;
    }

    public void removeListeners() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (gpsListener != null) {
            locationManager.removeUpdates(gpsListener);
        }
        if (netWorkListener != null) {
            locationManager.removeUpdates(netWorkListener);
        }

        isListing = false;
    }

    private LocationListener getNetWorkListener() {
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
                locationDisabledDialog.dismiss();
            }

            @Override
            public void onProviderDisabled(String s) {
                isAnyProviderEnabled();
            }
        };
    }

    private LocationListener getGpsListener() {
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

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    public Boolean isListing() {
        return isListing;
    }
}
