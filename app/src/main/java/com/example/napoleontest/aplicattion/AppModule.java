package com.example.napoleontest.aplicattion;

import android.app.Application;

import com.example.napoleontest.Infraestructure.InternetManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    protected Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Application providesApplication(){
        return mApplication;
    }

    @Provides
    @Singleton
    public InternetManager providesInternetManager(Application application)
    {
        return new InternetManager(application);
    }
}
