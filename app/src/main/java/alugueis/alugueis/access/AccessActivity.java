package alugueis.alugueis.access;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import alugueis.alugueis.R;
import alugueis.alugueis.access.signup.SignUpFragment;
import alugueis.alugueis.access.login.LoginFragment;
import butterknife.ButterKnife;

public class AccessActivity extends AppCompatActivity {
    private static final String TAG = "AccessActivity";
    private Fragment loginFragment;

    @Inject
    AccessRouter accessRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        loginFragment = new LoginFragment();

        loadSignUpFragment();
        ButterKnife.bind(this);
    }

    public void loadSignUpFragment() {
        accessRouter.inflateLoginFragment(getFragmentManager());
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