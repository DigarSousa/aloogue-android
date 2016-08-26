package alugueis.alugueis;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import alugueis.alugueis.model.UserApp;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SignUpFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.userNameSignUp)
    EditText name;
    @BindView(R.id.userMailSignUp)
    EditText mail;
    @BindView(R.id.userPassSignUp)
    EditText pass;
    @BindView(R.id.signEnterButton)
    Button signUp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.signEnterButton)
    void signUpAction() {
        UserApp userApp = new UserApp();
        userApp.setName(name.getText().toString());
        userApp.setEmail(mail.getText().toString());
        userApp.setPassword(pass.getText().toString());

        //retrofit...
    }
}
