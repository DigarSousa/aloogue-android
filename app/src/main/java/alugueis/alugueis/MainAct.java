package alugueis.alugueis;

import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.util.Util;
import alugueis.alugueis.view.RoundedImageView;
import service.ConstantsService;
import service.LoginService;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainAct extends ActionBarActivity implements View.OnClickListener {

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

    private UserApp loggedUserApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        loggedUserApp = new UserApp();
        getLogged();

        initializeToolbar();
        initializeComponents();
        initializeBehaviours();
    }

    private void getLogged() {
        try {
            loggedUserApp = (UserApp) StaticUtil.readObject(context, StaticUtil.LOGGED_USER);
        } catch (Exception ex) {
        }
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

        if (loggedUserApp.getName() != null) {
            String userNamer = loggedUserApp.getName();
            String customWelcome = getResources().getString(R.string.welcomeCustomized) + " " + userNamer + "!";
            welcomeUser.setText(customWelcome);
        }

        pictureImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.emoticon_cool));

        if (loggedUserApp.getEmail() != null) {
            userEditText.setText(loggedUserApp.getEmail());
        }
        if (loggedUserApp.getPassword() != null) {
            passwordEditText.setText(loggedUserApp.getPassword());
        }

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
            enterButtonAction();
        } else if (v.equals(signinButton)) {
            Intent it = new Intent(context, SignupAct.class);
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

        if (Util.isOnlineWithToast(context)) {
            String emailLogin = userEditText.getText().toString();
            String passwordLogin = passwordEditText.getText().toString();
            new LoginService(getApplicationContext(), emailLogin, passwordLogin).execute();
        }
    }
}
