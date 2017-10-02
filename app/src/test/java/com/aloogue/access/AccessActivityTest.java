package com.aloogue.access;


import android.app.Fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import alugueis.alugueis.access.AccessActivity;
import alugueis.alugueis.access.AccessRouter;
import alugueis.alugueis.access.login.LoginFragment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class AccessActivityTest {
    private AccessActivity accessActivity;

    @Before
    public void setup() {
        accessActivity = Robolectric.setupActivity(AccessActivity.class);
    }

    @Test
    public void startActivityAndSetLoginFragmentInContainer() {
        Fragment fragment = accessActivity.getFragmentManager().findFragmentByTag(AccessRouter.LOGIN_FRAGMENT);
        assertThat(fragment).isNotNull();
        assertThat(fragment.getClass()).isEqualTo(LoginFragment.class);
    }
}
