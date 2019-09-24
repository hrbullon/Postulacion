package com.example.napoleontest.login;

import android.content.Context;

import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;

import retrofit2.Retrofit;

public class LoginActivityPresenter{

    private LoginActivityView mView;
    private Context context;
    private Retrofit retrofit;
    private SecurityRepository mSecurityRepository;

    public LoginActivityPresenter(Context context, LoginActivityView view) {
        this.context = context;
        this.mView = view;
    }

    public LoginActivityPresenter(LoginActivityView mView, Context context, Retrofit retrofit, SecurityRepository mSecurityRepository) {
        this.mView = mView;
        this.context = context;
        this.retrofit = retrofit;
        this.mSecurityRepository = mSecurityRepository;
    }

    public void onLoginClick(){

        if(checkValidation()){
            mSecurityRepository.login(mView.getIdUser(),mView,context);
        }

    }

    private boolean checkValidation() {
        String getIdUser = mView.getIdUser();

        if(getIdUser.isEmpty()){

            mView.setAnimationCredentials();
        }else{
            return true;
        }
        return false;
    }
}
