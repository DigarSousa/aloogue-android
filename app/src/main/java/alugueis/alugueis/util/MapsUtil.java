package alugueis.alugueis.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.util.Map;

import alugueis.alugueis.R;

/**
 * Created by Pedreduardo on 09/12/2015.
 */
public class MapsUtil {

    public static void addPlace(Context c, GoogleMap map, LatLng latLng, String title, String snippet){

        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.title(title);
        options.snippet(snippet);
        options.draggable(false);

        Bitmap pinIcon = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_teal);
        Bitmap myMarker = Bitmap.createScaledBitmap(pinIcon, pinIcon.getWidth() / 5, pinIcon.getHeight() / 5, false);
        options.icon(BitmapDescriptorFactory.fromBitmap(myMarker));

        Marker marker = map.addMarker(options);
    }

    public static void setMyLocation(Context c, GoogleMap map, double myLatitude, double myLongitude, String title) {
        LatLng whereAmI = new LatLng(myLatitude, myLongitude);

        Bitmap pinIcon = BitmapFactory.decodeResource(c.getResources(), R.drawable.pin_laranja);
        Bitmap myMarker = Bitmap.createScaledBitmap(pinIcon, pinIcon.getWidth() / 5, pinIcon.getHeight() / 5, false);

        map.addMarker(new MarkerOptions()
                .position(whereAmI)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(myMarker)));

        map.moveCamera(CameraUpdateFactory.newLatLng(whereAmI));
        map.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
    }

    public static void getPlacesAroundMe(Context c, GoogleMap map, LatLng whereAmI, double distance){
        double distanceReturned;

        double latitude = -20.0725553;
        double longitude = -44.579636;
        LatLng placeTest = new LatLng(latitude,longitude);
        MapsUtil.addPlace(c, map, placeTest, "Teste", "Snippet teste");


        distanceReturned = SphericalUtil.computeDistanceBetween(placeTest, whereAmI);

        if( distanceReturned > distance  ){
            Toast.makeText(c, "Outside", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(c, "Inside", Toast.LENGTH_LONG).show();
        }
    }
}
