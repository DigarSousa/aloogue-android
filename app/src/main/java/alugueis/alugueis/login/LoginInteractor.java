package alugueis.alugueis.login;

import javax.inject.Inject;

import alugueis.alugueis.model.User;
import alugueis.alugueis.viper.arquitecture.Interactor;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class LoginInteractor extends Interactor<User> {
    private LoginService loginService;

    @Inject
    public LoginInteractor(LoginService loginService) {
        this.loginService = loginService;
    }

    public void login(String email, String pass, DisposableObserver<User> disposableObserver) {
        Observable<User> observable = loginService.login(email, pass);

        this.executeUniqueResult(observable, disposableObserver);
    }
}
