package alugueis.alugueis;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import alugueis.alugueis.classes.maps.GPSTracker;
import alugueis.alugueis.classes.maps.GeocoderJSONParser;
import alugueis.alugueis.util.MapsUtil;

public class MapAct extends FragmentActivity implements OnMapReadyCallback {

    private LocationManager myLocation;
    private LocationListener myLocationListener;
    private GPSTracker gps;
    private double myLatitude;
    private double myLongitude;
    private FloatingActionButton searchButton;
    private EditText productText;
    private EditText placeText;
    private GoogleMap map;
    private LatLng whereAmI;
    private Marker lastOpened; //For marker click

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map_rent);
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
        // Setting click event listener for the find button
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Getting the place entered
                String location = placeText.getText().toString();

                if (location == null || location.equals("")) {
                    Toast.makeText(getBaseContext(), "No Place is entered", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://maps.googleapis.com/maps/api/geocode/json?";
                try {
                    // encoding special characters like space in the user input place
                    location = URLEncoder.encode(location, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String address = "address=" + location;
                String sensor = "sensor=false";

                // url , from where the geocoding data is fetched
                url = url + address + "&" + sensor;

                // Instantiating DownloadTask to get places from Google Geocoding service
                // in a non-ui thread
                DownloadTask downloadTask = new DownloadTask();

                // Start downloading the geocoding places
                downloadTask.execute(url);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gps = new GPSTracker(MapAct.this);
        map = googleMap;

        // check if GPS enabled
        if(gps.canGetLocation()){
            myLatitude = gps.getLatitude();
            myLongitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }

        whereAmI = new LatLng(myLatitude, myLongitude);
        MapsUtil.setMyLocation(this, map, myLatitude, myLongitude, getResources().getString(R.string.youAreHere));
        MapsUtil.getPlacesAroundMe(this, map, whereAmI, 3000); // meters
        setMarkersListeners();
    }

    private void setMarkersListeners() {
        //TODO:
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapAct.this, PlaceProfileAct.class);
                startActivity(intent);
            }
        });
    }

    //CLASSES THAT HELP ON SEARCHES
    //----------------------------------------------------------------------------------------------
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Error downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    /** A class, to download Places from Geocoding webservice */
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        String data = null;
        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){

            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            ParserTask parserTask = new ParserTask();

            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /** A class to parse the Geocoding Places in non-ui thread */
    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;
        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocoderJSONParser parser = new GeocoderJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){

            // Clears all the existing markers
            map.clear();

            for(int i=0;i<list.size();i++){
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();
                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);
                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));
                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
                // Getting name
                String name = hmPlace.get("formatted_address");
                LatLng latLng = new LatLng(lat, lng);
                if(i==0)
                    MapsUtil.setMyLocation(MapAct.this, map, latLng.latitude, latLng.longitude, name);
            }
        }
    }

}