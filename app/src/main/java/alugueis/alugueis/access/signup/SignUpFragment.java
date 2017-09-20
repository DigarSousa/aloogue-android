package alugueis.alugueis.access.signup;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alugueis.alugueis.R;
import alugueis.alugueis.butterknife.ButterKnifeViewControls;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignUpFragment extends Fragment {

    private Unbinder unbinder;
    private List<View> views;

    @BindView(R.id.userNameSignUp)
    EditText name;
    @BindView(R.id.userMailSignUp)
    EditText mail;
    @BindView(R.id.userPassSignUp)
    EditText pass;
    @BindView(R.id.signEnterButton)
    ActionProcessButton signUpButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        signUpButton.setMode(ActionProcessButton.Mode.ENDLESS);

        views = new ArrayList<>();
        views.addAll(Arrays.asList(name, mail, pass));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }



    private void clearState() {
        ButterKnife.apply(views, ButterKnifeViewControls.ENABLED, true);
        signUpButton.setProgress(0);
        signUpButton.setClickable(true);
    }
}
