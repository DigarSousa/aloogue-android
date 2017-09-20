package com.aloogue.access.login;

import com.aloogue.RxSchedulerTestSetup;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import alugueis.alugueis.access.AccessService;
import alugueis.alugueis.access.login.LoginInteractor;
import alugueis.alugueis.model.User;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginInteractorTest {

    @Mock
    AccessService loginService;

    @InjectMocks
    LoginInteractor loginInteractor;

    @BeforeClass
    public static void setup() {
        RxSchedulerTestSetup.setupRxScheduler();
    }

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
    public void holdRightExceptionOnTryToFindUserByEmailAndPass() {
        when(loginService.login("erro@erro.com", "error")).thenReturn(Observable.error(new Throwable("errorToConnect")));

        loginInteractor.login("erro@erro.com", "error", new DisposableObserver<User>() {
            @Override
            public void onNext(@NonNull User user) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                assertThat(e.getMessage()).isEqualTo("errorToConnect");
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
