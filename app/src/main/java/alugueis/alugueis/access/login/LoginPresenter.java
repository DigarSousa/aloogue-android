package alugueis.alugueis.access.login;


import javax.inject.Inject;

import alugueis.alugueis.access.AccessRouter;
import alugueis.alugueis.model.User;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class LoginPresenter {
    private LoginInteractor loginInteractor;
    private LoginFragment loginFragment;
    private AccessRouter accessRouter;

    @Inject
    public LoginPresenter(LoginInteractor loginInteractor, AccessRouter accessRouter) {
        this.loginInteractor = loginInteractor;
        this.accessRouter = accessRouter;
    }

    public void setView(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public void doLogin(String email, String password) {
        changeViewToLoadState();

        loginInteractor.login(email, password,
                new DisposableObserver<User>() {
                    @Override
                    public void onNext(@NonNull User user) {
                        accessRouter.startMainActivity(loginFragment.getContext());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        changeViewToDefaultState();
                        loginFragment.error();
                    }

                    @Override
                    public void onComplete() {
                        loginInteractor.dispose();
                        changeViewToDefaultState();
                    }
                });
    }

    private void changeViewToLoadState() {
        loginFragment.blockViews();
        loginFragment.startViewAnimations();
    }

    private void changeViewToDefaultState() {
        loginFragment.clearViewStates();
        loginFragment.stopViewAnimations();
    }

    public void openSignUpFragment() {
        accessRouter.swapToSignUpFragment(loginFragment.getFragmentManager());
    }

    public void onDestroy() {
    }

}
