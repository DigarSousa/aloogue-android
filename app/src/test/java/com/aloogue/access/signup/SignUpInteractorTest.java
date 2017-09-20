package com.aloogue.access.signup;

import com.aloogue.RxSchedulerTestSetup;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import alugueis.alugueis.access.AccessService;
import alugueis.alugueis.access.signup.SignupInteractor;
import alugueis.alugueis.model.User;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SignUpInteractorTest {
    @Mock
    AccessService accessService;

    @InjectMocks
    SignupInteractor signupInteractor;


    @BeforeClass
    public static void settings() {
        RxSchedulerTestSetup.setupRxScheduler();
    }

    @Test
    public void saveUserAndReturnIt() {
        User user = User.builder().id(1L).build();

        when(accessService.save(user)).thenReturn(Observable.just(user));
        signupInteractor.signUp(user, new DisposableObserver<User>() {
            @Override
            public void onNext(@NonNull User user) {
                assertThat(user).isNotNull();
                assertThat(user.getId()).isEqualTo(1L);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Test
    public void holdRightExceptionOnTryToSaveUser() {
        User user = User.builder()
                .id(1L)
                .email("user@user.com")
                .password("123456")
                .build();

        when(accessService.save(user)).thenReturn(Observable.error(new Throwable("errorOnTryToSaveUser")));

        signupInteractor.signUp(user, new DisposableObserver<User>() {
            @Override
            public void onNext(@NonNull User user) {

            }

            @Override
            public void onError(@NonNull Throwable e) {
                assertThat(e.getMessage()).isEqualTo("errorOnTryToSaveUser");
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
