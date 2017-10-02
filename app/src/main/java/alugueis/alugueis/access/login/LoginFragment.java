package alugueis.alugueis.access.login;

import alugueis.alugueis.R;
import alugueis.alugueis.butterknife.ButterKnifeViewControls;
import alugueis.alugueis.dagger.DaggerApplicationComponent;
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

import javax.inject.Inject;

public class LoginFragment extends Fragment {
    private String TAG = "LoginFragment";

    @BindView(R.id.userNameLogin)
    EditText userNameLogin;

    @BindView(R.id.passwordLogin)
    EditText passwordLogin;


    @BindView(R.id.loginButton)
    ActionProcessButton enterButton;

    @BindView(R.id.signUp)
    TextView signUpButton;

    @BindViews({R.id.userNameLogin, R.id.passwordLogin, R.id.loginButton, R.id.signUp})
    List<View> views;

    private Unbinder unbinder;

    @Inject
    LoginPresenter loginPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        DaggerApplicationComponent.create().inject(this);
        setupViews();
        return view;
    }

    private void setupViews() {
        enterButton.setMode(ActionProcessButton.Mode.ENDLESS);
    }

    @OnClick(R.id.loginButton)
    public void doLogin() {
        loginPresenter.doLogin(userNameLogin.getText().toString(), passwordLogin.getText().toString());
    }

    @Override
    public void onDestroy() {
        loginPresenter.onDestroy();
        unbinder.unbind();
        super.onDestroy();
    }

    public void startViewAnimations() {
        enterButton.setProgress(1);
    }

    public void blockViews() {
        ButterKnife.apply(views, ButterKnifeViewControls.ENABLED, false);
    }

    public void stopViewAnimations() {
        enterButton.setProgress(0);
    }

    public void clearViewStates() {
        //chamar butter knife
    }

    public void error() {
        //chamar dialogo para mostrar erro
    }
}
