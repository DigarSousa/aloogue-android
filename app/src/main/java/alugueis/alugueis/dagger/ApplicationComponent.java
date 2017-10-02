package alugueis.alugueis.dagger;

import javax.inject.Singleton;

import alugueis.alugueis.access.AccessActivity;
import alugueis.alugueis.access.AccessService;
import alugueis.alugueis.access.login.LoginFragment;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {
    AccessService make();

    void inject(LoginFragment loginFragment);

    void inject(AccessActivity accessActivity);
}
