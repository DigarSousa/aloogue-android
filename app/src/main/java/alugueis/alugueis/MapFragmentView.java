package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.StandardFragment;
import alugueis.alugueis.location.LocationChangeListener;
import alugueis.alugueis.location.LocationSimpleListener;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.Unbinder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;

public class MapFragmentView extends StandardFragment implements OnMapReadyCallback {
    private static String TAG = "MapFragmentView";
    private Unbinder unbinder;
    private Marker currentLocation;
    private Marker myMarker;
    private GoogleMap googleMap;
    private LocationChangeListener locationChangeListener;

    @BindView(R.id.location_button)
    FloatingActionButton locationButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_activity, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MapFragment mapFragment = MapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        initFields();
    }

    private void initFields() {
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveCamera();
            }
        });
    }

    void startLocationSettings() {
        locationChangeListener = new LocationChangeListener(getAppCompatActivity(), new LocationSimpleListener() {
            @Override
            public void onLocationChange(Location location) {
                setMapsMarkers(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        });

        locationChangeListener.startGpsListener();
        locationChangeListener.startNetWorkListener();
    }

    private void setMapsMarkers(LatLng latLng) {

        if (currentLocation == null) {
            currentLocation = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(getIcon(R.drawable.ic_current_location_circle_blue)));

            myMarker = googleMap.addMarker(new MarkerOptions().position(latLng).icon(getIcon(R.drawable.ic_my_pin_location)));
            moveCamera();
        }
        currentLocation.setPosition(latLng);
    }

    private BitmapDescriptor getIcon(int resource) {
        return BitmapDescriptorFactory.fromResource(resource);
    }

    private void moveCamera() {
        myMarker.setPosition(currentLocation.getPosition());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation.getPosition()));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(19f));
    }

    @Override
    public Toolbar getToolBar() {
        return null;
    }

    @Override
    public void onDestroy() {
        if (locationChangeListener != null) {
            locationChangeListener.removeGpsListener();
            locationChangeListener.removeNetWorkListener();
        }
        super.onDestroy();
        unbinder.unbind();
    }
}


