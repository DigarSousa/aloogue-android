package com.aloogue.access.login;

import android.app.FragmentManager;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import alugueis.alugueis.R;
import alugueis.alugueis.access.AccessActivity;
import alugueis.alugueis.access.AccessRouter;
import alugueis.alugueis.access.login.LoginFragment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class LoginFragmentTest {
    private AccessActivity accessActivity;

    @Before
    public void setup() {
        accessActivity = Robolectric.setupActivity(AccessActivity.class);
    }

    @Test
    public void setLoginButtonStatusTo1OnStartLoginProcess() {
        LoginFragment loginFragment = getLoginFragment(accessActivity.getFragmentManager());
        ActionProcessButton loginButton = accessActivity.findViewById(R.id.loginButton);

        loginFragment.startViewAnimations();
        assertThat(loginButton.getProgress()).isEqualTo(1);
    }

    @Test
    public void blockViewsOnStartLoginProcess() {
        EditText txtLogin = accessActivity.findViewById(R.id.userNameLogin);
        EditText txtPassword = accessActivity.findViewById(R.id.passwordLogin);

        LoginFragment loginFragment = getLoginFragment(accessActivity.getFragmentManager());
        loginFragment.blockViews();
        assertThat(txtLogin.isEnabled()).isFalse();
        assertThat(txtPassword.isEnabled()).isFalse();
    }

    private LoginFragment getLoginFragment(FragmentManager fragmentManager) {
        return (LoginFragment) fragmentManager
                .findFragmentByTag(AccessRouter.LOGIN_FRAGMENT);
    }
}
