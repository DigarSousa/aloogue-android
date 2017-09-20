package alugueis.alugueis.access;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import javax.inject.Inject;

import alugueis.alugueis.R;
import alugueis.alugueis.access.login.LoginFragment;

public class AccessRouter {

    @Inject
    public AccessRouter() {

    }

    public void inflateLoginFragment(FragmentManager fragmentManager) {
        LoginFragment loginFragment = new LoginFragment();

        fragmentManager
                .beginTransaction()
                .add(R.id.login_sign_up_fragment, loginFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    public void startMainActivity(Context context) {
    }

    public void infalteSignUpFragment(FragmentManager fragmentManager) {
    }
}
