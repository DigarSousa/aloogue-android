package alugueis.alugueis;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import alugueis.alugueis.model.AddressApp;
import alugueis.alugueis.model.City;
import alugueis.alugueis.model.Country;
import alugueis.alugueis.model.LoggedUser;
import alugueis.alugueis.model.Neighbourhood;
import alugueis.alugueis.model.StateFU;
import alugueis.alugueis.model.Street;
import alugueis.alugueis.util.ImageUtil;
import alugueis.alugueis.util.Util;
import alugueis.alugueis.view.RoundedImageView;

public class SignupAct extends ActionBarActivity {

    private Toolbar mainToolbar;
    private LoggedUser loggedUser;
    private RadioButton userRadio;
    private RadioButton tenantRadio;
    private EditText cpfCnpjEditText;
    private EditText nameEditText;
    private EditText addressEditText;
    private EditText streetNumberEditText;
    private EditText neighbourhoodEditText;
    private EditText cityEditText;
    private Spinner stateSpinner;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private Spinner businessInitialHourSpinner;
    private Spinner businessFinalHourSpinner;
    private RoundedImageView pictureImageView;
    private EditText selfDescriptionEditText;
    private CheckBox acceptTermsCheckBox;
    private Button selectPictureButton;
    private Button doneButton;
    //For image upload
    private int RESULT_LOAD_IMAGE = 1;
    private Thread startLogin;
    private EditText zipCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.loggedUser = LoggedUser.getInstance();

        initializeToolbar();
        initializeComponents();
        initializeListeners();
        initializeBehaviours();
        initializeStartLoginThread();
    }

    private void initializeStartLoginThread() {
        startLogin = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                    Intent intent = new Intent(SignupAct.this, MainAct.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void initializeListeners() {

        /*
        selectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        */

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validated = validateComponents();
                if (validated) {
                    saveNewUser();
                } else {
                    Toast.makeText(getApplicationContext(), "O formulário contém alguns erros. Corrija-os e tente novamente! (:", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*userRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    ((TextView) findViewById(R.id.selfDescriptionLabel)).setVisibility(View.INVISIBLE);
                    selfDescriptionEditText.setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.maxDescriptionLabel)).setVisibility(View.INVISIBLE);

                    ((TextView) findViewById(R.id.businessHoursLabel)).setVisibility(View.INVISIBLE);
                    ((RelativeLayout) findViewById(R.id.businessHoursArea)).setVisibility(View.INVISIBLE);
                }
                else{
                    ((TextView) findViewById(R.id.selfDescriptionLabel)).setVisibility(View.VISIBLE);
                    selfDescriptionEditText.setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.maxDescriptionLabel)).setVisibility(View.VISIBLE);

                    ((TextView) findViewById(R.id.businessHoursLabel)).setVisibility(View.VISIBLE);
                    ((RelativeLayout) findViewById(R.id.businessHoursArea)).setVisibility(View.VISIBLE);
                }
            }
        });*/

    }

    private void saveNewUser() {

        //loggedUser.setCpfCnpj(cpfCnpjEditText.getText().toString());
        loggedUser.setName(nameEditText.getText().toString());
        loggedUser.setEmail(emailEditText.getText().toString());
        loggedUser.setPassword(passwordEditText.getText().toString());
        loggedUser.setPicture(ImageUtil.BitmapToByteArray(BitmapFactory.decodeResource(getResources(),R.drawable.emoticon_cool)));
        //Logged as common user: 1, tenant: 2.
        loggedUser.setLoggedAs(1);
        //loggedUser.setBusinessInitialHour(businessInitialHourSpinner.getSelectedItem().toString());
        //loggedUser.setBusinessFinalHour(businessFinalHourSpinner.getSelectedItem().toString());
        //TODO: Inserir telefone no usuário
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

        loggedUser.setAddressApp(addressApp);
        try {
            String completeAddress = addressApp.makeAddress().toString();

            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses;
            addresses = geocoder.getFromLocationName(completeAddress, 1);
            if(addresses.size() > 0) {
                loggedUser.setLatitude(addresses.get(0).getLatitude());
                loggedUser.setLongitude(addresses.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-----------------------------------------------------------------------

        LoggedUser.deleteAll(LoggedUser.class);
        loggedUser.save();
        Toast.makeText(getApplicationContext(), "Usuário salvo com sucesso. (:", Toast.LENGTH_LONG).show();
        startLogin.start();
    }

    private boolean validateComponents() {
        boolean validated = true;
        /*
        if(!validateCpfCnpj()){
            cpfCnpjEditText.setError(getResources().getString(R.string.invalidCpfCnpj));
            validated = false;
        }
        */
        if(!validateName()){
            nameEditText.setError(getResources().getString(R.string.emptyName));
            validated = false;
        }
        if(!validateZipCode()){
            zipCodeText.setError(getResources().getString(R.string.emptyZipCode));
            validated = false;
        }

        if(!validateStreet()){
            addressEditText.setError(getResources().getString(R.string.emptyStreet));
            validated = false;
        }
        if(!validateStreetNumber()){
            streetNumberEditText.setError(getResources().getString(R.string.emptyStreetNumber));
            validated = false;
        }
        if(!validateNeighbourhood()){
            neighbourhoodEditText.setError(getResources().getString(R.string.emptyStreetNeighbourhood));
            validated = false;
        }
        if(!validateCity()){
            cityEditText.setError(getResources().getString(R.string.emptyCity));
            validated = false;
        }
        if(!validateEmail()){
            emailEditText.setError(getResources().getString(R.string.invalidEmail));
            validated = false;
        }
        if(!validatePassword()){
            validated = false;
        }
        if(!validatePasswordConfirm()){
            validated = false;
        }
        if(!validateAcceptedTerms()){
            acceptTermsCheckBox.setError(getResources().getString(R.string.acceptTermsError));
            validated = false;
        }
        return validated;
    }

    private boolean validateEmail() {
        if(!Util.isValidEmail(emailEditText.getText().toString())){
            return false;
        }
        return true;
    }

    private boolean validateCity() {
        if(cityEditText.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private boolean validateAcceptedTerms() {
        //TODO: Validar accept changes apenas com toast
        if(!acceptTermsCheckBox.isChecked()){
            return false;
        }
        return true;
    }

    private boolean validatePasswordConfirm() {
        if(!passwordConfirmEditText.getText().toString().equals(passwordEditText.getText().toString())){
            passwordConfirmEditText.setError(getResources().getString(R.string.wrongPasswordConfirm));
            return false;
        }
        if(passwordConfirmEditText.getText().toString().equals("")){
            passwordConfirmEditText.setError(getResources().getString(R.string.emptyPasswordConfirm));
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if(passwordEditText.getText().toString().equals("")){
            passwordEditText.setError(getResources().getString(R.string.emptyPassword));
            return false;
        }
        else if(passwordEditText.getText().toString().length() < 4 || passwordEditText.getText().toString().length() > 10) {
            passwordEditText.setError(getResources().getString(R.string.minMaxPasswordTextError));
            return false;
        }
        return true;
    }

    private boolean validateNeighbourhood() {
        if(neighbourhoodEditText.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private boolean validateStreetNumber() {
        if(streetNumberEditText.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private boolean validateStreet() {
        if(addressEditText.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private boolean validateName() {
        if(nameEditText.getText().toString().equals("")){
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
        if(zipCodeText.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

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
    */

    private void initializeToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainToolbar.setTitle(R.string.signup);
        setSupportActionBar(mainToolbar);
        //mainToolbar.setSubtitle("v. 1.0.0");
        //mainToolbar.setLogo(R.drawable.logo_branco);
    }

    private void initializeComponents() {


        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);

        //Radios
        /*
        selfDescriptionEditText = (EditText) findViewById(R.id.selfDescriptionText);
        userRadio = (RadioButton) findViewById(R.id.iAmAnUser);
        tenantRadio = (RadioButton) findViewById(R.id.iAmAnTenant);
        if(userRadio.isChecked()){
            ((TextView) findViewById(R.id.selfDescriptionLabel)).setVisibility(View.INVISIBLE);
            selfDescriptionEditText.setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.maxDescriptionLabel)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.businessHoursLabel)).setVisibility(View.INVISIBLE);
            ((RelativeLayout) findViewById(R.id.businessHoursArea)).setVisibility(View.INVISIBLE);
        }
        */


        //General
        //cpfCnpjEditText = (EditText) findViewById(R.id.cpfCnpjText);
        nameEditText = (EditText) findViewById(R.id.nameText);
        zipCodeText = (EditText) findViewById(R.id.zipCodeText);
        addressEditText = (EditText) findViewById(R.id.addressText);
        streetNumberEditText = (EditText) findViewById(R.id.streetNumberText);
        neighbourhoodEditText = (EditText) findViewById(R.id.neighbourhoodText);
        cityEditText = (EditText) findViewById(R.id.cityText);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);

        //Account
        emailEditText = (EditText) findViewById(R.id.emailText);
        passwordEditText = (EditText) findViewById(R.id.passwordText);
        passwordConfirmEditText = (EditText) findViewById(R.id.passwordConfirmText);

        //Profile
        //businessInitialHourSpinner = (Spinner) findViewById(R.id.initialHoursText);
        //businessFinalHourSpinner = (Spinner) findViewById(R.id.finalHoursText);
        //pictureImageView = (RoundedImageView) findViewById(R.id.pictureImage);
        //pictureImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.emoticon_cool));
        //selectPictureButton = (Button) findViewById(R.id.selectPictureButton);

        //Terms
        acceptTermsCheckBox = (CheckBox) findViewById(R.id.acceptTermsCheck);

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
        //Util.populeHoursSpinner(this, businessInitialHourSpinner);
        //Util.populeHoursSpinner(this, businessFinalHourSpinner);
        //-------------------------------------------


    }

}
