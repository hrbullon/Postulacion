package com.example.napoleontest.aplicattion;


import com.example.napoleontest.actitivies.DetailPostActivity;
import com.example.napoleontest.fragment.AllPostFragment;
import com.example.napoleontest.fragment.FavoritesFragment;
import com.example.napoleontest.fragment.MyPostFragment;
import com.example.napoleontest.login.LoginActivity;
import com.example.napoleontest.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules ={
                AppModule.class,
                NetModule.class,
                RepositoryModule.class,
                SqliteModule.class
        }
)
public interface AppComponent {
    void inject(LoginActivity activity);
    void inject(MainActivity activity);
    void inject(MyPostFragment fragment);
    void inject (AllPostFragment fragment);
    void inject(DetailPostActivity activity);
    void inject(FavoritesFragment fragment);

}



