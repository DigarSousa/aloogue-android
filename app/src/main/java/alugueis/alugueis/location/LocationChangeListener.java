package alugueis.alugueis.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;

public class LocationChangeListener {
    private Context context;
    private LocationManager locationManager;
    private LocationListener gpsListener;
    private LocationListener networkListener;
    private LocationSimpleListener locationSimpleLisener;

    public LocationChangeListener(Context context, LocationSimpleListener locationSimpleLisener) {
        this.context = context;
        this.locationSimpleLisener = locationSimpleLisener;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public void startGpsListener() {
        startLocationListener(LocationManager.GPS_PROVIDER, 0L, 0f);
    }

    public void startNetworkListener() {
        startLocationListener(LocationManager.NETWORK_PROVIDER, 0L, 0f);

    }

    private void startLocationListener(String provider, Long minTime, Float minChangeDistance) {
        isAnyProviderEnabled();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (provider.equals(LocationManager.GPS_PROVIDER))
            locationManager.requestLocationUpdates(provider, minTime, minChangeDistance, getGpsLocationListener());
        else {

            locationManager.requestLocationUpdates(provider, minTime, minChangeDistance, getNetWorkLocationListener());
        }
    }

    private LocationListener getGpsLocationListener() {
        gpsListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationSimpleLisener.onLocationChange(location);
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
        return gpsListener;
    }

    private LocationListener getNetWorkLocationListener() {
        networkListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationSimpleLisener.onLocationChange(location);
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
        return networkListener;
    }

    private void removeGpsListener() {
        if (gpsListener != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(gpsListener);
        }
    }

    private void removeNetworkListener() {
        if (networkListener != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(networkListener);
        }

    }

    private void enableListeners() {
        Boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGpsEnabled) {
            startGpsListener();
        }
        if (isNetworkEnabled) {
            startNetworkListener();
        }
    }

    private void isAnyProviderEnabled() {
        Boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGpsEnabled) {
            removeGpsListener();
        }
        if (!isNetworkEnabled) {
            removeNetworkListener();
        }

        if (!isGpsEnabled && !isNetworkEnabled) {
            //todo: mostrar mensagem...
        }
    }
}
