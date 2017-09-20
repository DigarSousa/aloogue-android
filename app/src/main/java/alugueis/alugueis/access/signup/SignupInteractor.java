package alugueis.alugueis.access.signup;

import javax.inject.Inject;

import alugueis.alugueis.access.AccessService;
import alugueis.alugueis.model.User;
import alugueis.alugueis.viper.arquitecture.Interactor;
import io.reactivex.observers.DisposableObserver;

public class SignupInteractor extends Interactor<User> {
    private AccessService accessService;

    @Inject
    public SignupInteractor(AccessService accessService) {
        this.accessService = accessService;
    }

    public void signUp(User user, DisposableObserver<User> disposableObserver) {
        this.executeUniqueResult(accessService.save(user), disposableObserver);
    }
}
