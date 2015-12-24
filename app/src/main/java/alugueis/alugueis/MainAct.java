package alugueis.alugueis;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import alugueis.alugueis.model.LoggedUser;
import alugueis.alugueis.model.User;
import alugueis.alugueis.util.UserUtil;
import alugueis.alugueis.util.Util;
import alugueis.alugueis.view.RoundedImageView;

public class MainAct extends ActionBarActivity {

    private Context context;
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

    private LoggedUser loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        User u = new User();
        loggedUser = LoggedUser.getInstance();

        tryToGetLogged();
        initializeToolbar();
        initializeComponents();
        initializeBehaviours();
    }

    private void tryToGetLogged() {

        List<LoggedUser>loggedUsers = LoggedUser.listAll(LoggedUser.class);
        if(loggedUsers.size() > 0){
            loggedUser = loggedUsers.get(0);
        }
    }

    private void initializeToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainToolbar.setTitle("");
        setSupportActionBar(mainToolbar);
        //mainToolbar.setTitle("iJob");
        //mainToolbar.setSubtitle("v. 1.0.0");
        //mainToolbar.setLogo(R.drawable.);
    }

    private void initializeComponents() {

        userEditText = (EditText) findViewById(R.id.userNameLogin);
        passwordEditText = (EditText) findViewById(R.id.passwordLogin);
        enterButton = (Button) findViewById(R.id.enterButton);
        signinButton = (Button) findViewById(R.id.signinButton);
        bottomToolbar = (Toolbar) findViewById(R.id.tb_bottom);
        pictureImageView = (RoundedImageView) findViewById(R.id.pictureImage);
        welcomeUser = (TextView) findViewById(R.id.welcomeUser);

        if(loggedUser.getName() != null){
            String userNamer = loggedUser.getName();
            welcomeUser.setText(getResources().getString(R.string.welcomeCustomized) + " " + userNamer + "! (:");
        }

        if(loggedUser.getPicture() != null){
            byte[] userPic = loggedUser.getPicture();
            pictureImageView.setImageBitmap(BitmapFactory.decodeByteArray(userPic, 0, userPic.length));
        }
        else {
            pictureImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.emoticon_cool));
        }

        if(loggedUser.getEmail() != null){
            userEditText.setText(loggedUser.getEmail().toString());
        }

        facebookImageButton = (ImageButton) findViewById(R.id.facebookButton);
        twitterImageButton = (ImageButton) findViewById(R.id.twitterButton);
    }

    private void initializeBehaviours() {

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.isOnlineWithToast(context)) {
                    String emailLogin = userEditText.getText().toString();
                    String passwordLogin = passwordEditText.getText().toString();
                    if (userEditText.getText().toString() == null ||
                        userEditText.getText().toString().trim().equals("") ||
                        !UserUtil.validateLogin(emailLogin, passwordLogin)) {

                        userEditText.setError(getResources().getString(R.string.invalidUser));
                    }
                    else{
                        Intent intent = new Intent(MainAct.this, MapAct.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, SignupAct.class);
                startActivity(it);
            }
        });

        facebookImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setData(Uri.parse("http://facebook.com"));
                startActivity(it);
            }
        });

        twitterImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setData(Uri.parse("http://twitter.com"));
                startActivity(it);
            }
        });

    }

}
