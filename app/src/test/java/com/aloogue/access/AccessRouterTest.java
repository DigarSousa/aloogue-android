package com.aloogue.access;

import android.app.Fragment;
import android.app.FragmentManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import alugueis.alugueis.R;
import alugueis.alugueis.access.AccessActivity;
import alugueis.alugueis.access.AccessRouter;
import alugueis.alugueis.access.login.LoginFragment;
import alugueis.alugueis.access.signup.SignUpFragment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class AccessRouterTest {
    private FragmentManager fragmentManager;
    private AccessRouter accessRouter;

    @Before
    public void setup() {
        AccessActivity accessActivity = Robolectric.setupActivity(AccessActivity.class);
        fragmentManager = accessActivity.getFragmentManager();

        fragmentManager.beginTransaction()
                .remove(fragmentManager.findFragmentById(R.id.login_sign_up_fragment))
                .commit();

        accessRouter = new AccessRouter();
    }

    @Test
    public void addLoginFragmentAndMakeItVisible() {
        accessRouter.inflateLoginFragment(fragmentManager);

        Fragment fragment = fragmentManager.findFragmentByTag(AccessRouter.LOGIN_FRAGMENT);

        assertThat(fragment).isNotNull();
        assertThat(fragment.getClass()).isEqualTo(LoginFragment.class);
        assertThat(fragment.isVisible());
    }

    @Test
    public void replaceLoginFragmentBySignUpFragment() {
        accessRouter.inflateLoginFragment(fragmentManager);
        accessRouter.swapToSignUpFragment(fragmentManager);

        Fragment signUpFragment = fragmentManager.findFragmentById(R.id.login_sign_up_fragment);

        assertThat(signUpFragment).isNotNull();
        assertThat(signUpFragment.getClass()).isEqualTo(SignUpFragment.class);
        assertThat(signUpFragment.isVisible());
    }
}
