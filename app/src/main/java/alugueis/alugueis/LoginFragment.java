package alugueis.alugueis;

import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.services.StdService;
import alugueis.alugueis.services.user.UserService;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private String TAG = "LoginFragment";

    @BindView(R.id.userNameLogin)
    EditText userNameLogin;

    @BindView(R.id.passwordLogin)
    EditText passwordLogin;

    @BindView(R.id.enterButton)
    Button enterButton;

    @BindView(R.id.signUp)
    TextView signUpButton;

    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.enterButton)
    public void enter(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        UserService userService = StdService.createService(UserService.class);
        Call<UserApp> call = userService.login(userNameLogin.getText().toString(), passwordLogin.getText().toString());
        call.enqueue(new Callback<UserApp>() {
            @Override
            public void onResponse(Call<UserApp> call, Response<UserApp> response) {
                if (response.body() != null) {
                    ((StartActivity) getActivity()).startMainActivity();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserApp> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "Login failure", t);
            }
        });
    }


    @OnClick(R.id.signUp)
    public void createAccount(View view) {
        ((StartActivity) getActivity()).loadSignUpFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
