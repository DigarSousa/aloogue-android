package alugueis.alugueis;

import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.MapsUtil;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.util.Util;
import alugueis.alugueis.view.RoundedImageView;
import service.ConstantsService;
import service.httputil.OnFinishTask;
import service.httputil.Service;
import service.httputil.URLBuilder;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainAct extends ActionBarActivity implements View.OnClickListener, OnFinishTask {

    private Toolbar mainToolbar;
    private Toolbar bottomToolbar;
    private EditText userEditText;
    private EditText passwordEditText;
    private Button enterButton;
    private Button signinButton;
    private ImageButton facebookImageButton;
    private ImageButton twitterImageButton;
    private RoundedImageView pictureImageView;
    private TextView welcomeUser;
    private LatLng whereAmI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* if (getLogged() != null) {
            Intent intent = new Intent(this, MapAct.class);
            startActivity(intent);
            this.finish();
        }*/

        MapsUtil.requestLocationPermition(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();
        initializeComponents();
        initializeBehaviours();
    }


    private UserApp getLogged() {
        try {
            return (UserApp) StaticUtil.readObject(getApplicationContext(), StaticUtil.LOGGED_USER);
        } catch (Exception ex) {
            //todo:colocar log no arquivo de log =D
        }
        return null;
    }

    private void initializeToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainToolbar.setTitle("");
        setSupportActionBar(mainToolbar);
    }

    private void initializeComponents() {

        userEditText = (EditText) findViewById(R.id.userNameLogin);
        passwordEditText = (EditText) findViewById(R.id.passwordLogin);
        enterButton = (Button) findViewById(R.id.enterButton);
        signinButton = (Button) findViewById(R.id.signinButton);
        bottomToolbar = (Toolbar) findViewById(R.id.tb_bottom);
        pictureImageView = (RoundedImageView) findViewById(R.id.pictureImage);
        welcomeUser = (TextView) findViewById(R.id.welcomeUser);

        /*if (loggedUserApp.getName() != null) {
            String userNamer = loggedUserApp.getName();
            String customWelcome = getResources().getString(R.string.welcomeCustomized) + " " + userNamer + "!";
            welcomeUser.setText(customWelcome);
        }*/

        pictureImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.emoticon_cool));

        /*if (loggedUserApp.getEmail() != null) {
            userEditText.setText(loggedUserApp.getEmail());
        }*/

        facebookImageButton = (ImageButton) findViewById(R.id.facebookButton);
        twitterImageButton = (ImageButton) findViewById(R.id.twitterButton);
    }

    private void initializeBehaviours() {
        enterButton.setOnClickListener(this);
        signinButton.setOnClickListener(this);
        facebookImageButton.setOnClickListener(this);
        twitterImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(enterButton)) {
            //enterButtonAction();
            Intent intent = new Intent(MainAct.this, MapAct.class);
            MainAct.this.startActivity(intent);
        } else if (v.equals(signinButton)) {
            Intent it = new Intent(getApplicationContext(), SignupAct.class);
            startActivity(it);

        } else if (v.equals(facebookImageButton)) {
            Intent it = new Intent();
            it.setData(Uri.parse(ConstantsService.FACEBOOK));
            startActivity(it);
        } else if (v.equals(twitterImageButton)) {
            Intent it = new Intent();
            it.setData(Uri.parse(ConstantsService.TWITTER));
            startActivity(it);
        }
    }

    private void enterButtonAction() {
        if (Util.isOnlineWithToast(getApplicationContext())) {
            String emailLogin = userEditText.getText().toString();
            String passwordLogin = passwordEditText.getText().toString();
            new Service(this).find(UserApp.class,
                    new Pair<String, Object>("email", emailLogin),
                    new Pair<String, Object>("password", passwordLogin)).execute();
        }
    }


    @Override
    public void onFinishTask(Object result) {
        final String USER_ID = "userId";
        if (result != null) {
            try {
                UserApp loggedUser = (UserApp) result;
                StaticUtil.setOject(this, StaticUtil.LOGGED_USER, loggedUser);
                new Service(new OnFinishTask() {
                    @Override
                    public void onFinishTask(Object result) {
                        try {
                            Place place = (Place) result;
                            StaticUtil.setOject(MainAct.this, StaticUtil.PLACE, place);
                            Intent intent = new Intent(MainAct.this, MapAct.class);
                            MainAct.this.startActivity(intent);
                            MainAct.this.finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).find(Place.class, new Pair<>(USER_ID, loggedUser.getId())).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_LONG).show();
        }
    }
}