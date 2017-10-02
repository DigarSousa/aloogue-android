package alugueis.alugueis.access;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import javax.inject.Inject;

import alugueis.alugueis.R;
import alugueis.alugueis.access.login.LoginFragment;
import alugueis.alugueis.access.signup.SignUpFragment;

public class AccessRouter {
    public static final String LOGIN_FRAGMENT = "loginFragment";
    public static final String SIGNUP_FRAGMENT = "signUpFragment";

    @Inject
    public AccessRouter() {

    }

    public void inflateLoginFragment(FragmentManager fragmentManager) {
        LoginFragment loginFragment = new LoginFragment();

        fragmentManager
                .beginTransaction()
                .add(R.id.login_sign_up_fragment, loginFragment, LOGIN_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    public void startMainActivity(Context context) {
    }

    public void swapToSignUpFragment(FragmentManager fragmentManager) {
        SignUpFragment signUpFragment = new SignUpFragment();

        fragmentManager
                .beginTransaction()
                .replace(R.id.login_sign_up_fragment, signUpFragment, SIGNUP_FRAGMENT)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
