package alugueis.alugueis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import alugueis.alugueis.util.MapsUtil;
import service.httputil.Service;

public class MapAct extends DashboardNavAct implements OnMapReadyCallback,
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2424;
    public static final int PERMISSION_ACESS_FINE_LOCATION = 25;

    private FloatingActionButton searchButton;
    private EditText productText;
    private EditText placeText;
    private GoogleMap map;
    private LatLng whereAmI;
    private Marker myMarker;
    private Place place;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.fragment_map_rent, frameLayout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        createGoogleLocationAPI();
        initiateComponents();
        setListeners();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void createGoogleLocationAPI() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void initiateComponents() {
        searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        productText = (EditText) findViewById(R.id.productText);
        placeText = (EditText) findViewById(R.id.placeText);
    }

    private void setListeners() {
        placeText.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getActualLocation();
        setMarkersListeners();
    }

    private void putMyMarker() {
        if (place != null) {
            if (myMarker != null) {
                myMarker.remove();
            }
            myMarker = map.addMarker(MapsUtil.setMyLocation(this, map, place, getResources().getString(R.string.youAreHere)));
        }
    }

    private void setMarkersListeners() {
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapAct.this, PlaceProfileAct.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
            place = PlaceAutocomplete.getPlace(this, data);
            placeText.setText(place.getAddress());
            putMyMarker();
            map.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            map.animateCamera(CameraUpdateFactory.zoomTo(16f));

        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(placeText)) {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .build(MapAct.this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

            } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        } else if (v.equals(searchButton)) {
            //todo:service...
        }
    }

    public void getActualLocation() {
        checkPermission();
        PendingResult<PlaceLikelihoodBuffer> pendingResult = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        pendingResult.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                    if (placeLikelihood.getLikelihood() > 0.55) {
                        place = placeLikelihood.getPlace();
                        break;
                    }
                }
                placeText.setText(place != null ? place.getAddress() : "");
                putMyMarker();
            }
        });
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_ACESS_FINE_LOCATION);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ACESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        } else {

        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}

