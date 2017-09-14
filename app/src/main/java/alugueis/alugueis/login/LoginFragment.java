package alugueis.alugueis.login;

import alugueis.alugueis.R;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.List;

public class LoginFragment extends Fragment {
    private String TAG = "LoginFragment";

    @BindView(R.id.userNameLogin)
    EditText userNameLogin;

    @BindView(R.id.passwordLogin)
    EditText passwordLogin;


    @BindView(R.id.enterButton)
    ActionProcessButton enterButton;

    @BindView(R.id.signUp)
    TextView signUpButton;

    @BindViews({R.id.userNameLogin, R.id.passwordLogin, R.id.enterButton, R.id.signUp})
    List<View> views;

    private Unbinder unbinder;
    private LoginPresenter loginPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);
        loginPresenter = new LoginPresenter(view);
        return view;
    }

    @OnClick(R.id.enterButton)
    public void doLogin() {
        loginPresenter.onLoginPressed(userNameLogin.getText().toString(), passwordLogin.getText().toString());
    }

    @Override
    public void onDestroy() {
        loginPresenter.onDestroy();
        unbinder.unbind();
        super.onDestroy();
    }
}
