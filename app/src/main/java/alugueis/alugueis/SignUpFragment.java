package alugueis.alugueis;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alugueis.alugueis.abstractiontools.ButterKnifeViewControls;
import alugueis.alugueis.dialogs.DialogsUtil;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.services.StdService;
import alugueis.alugueis.services.user.UserService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @OnClick(R.id.signEnterButton)
    void signUpAction() {

        UserApp userApp = new UserApp();
        userApp.setName(name.getText().toString());
        userApp.setEmail(mail.getText().toString());
        userApp.setPassword(pass.getText().toString());

        ButterKnife.apply(views, ButterKnifeViewControls.ENABLED, false);
        doSignUp(userApp);
    }

    private void doSignUp(UserApp userApp) {
        signUpButton.setProgress(1);
        UserService userService;
        try {
            userService = StdService.createService(UserService.class, getContext());
            Call<UserApp> call = userService.save(userApp);
            call.enqueue(new Callback<UserApp>() {
                @Override
                public void onResponse(Call<UserApp> call, Response<UserApp> response) {
                    ((StartActivity) getActivity()).startMainActivity();
                }

                @Override
                public void onFailure(Call<UserApp> call, Throwable t) {
                    ButterKnife.apply(views, ButterKnifeViewControls.ENABLED, true);
                }
            });
        }catch (ConnectException e){
            Log.e(getClass().getCanonicalName(), "Save user error", e);
            DialogsUtil.connectionError(getActivity());
        }
    }
}
