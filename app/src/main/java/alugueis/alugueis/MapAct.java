package alugueis.alugueis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import alugueis.alugueis.util.MapsUtil;
import service.ConstantsService;
import service.httputil.OnFinishTask;
import service.httputil.Service;
import service.httputil.URLBuilder;

public class MapAct extends DashboardNavAct implements OnMapReadyCallback,
        View.OnClickListener,OnFinishTask {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2424;
    public static final int PERMISSION_ACESS_FINE_LOCATION = 25;

    private FloatingActionButton searchButton;
    private EditText productText;
    private EditText placeText;
    private GoogleMap map;
    private Marker myMarker;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.fragment_map_rent, frameLayout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initiateComponents();
        setListeners();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initiateComponents() {
        searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        productText = (EditText) findViewById(R.id.productText);
        placeText = (EditText) findViewById(R.id.placeText);
        markers = new ArrayList<>();
    }

    private void setListeners() {
        placeText.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        checkPermission();
        new GetActualPlace(MapsUtil.whereAmI(this)).execute();

        setMarkersListeners();
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                new GetActualPlace(MapsUtil.whereAmI(MapAct.this)).execute();
                return true;
            }
        });
    }

    private void putMyMarker() {
        if (place != null) {
            if (myMarker != null) {
                myMarker.remove();
            }
            myMarker = map.addMarker(MapsUtil.setMyLocation(this, map, place.getLatLng(), getResources().getString(R.string.youAreHere)));
        }
    }

    private void setMarkersListeners() {
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(myMarker)) {
                    return;
                }

                Intent intent = new Intent(MapAct.this, PlaceProfileAct.class);
                if (placeMap.get(marker) != null) {
                    intent.putExtra("place", placeMap.get(marker));
                }
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
            removeMakers();
            new Service(this).putPath("/around").find(alugueis.alugueis.model.Place.class,
                    new Pair<String, Object>("description", productText.getText().toString()),
                    new Pair<String, Object>("latitude", myMarker.getPosition().latitude),
                    new Pair<String, Object>("longitude", myMarker.getPosition().longitude),
                    new Pair<String, Object>("distance", 5))//todo:defina a distancia aqui porra
                    .execute();

        }
    }

    @Override
    public void onFinishTask(Object result) {
        List<alugueis.alugueis.model.Place> places = (List<alugueis.alugueis.model.Place>) result;
        placeMap = new HashMap<>();
        Marker marker;
        LatLng latLng;
        for (alugueis.alugueis.model.Place place : places) {
            latLng = new LatLng(place.getAddress().getLatitude(), place.getAddress().getLongitude());
            marker = MapsUtil.addPlace(this, map, latLng, place.getName(), place.getCpfCnpj());
            markers.add(marker);
            placeMap.put(marker, place);
        }
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

    private class GetActualPlace extends AsyncTask<Void, Void, String> {
        private LatLng latLng;

        public GetActualPlace(LatLng latLng) {
            this.latLng = latLng;
        }

        @Override
        protected String doInBackground(Void... params) {
            URLBuilder urlBuilder = new URLBuilder("https://maps.googleapis.com/maps/api/geocode/json");
            urlBuilder.putParams(
                    new Pair<String, Object>("latlng", latLng.latitude + "," + latLng.longitude),
                    new Pair<String, Object>("key", getResources().getString(R.string.google_maps_key)),
                    new Pair<String, Object>("result_type", "street_address"));

            HttpURLConnection connection;
            String response = "";
            try {
                connection = (HttpURLConnection) new URL(urlBuilder.build()).openConnection();
                connection.setDoOutput(Boolean.TRUE);
                connection.setRequestProperty(ConstantsService.CONTENT, ConstantsService.JSON);
                connection.setRequestMethod("GET");
                connection.connect();

                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                String line;
                BufferedReader reader;
                reader = new BufferedReader(inputStream);
                StringBuilder sb = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONObject jSonObject = new JSONObject(json);
                JSONArray jsonArray = (JSONArray) jSonObject.get("results");
                JSONObject place = (JSONObject) jsonArray.get(0);
                placeText.setText((String) place.get("formatted_address"));
                if (myMarker != null) {
                    myMarker.remove();
                }
                myMarker = map.addMarker(MapsUtil.setMyLocation(MapAct.this, map, latLng, getResources().getString(R.string.youAreHere)));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void removeMakers() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }
}

