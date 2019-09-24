package com.example.napoleontest.domain.repository.implementation;

import android.content.Context;

import com.example.napoleontest.domain.model.User;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.login.LoginActivityView;
import com.example.napoleontest.networkservice.NetworkAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SecurityRepositoryImpl implements SecurityRepository {

    protected static final String TAG = SecurityRepository.class.getSimpleName();
    Retrofit retrofit;

    public SecurityRepositoryImpl(Retrofit retrofit)
    {
        this.retrofit = retrofit;
    }


    @Override
    public void login(String idUser, final LoginActivityView mView, Context context) {
        mView.showProgressDialog();
        Call call = retrofit.create(NetworkAPI.class).getAllUsers();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.code() == 200){
                    mView.hideProgressDialog();
                    List<User> mListUser = (List<User>) response.body();
                    mView.getDataUser(mListUser);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
