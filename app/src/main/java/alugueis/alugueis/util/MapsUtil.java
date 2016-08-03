package alugueis.alugueis.util;

import alugueis.alugueis.R;
import alugueis.alugueis.classes.maps.GPSTracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

public class MapsUtil {

    public static Marker addPlace(Context c, GoogleMap map, LatLng latLng, String title, String snippet) {

        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.title(title);
        options.snippet(snippet);
        options.draggable(false);

        Bitmap pinIcon = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_teal);
        Bitmap myMarker = Bitmap.createScaledBitmap(pinIcon, pinIcon.getWidth() / 5, pinIcon.getHeight() / 5, false);
        options.icon(BitmapDescriptorFactory.fromBitmap(myMarker));

        return map.addMarker(options);
    }

    public static MarkerOptions setMyLocation(Context c, GoogleMap map, LatLng latLng, String title) {
        Bitmap pinIcon = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_laranja);
        Bitmap myMarker = Bitmap.createScaledBitmap(pinIcon, pinIcon.getWidth() / 5, pinIcon.getHeight() / 5, false);

        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(16f));

        return new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(myMarker));
    }

    public static void getPlacesAroundMe(Context c, GoogleMap map, LatLng whereAmI, double distance) {
        double distanceReturned;

        double latitude = -20.0725553;
        double longitude = -44.579636;
        LatLng placeTest = new LatLng(latitude, longitude);
        MapsUtil.addPlace(c, map, placeTest, "Teste", "Snippet teste");


        distanceReturned = SphericalUtil.computeDistanceBetween(placeTest, whereAmI);

        if (distanceReturned > distance) {
            Toast.makeText(c, "Outside", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(c, "Inside", Toast.LENGTH_LONG).show();
        }
    }


    public static boolean locationPermissionDialog(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermition(Activity activity) {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                0);
    }

}
