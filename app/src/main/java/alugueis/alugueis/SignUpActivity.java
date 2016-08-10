package alugueis.alugueis;

import alugueis.alugueis.model.*;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.widget.*;

public class SignUpActivity extends Fragment {

    private Toolbar mainToolbar;
    private UserApp loggedUserApp;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private CheckBox acceptTermsCheckBox;
    private Button doneButton;
    private ProgressDialog progressDialog;
    //For image upload
    private Context context;

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        this.context = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        loggedUserApp = new UserApp();

        initializeToolbar();
        initializeComponents();
        initializeListeners();
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
                    saveNewUser();
                } else {
                    Toast.makeText(getApplicationContext(), "O formulário contém alguns erros. Corrija-os e tente novamente! (:", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void saveNewUser() {
        loggedUserApp.setName(nameEditText.getText().toString());
        loggedUserApp.setEmail(emailEditText.getText().toString());
        loggedUserApp.setPassword(passwordEditText.getText().toString());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Criando nova conta...");
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

        if (this.loggedUserApp != null) {
            //Desabilitando os termos de uso
            acceptTermsCheckBox.setChecked(true);
            acceptTermsCheckBox.setClickable(false);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }*/


}
