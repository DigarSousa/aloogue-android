package alugueis.alugueis;

import alugueis.alugueis.classes.maps.GeocoderJSONParser;
import alugueis.alugueis.model.*;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.util.Util;
import alugueis.alugueis.view.RoundedImageView;
import service.httputil.OnFinishTask;
import service.httputil.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreatePlaceAct extends DashboardNavAct implements OnFinishTask {

    private Context context;
    private Place place;
    private EditText cpfCnpjEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText streetNumberEditText;
    private EditText neighbourhoodEditText;
    private EditText cityEditText;
    private Spinner stateSpinner;
    private Spinner businessInitialHourSpinner;
    private Spinner businessFinalHourSpinner;
    private RoundedImageView pictureImageView;
    private Button selectPictureButton;
    private Button doneButton;
    //For image upload

    private static final Integer RESULT_LOAD_IMAGE = 1;
    private Thread startLogin;
    private EditText zipCodeText;
    private ProgressDialog dialogCoord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Utilizado para levar o layout da activity para o pai (nav drawer)
        getLayoutInflater().inflate(R.layout.activity_create_place, frameLayout);

        dialogCoord = new ProgressDialog(CreatePlaceAct.this);
        this.context = getApplicationContext();

        getLogged();

        this.place = new Place();

        initializeToolbar();
        initializeComponents();
        initializeListeners();
        initializeBehaviours();
        initializeStartLoginThread();
    }

    private void getLogged() {
        UserApp loggedUserApp = new UserApp();
        try {
            loggedUserApp = (UserApp) StaticUtil.readObject(context, StaticUtil.LOGGED_USER);
        } catch (Exception ex) {
        }
    }

    private void initializeStartLoginThread() {
        startLogin = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                    Intent intent = new Intent(CreatePlaceAct.this, MapAct.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void initializeListeners() {


        selectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validated = validateComponents();
                if (Util.isOnlineWithToast(context)) {
                    if (validated) {
                        saveNewPlace();
                    } else {
                        Toast.makeText(getApplicationContext(), "O formulário contém alguns erros. Corrija-os e tente novamente! (:", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void saveNewPlace() {
        try {
            UserApp loggedUser = (UserApp) StaticUtil.readObject(getApplicationContext(), StaticUtil.LOGGED_USER);
            place.setCpfCnpj(cpfCnpjEditText.getText().toString());
            place.setName(nameEditText.getText().toString());

            Phone phone = new Phone();
            phone.setNumber(phoneEditText.getText().toString());
            ArrayList<Phone> phones = new ArrayList<Phone>();
            phones.add(phone);
            place.setPhones(phones);

            //loggedUserApp.setPicture(ImageUtil.BitmapToByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.emoticon_cool)));
            place.setBusinessInitialHour(businessInitialHourSpinner.getSelectedItem().toString());
            place.setBusinessFinalHour(businessFinalHourSpinner.getSelectedItem().toString());

            //ADDRESS
            //-----------------------------------------------------------------------
            AddressApp addressApp = new AddressApp();

            Street street = new Street();
            street.setDescription(addressEditText.getText().toString());
            addressApp.setStreet(street);

            addressApp.setNumber(streetNumberEditText.getText().toString());

            Neighbourhood neighbourhood = new Neighbourhood();
            neighbourhood.setDescription(neighbourhoodEditText.getText().toString());
            addressApp.setNeighbourhood(neighbourhood);

            City city = new City();
            city.setDescription(cityEditText.getText().toString());
            addressApp.setCity(city);

            StateFU stateFU = new StateFU();
            stateFU.setDescription(stateSpinner.getSelectedItem().toString());
            addressApp.setStateFU(stateFU);

            Country country = new Country();
            country.setDescription("Brasil");
            addressApp.setCountry(country);
            place.setUserApp(loggedUser);
            place.setAddressApp(addressApp);
            //todo: Pegar coordenadas
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        new Service(this).save(place, Place.class).execute();
    }

    @Override
    public void onFinishTask(Object result) {
        Place place= (Place) result;
        try {
            StaticUtil.setOject(this,StaticUtil.PLACE,place);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCoordinatesFromAddress() {
        // Getting the place entered
        String location = place.getAddressApp().toString();
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

    private boolean validateComponents() {
        boolean validated = true;

        if (!validateCpfCnpj()) {
            cpfCnpjEditText.setError(getResources().getString(R.string.invalidCpfCnpj));
            validated = false;
        }
        if (!validateName()) {
            nameEditText.setError(getResources().getString(R.string.emptyName));
            validated = false;
        }

        if (!validatePhone()) {
            phoneEditText.setError(getResources().getString(R.string.emptyPhone));
            validated = false;
        }

        if (!validateZipCode()) {
            zipCodeText.setError(getResources().getString(R.string.emptyZipCode));
            validated = false;
        }
        if (!validateStreet()) {
            addressEditText.setError(getResources().getString(R.string.emptyStreet));
            validated = false;
        }
        if (!validateStreetNumber()) {
            streetNumberEditText.setError(getResources().getString(R.string.emptyStreetNumber));
            validated = false;
        }
        if (!validateNeighbourhood()) {
            neighbourhoodEditText.setError(getResources().getString(R.string.emptyStreetNeighbourhood));
            validated = false;
        }
        if (!validateCity()) {
            cityEditText.setError(getResources().getString(R.string.emptyCity));
            validated = false;
        }
        return validated;
    }

    private boolean validateCity() {
        if (cityEditText.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateNeighbourhood() {
        if (neighbourhoodEditText.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateStreetNumber() {
        if (streetNumberEditText.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateStreet() {
        if (addressEditText.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateName() {
        if (nameEditText.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        if (phoneEditText.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateCpfCnpj() {
        if (!Util.isValidCPF(cpfCnpjEditText.getText().toString()) &&
                !Util.isValidCNPJ(cpfCnpjEditText.getText().toString())) {
            return false;
        }
        return true;
    }

    private boolean validateZipCode() {
        if (zipCodeText.getText().toString().equals("")) {
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap imageFromGallery = BitmapFactory.decodeFile(picturePath);
            Bitmap resized = Bitmap.createScaledBitmap(imageFromGallery, (int) (imageFromGallery.getWidth() * 0.4), (int) (imageFromGallery.getHeight() * 0.4), true);
            pictureImageView.setImageBitmap(resized);
        }


    }


    private void initializeToolbar() {
        mainToolbar.setTitle("Novo estabelecimento");
    }

    private void initializeComponents() {


        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);

        //General
        cpfCnpjEditText = (EditText) findViewById(R.id.cpfCnpjText);
        nameEditText = (EditText) findViewById(R.id.nameText);
        phoneEditText = (EditText) findViewById(R.id.phoneText);

        //Address
        zipCodeText = (EditText) findViewById(R.id.zipCodeText);
        addressEditText = (EditText) findViewById(R.id.addressText);
        streetNumberEditText = (EditText) findViewById(R.id.streetNumberText);
        neighbourhoodEditText = (EditText) findViewById(R.id.neighbourhoodText);
        cityEditText = (EditText) findViewById(R.id.cityText);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);

        //Profile
        businessInitialHourSpinner = (Spinner) findViewById(R.id.initialHoursText);
        businessFinalHourSpinner = (Spinner) findViewById(R.id.finalHoursText);
        pictureImageView = (RoundedImageView) findViewById(R.id.pictureImage);
        pictureImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.emoticon_cool));
        selectPictureButton = (Button) findViewById(R.id.selectPictureButton);

        //Done
        doneButton = (Button) findViewById(R.id.iAmDoneButton);

    }


    private void initializeBehaviours() {

        //State list
        //-------------------------------------------
        Util.populeStatesSpinner(this, stateSpinner);
        //-------------------------------------------

        //Hours list
        //-------------------------------------------
        Util.populeHoursSpinner(this, businessInitialHourSpinner);
        Util.populeHoursSpinner(this, businessFinalHourSpinner);
        //-------------------------------------------
    }


    //CLASSES THAT HELP ON FIND COORDINATES
    //----------------------------------------------------------------------------------------------
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
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

    /**
     * A class, to download Places from Geocoding webservice
     */
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        String data = null;

        protected void onPreExecute() {
            dialogCoord.setMessage("Criando loja");
            dialogCoord.show();
        }

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {

            // Instantiating ParserTask which parses the json data from Geocoding webservice
            // in a non-ui thread
            ParserTask parserTask = new ParserTask();

            // Start parsing the places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Geocoding Places in non-ui thread
     */
    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GeocoderJSONParser parser = new GeocoderJSONParser();

            try {
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a an ArrayList */
                places = parser.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            for (int i = 0; i < list.size(); i++) {

                HashMap<String, String> hmPlace = list.get(i);
                double lat = Double.parseDouble(hmPlace.get("lat"));
                double lng = Double.parseDouble(hmPlace.get("lng"));
                LatLng latLng = new LatLng(lat, lng);
                if (i == 0) {
                    place.getAddressApp().setLatitude(lat);
                    place.getAddressApp().setLongitute(lng);
                }
            }
        }
    }

}
