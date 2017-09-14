package alugueis.alugueis.login;

import android.view.View;

import javax.inject.Inject;

import alugueis.alugueis.model.User;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class LoginPresenter {

    @Inject
    LoginInteractor loginInteractor;

    public LoginPresenter(View view) {

    }


    public void onLoginPressed(String email, String password) {
        loginInteractor.login(email, password,
                new DisposableObserver<User>() {
                    @Override
                    public void onNext(@NonNull User user) {
                        //implementar router
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //para animaçao
                    }
                });
    }

    public void onDestroy() {
    }

}
