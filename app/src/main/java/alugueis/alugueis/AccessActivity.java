package alugueis.alugueis;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import alugueis.alugueis.login.LoginFragment;
import butterknife.ButterKnife;

public class AccessActivity extends AppCompatActivity {
    private static final String TAG = "AccessActivity";
    private Fragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        loginFragment = new LoginFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.login_sign_up_fragment, loginFragment)
                .commit();

        ButterKnife.bind(this);
    }

    public void loadSignUpFragment() {
        getFragmentManager()
                .beginTransaction()
                .hide(loginFragment)
                .add(R.id.login_sign_up_fragment, new SignUpFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    @Override
    public void onBackPressed() {
        if (isOpen(SignUpFragment.class)) {
            getFragmentManager().beginTransaction()
                    .detach(getCurrentFragment())
                    .show(loginFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            return;
        }
        super.onBackPressed();
    }

    private Boolean isOpen(Class fragmentClass) {
        return getCurrentFragment().getClass().getName().equals(fragmentClass.getName());
    }


    private Fragment getCurrentFragment() {
        return getFragmentManager().findFragmentById(R.id.login_sign_up_fragment);
    }
}