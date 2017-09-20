package alugueis.alugueis.dagger;


import javax.inject.Singleton;

import alugueis.alugueis.access.AccessService;
import alugueis.alugueis.retrofit.StandardService;
import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    static AccessService provideLoginService() {
        return StandardService.createService(AccessService.class);
    }
}
