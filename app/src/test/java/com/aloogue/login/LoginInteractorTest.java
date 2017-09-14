package com.aloogue.login;


import com.aloogue.AndroidMainThreadConfig;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import alugueis.alugueis.login.LoginInteractor;
import alugueis.alugueis.login.LoginService;
import alugueis.alugueis.model.User;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


@RunWith(MockitoJUnitRunner.class)
public class LoginInteractorTest extends AndroidMainThreadConfig {

    @Mock
    LoginService loginService;

    @InjectMocks
    LoginInteractor loginInteractor;

    @Test
    public void findUserByEmailAndPassword() {
        User user = User.builder()
                .email("user@mail.com")
                .password("123456")
                .id(1L)
                .build();

        when(loginService.login("user@mail.com", "123456")).thenReturn(Observable.just(user));

        loginInteractor.login("user@mail.com", "123456", new DisposableObserver<User>() {
            @Override
            public void onNext(@NonNull User user) {
                assertThat(user.getId()).isEqualTo(1L);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void holRightExceptionOnTryToFindUserByEmailAndPass() {
        when(loginService.login("erro@erro.com", "erro")).thenReturn(Observable.error(new Throwable("errorToConect")));

        loginInteractor.login("erro@erro.com", "erro", new DisposableObserver<User>() {
            @Override
            public void onNext(@NonNull User user) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                assertThat(e.getMessage()).isEqualTo("errorToConect");
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
