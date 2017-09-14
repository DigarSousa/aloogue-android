package alugueis.alugueis.dagger;


import javax.inject.Singleton;

import alugueis.alugueis.login.LoginService;
import alugueis.alugueis.services.StandardService;
import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    static LoginService provideLoginService() {
        return StandardService.createService(LoginService.class);
    }
}
