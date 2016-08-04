
/*package alugueis.alugueis;

import alugueis.alugueis.model.*;
import alugueis.alugueis.util.StaticUtil;
import alugueis.alugueis.util.Util;
import android.support.v7.app.AppCompatActivity;
import service.httputil.OnFinishTask;
import service.httputil.Service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import com.google.android.gms.drive.events.TransferProgressEvent;

import java.io.IOException;
public class EditProfileAct extends AppCompatActivity {

    private Toolbar mainToolbar;
    private UserApp loggedUserApp;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private CheckBox acceptTermsCheckBox;
    private Button doneButton;
    //For image upload
    private Thread startSecondActivity;
    String sourceAct;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(this, R.layout.activity_edit_profile);

        this.context = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        loggedUserApp = new UserApp();

        initializeComponents();
        initializeListeners();

        if (extras != null) {
            sourceAct = extras.getString("source");
            //Se o extra vier da edição de cadastro (:
            assert sourceAct != null;
            if (sourceAct.equals("changeData")) {
                getLogged();
                populateControls();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditProfileAct.this, MapAct.class);
        startActivity(intent);
    }

    private void getLogged() {
        try {
            loggedUserApp = (UserApp) StaticUtil.readObject(context, StaticUtil.LOGGED_USER);
        } catch (Exception ex) {
        }
    }

    private void populateControls() {

        nameEditText.setText(this.loggedUserApp.getName());

        //Account
        emailEditText.setText(this.loggedUserApp.getEmail());
    }

    private void initializeListeners() {


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validated = validateComponents();
                if (validated) {
                    saveUser();
                } else {
                    Toast.makeText(getApplicationContext(), "O formulário contém alguns erros. Corrija-os e tente novamente! (:", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void saveUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Atualizando perfil...");
        loggedUserApp.setName(nameEditText.getText().toString());
        loggedUserApp.setEmail(emailEditText.getText().toString());
        loggedUserApp.setPassword(passwordEditText.getText().toString());

        new Service(new OnFinishTask() {
            @Override
            public void onFinishTask(Object result) {
                if (result == null || ((UserApp) result).getId() == null) {
                    Toast.makeText(EditProfileAct.this, "Erro ao editar perfil!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfileAct.this, "Perfil atualizado!", Toast.LENGTH_SHORT).show();
                }
                try {
                    StaticUtil.setOject(EditProfileAct.this, StaticUtil.LOGGED_USER, result);
                } catch (IOException e) {
                    Toast.makeText(EditProfileAct.this, "Erro ao editar perfil!", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }
        }, progressDialog).save(loggedUserApp, UserApp.class).execute();
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
        if (!acceptTermsCheckBox.isChecked()) {
            Toast.makeText(getApplicationContext(), "Para prosseguir, aceite os termos de uso.", Toast.LENGTH_SHORT).show();
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
        } else if (passwordEditText.getText().toString().length() < 4 || passwordEditText.getText().toString().length() > 32) {
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

        if (this.loggedUserApp != null) {
            //Desabilitando os termos de uso
            acceptTermsCheckBox.setChecked(true);
            acceptTermsCheckBox.setClickable(false);
        }

    }
}
