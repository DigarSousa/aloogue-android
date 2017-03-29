package alugueis.alugueis;

import alugueis.alugueis.abstractiontools.ButterKnifeViewControls;
import alugueis.alugueis.dialogs.ErrorDialog;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.model.UserApp;
import alugueis.alugueis.services.StdService;
import alugueis.alugueis.services.place.PlaceService;
import alugueis.alugueis.services.user.UserService;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alugueis.alugueis.util.StaticUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//todo: Não permitir campos vazios
//todo: Verificar email
public class LoginFragment extends Fragment implements DialogInterface.OnDismissListener {
    private String TAG = "LoginFragment";
    private List<View> views;

    @BindView(R.id.userNameLogin)
    EditText userNameLogin;

    @BindView(R.id.passwordLogin)
    EditText passwordLogin;

    @BindView(R.id.enterButton)
    ActionProcessButton enterButton;

    @BindView(R.id.signUp)
    TextView signUpButton;

    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        enterButton.setMode(ActionProcessButton.Mode.ENDLESS);

        views = new ArrayList<>();
        views.addAll(Arrays.asList(userNameLogin, passwordLogin, signUpButton));
        return view;
    }

    @OnClick(R.id.enterButton)
    void enter(View view) {
        enterButton.setClickable(false);
        enterButton.setProgress(1);
        ButterKnife.apply(views, ButterKnifeViewControls.ENABLED, false);

        UserService userService = StdService.createService(UserService.class);
        Call<UserApp> call = userService.login(userNameLogin.getText().toString(), passwordLogin.getText().toString());
        call.enqueue(new Callback<UserApp>() {
            @Override
            public void onResponse(Call<UserApp> call, Response<UserApp> response) {
                if (response.code() == StdService.NOT_FOUND) {
                    new ErrorDialog(getActivity(), getString(R.string.deniedlogintitle))
                            .setIcon(R.drawable.ic_warning)
                            .setOnDimissListener(LoginFragment.this).show();
                    return;
                }

                try {
                    StaticUtil.setOject(getContext(), StaticUtil.LOGGED_USER, response.body());
                    loadPlace(response.body());
                } catch (IOException e) {
                    onFailure(call, e);
                }
            }

            @Override
            public void onFailure(Call<UserApp> call, Throwable t) {
                falure(t);
            }
        });
    }

    private void loadPlace(UserApp user) {
        PlaceService placeService = StdService.createService(PlaceService.class);
        Call<Place> call = placeService.placeByUserId(user.getId());
        call.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                if (response.code() == StdService.ACCEPTED) {
                    try {
                        StaticUtil.setOject(getContext(), StaticUtil.PLACE, response.body());
                    } catch (IOException e) {
                        onFailure(call, e);
                    }
                }
                ((StartActivity) getActivity()).startMainActivity();
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                falure(t);
            }
        });
    }

    private void falure(Throwable t) {
        Log.e(TAG, "Load place failure", t);
        new ErrorDialog(getActivity(), getString(R.string.errorLoginTitle))
                .setErrorMsg(getString(R.string.errorLoginMsg))
                .setOnDimissListener(LoginFragment.this).show();

    }

    private void clearState() {
        ButterKnife.apply(views, ButterKnifeViewControls.ENABLED, true);
        enterButton.setProgress(0);
        enterButton.setClickable(true);
    }

    @OnClick(R.id.signUp)
    void createAccount(View view) {
        ((StartActivity) getActivity()).loadSignUpFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        clearState();
    }
}
