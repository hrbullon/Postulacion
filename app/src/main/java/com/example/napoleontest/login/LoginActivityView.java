package com.example.napoleontest.login;

import com.example.napoleontest.domain.model.User;

import java.util.List;

public interface LoginActivityView {

    String getIdUser();

    void showMessage(String message);
    void setAnimationCredentials();

    void showProgressDialog();
    void hideProgressDialog();

    void getDataUser(List<User> mListUser);
}
