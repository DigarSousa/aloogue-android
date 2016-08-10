package alugueis.alugueis;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.services.StdService;
import alugueis.alugueis.services.user.UserService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = "StartActivity";

    @BindView(R.id.userNameLogin)
    EditText userNameLogin;

    @BindView(R.id.passwordLogin)
    EditText passwordLogin;

    @BindView(R.id.enterButton)
    Button enterButton;

    @BindView(R.id.signUp)
    TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.enterButton)
    public void enter(View view) {
        UserService userService = StdService.createService(UserService.class);
        Call<UserApp> call = userService.login(userNameLogin.getText().toString(), passwordLogin.getText().toString());
        call.enqueue(new Callback<UserApp>() {
            @Override
            public void onResponse(Call<UserApp> call, Response<UserApp> response) {
                if (response.body() != null) {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<UserApp> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
        this.finish();
    }

    @OnClick(R.id.signUp)
    public void createAccount(View view) {

    }
}