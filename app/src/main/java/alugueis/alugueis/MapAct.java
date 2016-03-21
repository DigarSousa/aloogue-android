package alugueis.alugueis;

import alugueis.alugueis.classes.maps.GeocoderJSONParser;
import alugueis.alugueis.util.MapsUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

public class MapAct extends DashboardNavAct implements OnMapReadyCallback {

    private LocationManager myLocation;
    private FloatingActionButton searchButton;
    private EditText productText;
    private EditText placeText;
    private GoogleMap map;
    private LatLng whereAmI;
    private Marker lastOpened;
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.fragment_map_rent, frameLayout);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initiateComponents();
        setListeners();
    }

    private void initiateComponents() {
        myLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        searchButton = (FloatingActionButton) findViewById(R.id.searchButton);
        productText = (EditText) findViewById(R.id.productText);
        placeText = (EditText) findViewById(R.id.placeText);
        lastOpened = null;
    }

    private void setListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String location = placeText.getText().toString();
                if (location.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Defina", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://maps.googleapis.com/maps/api/geocode/json?";
                try {
                    location = URLEncoder.encode(location, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String address = "address=" + location;
                String sensor = "sensor=false";

                url = url + address + "&" + sensor;

                DownloadTask downloadTask = new DownloadTask();

                downloadTask.execute(url);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        whereAmI = MapsUtil.whereAmI(this);
        myMarker = map.addMarker(MapsUtil.setMyLocation(MapAct.this, map, whereAmI, getResources().getString(R.string.youAreHere)));
        MapsUtil.getPlacesAroundMe(this, map, whereAmI, 3000);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        map.setMyLocationEnabled(true);
        map.setPadding(20, 1000, 0, 0);
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                whereAmI = MapsUtil.whereAmI(MapAct.this);
                myMarker.remove();
                map.moveCamera(CameraUpdateFactory.newLatLng(whereAmI));
                map.animateCamera(CameraUpdateFactory.zoomTo(15f));
                myMarker = map.addMarker(MapsUtil.setMyLocation(MapAct.this, map, whereAmI, getResources().getString(R.string.youAreHere)));
                return true;
            }
        });
        setMarkersListeners();
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

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Error downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        String data = null;

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocoderJSONParser parser = new GeocoderJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);

                places = parser.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            map.clear();

            for (int i = 0; i < list.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                HashMap<String, String> hmPlace = list.get(i);
                double lat = Double.parseDouble(hmPlace.get("lat"));
                double lng = Double.parseDouble(hmPlace.get("lng"));
                String name = hmPlace.get("formatted_address");
                LatLng latLng = new LatLng(lat, lng);
                if (i == 0) {
                    myMarker.remove();
                    myMarker = map.addMarker(MapsUtil.setMyLocation(MapAct.this, map, latLng, name));
                }
            }
        }
    }
}