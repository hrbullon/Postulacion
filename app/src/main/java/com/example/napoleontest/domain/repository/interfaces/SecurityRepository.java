package com.example.napoleontest.domain.repository.interfaces;

import android.content.Context;

import com.example.napoleontest.login.LoginActivityView;

public interface SecurityRepository {

    void login(String idUser, LoginActivityView mView, Context context);
}
