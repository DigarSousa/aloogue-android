package alugueis.alugueis.access.login;

import javax.inject.Inject;

import alugueis.alugueis.access.AccessService;
import alugueis.alugueis.model.User;
import alugueis.alugueis.viper.arquitecture.Interactor;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class LoginInteractor extends Interactor<User> {
    private AccessService accessService;

    @Inject
    public LoginInteractor(AccessService loginService) {
        this.accessService = loginService;
    }

    public void login(String email, String pass, DisposableObserver<User> disposableObserver) {
        Observable<User> observable = accessService.login(email, pass);

        this.executeUniqueResult(observable, disposableObserver);
    }
}
