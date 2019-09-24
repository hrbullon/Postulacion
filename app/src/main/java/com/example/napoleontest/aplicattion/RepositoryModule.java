package com.example.napoleontest.aplicattion;

import com.example.napoleontest.domain.repository.implementation.SecurityRepositoryImpl;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public SecurityRepository provideSecurityRepository(Retrofit retrofit)
    {
        return new SecurityRepositoryImpl(retrofit);
    }
}
