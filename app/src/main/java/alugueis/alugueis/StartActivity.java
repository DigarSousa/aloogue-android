package alugueis.alugueis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        ButterKnife.bind(this);
    }

    void loadSignUpFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_sign_up_fragment, new SignUpFragment())
                .commit();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}