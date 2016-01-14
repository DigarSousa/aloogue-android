package alugueis.alugueis;

import alugueis.alugueis.model.*;
import alugueis.alugueis.util.ImageUtil;
import alugueis.alugueis.util.UserUtil;
import alugueis.alugueis.util.Util;
import alugueis.alugueis.view.RoundedImageView;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.util.List;

public class SignupAct extends ActionBarActivity {

    private Toolbar mainToolbar;
    private LoggedUser loggedUser;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private CheckBox acceptTermsCheckBox;
    private Button doneButton;
    //For image upload
    private Thread startSecondActivity;
    String sourceAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Bundle extras = getIntent().getExtras();

        initializeToolbar();
        initializeComponents();
        initializeListeners();
        initializeSecondActivityThread();

        if(extras != null){
            sourceAct = extras.getString("source");
            //Se o extra vier da edição de cadastro (:
            if(sourceAct.equals("changeData")){
                this.loggedUser = UserUtil.getLogged();
                populateControls();
            }
        }
    }

    private void populateControls() {

        nameEditText.setText(this.loggedUser.getName().toString());

        //Account
        emailEditText.setText(this.loggedUser.getEmail().toString());
    }

    private void initializeSecondActivityThread() {
        startSecondActivity = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                    Intent intent;
                    if(sourceAct != null && sourceAct.equals("changeData")){
                        intent = new Intent(SignupAct.this, MapAct.class);
                    }
                    else{
                        intent = new Intent(SignupAct.this, MainAct.class);
                    }
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void initializeListeners() {


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

    }

    private void saveNewUser() {

        loggedUser.setName(nameEditText.getText().toString());
        loggedUser.setEmail(emailEditText.getText().toString());
        loggedUser.setPassword(passwordEditText.getText().toString());
        loggedUser.setPicture(ImageUtil.BitmapToByteArray(BitmapFactory.decodeResource(getResources(), R.drawable.emoticon_cool)));

        LoggedUser.deleteAll(LoggedUser.class);
        loggedUser.save();
        Toast.makeText(getApplicationContext(), "Usuário salvo com sucesso. (:", Toast.LENGTH_LONG).show();
        startSecondActivity.start();
    }

    private boolean validateComponents() {
        boolean validated = true;

        if (!validateName()) {
            nameEditText.setError(getResources().getString(R.string.emptyName));
            validated = false;
        }

        if (!validateEmail()) {
            emailEditText.setError(getResources().getString(R.string.invalidEmail));
            validated = false;
        }
        if (!validatePassword()) {
            validated = false;
        }
        if (!validatePasswordConfirm()) {
            validated = false;
        }
        if (!validateAcceptedTerms()) {
            acceptTermsCheckBox.setError(getResources().getString(R.string.acceptTermsError));
            validated = false;
        }
        return validated;
    }

    private boolean validateEmail() {
        if (!Util.isValidEmail(emailEditText.getText().toString())) {
            return false;
        }
        return true;
    }


    private boolean validateAcceptedTerms() {
        //TODO: Validar accept changes apenas com toast
        if (!acceptTermsCheckBox.isChecked()) {
            return false;
        }
        return true;
    }

    private boolean validatePasswordConfirm() {
        if (!passwordConfirmEditText.getText().toString().equals(passwordEditText.getText().toString())) {
            passwordConfirmEditText.setError(getResources().getString(R.string.wrongPasswordConfirm));
            return false;
        }
        if (passwordConfirmEditText.getText().toString().equals("")) {
            passwordConfirmEditText.setError(getResources().getString(R.string.emptyPasswordConfirm));
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if (passwordEditText.getText().toString().equals("")) {
            passwordEditText.setError(getResources().getString(R.string.emptyPassword));
            return false;
        } else if (passwordEditText.getText().toString().length() < 4 || passwordEditText.getText().toString().length() > 10) {
            passwordEditText.setError(getResources().getString(R.string.minMaxPasswordTextError));
            return false;
        }
        return true;
    }

    private boolean validateName() {
        if (nameEditText.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private void initializeToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mainToolbar.setTitle(R.string.signup);
        setSupportActionBar(mainToolbar);
        //mainToolbar.setSubtitle("v. 1.0.0");
        //mainToolbar.setLogo(R.drawable.logo_branco);
    }

    private void initializeComponents() {


        mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);

        nameEditText = (EditText) findViewById(R.id.nameText);

        //Account
        emailEditText = (EditText) findViewById(R.id.emailText);
        passwordEditText = (EditText) findViewById(R.id.passwordText);
        passwordConfirmEditText = (EditText) findViewById(R.id.passwordConfirmText);

        //Terms
        acceptTermsCheckBox = (CheckBox) findViewById(R.id.acceptTermsCheck);

        //Done
        doneButton = (Button) findViewById(R.id.iAmDoneButton);

        if(this.loggedUser != null){
            //Desabilitando os termos de uso
            acceptTermsCheckBox.setChecked(true);
            acceptTermsCheckBox.setClickable(false);
        }

    }

}
