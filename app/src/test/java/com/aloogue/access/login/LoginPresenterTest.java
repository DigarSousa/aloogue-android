package com.aloogue.access.login;

import com.aloogue.RxSchedulerTestSetup;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import alugueis.alugueis.access.AccessRouter;
import alugueis.alugueis.access.AccessService;
import alugueis.alugueis.access.login.LoginFragment;
import alugueis.alugueis.access.login.LoginInteractor;
import alugueis.alugueis.access.login.LoginPresenter;
import alugueis.alugueis.model.User;
import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private AccessService accessService;

    @Mock
    private AccessRouter accessRouter;

    @Mock
    private LoginFragment loginFragment;

    private LoginPresenter loginPresenter;

    @BeforeClass
    public static void settings() {
        RxSchedulerTestSetup.setupRxScheduler();
    }

    @Before
    public void setup() {
        loginPresenter = new LoginPresenter(new LoginInteractor(accessService), accessRouter);
        loginPresenter.setView(loginFragment);
    }

    @Test
    public void blockOnStartAndClearViewStateOnCompleteLoginTask() {
        when(accessService.login("a@a.com", "123456")).thenReturn(Observable.just(User.builder().build()));

        loginPresenter.doLogin("a@a.com", "123456");

        verify(loginFragment).startViewAnimations();
        verify(loginFragment).blockViews();
        verify(loginFragment).stopViewAnimations();
        verify(loginFragment).clearViewStates();
    }

    @Test
    public void openMainActivityOnLoginSuccess() {
        when(accessService.login("a@a.com", "123456")).thenReturn(Observable.just(User.builder().build()));

        loginPresenter.doLogin("a@a.com", "123456");
        verify(accessRouter).startMainActivity(loginFragment.getContext());
    }


    @Test
    public void clearViewStateOnErrorAndShowIt() {
        when(accessService.login("error@error.com", "123456")).thenReturn(Observable.error(new Throwable("blablabla")));

        loginPresenter.doLogin("error@error.com", "123456");

        verify(loginFragment).stopViewAnimations();
        verify(loginFragment).clearViewStates();
        verify(loginFragment).error();
    }

    @Test
    public void inflateSignUpFragmentOnPressSignUp() {
        loginPresenter.openSignUpFragment();
        verify(accessRouter).swapToSignUpFragment(loginFragment.getFragmentManager());
    }
}
