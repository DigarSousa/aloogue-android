package alugueis.alugueis.dagger;

import javax.inject.Singleton;

import alugueis.alugueis.login.LoginService;
import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    LoginService bla();
}
