package com.example.napoleontest.actitivies;

import com.example.napoleontest.domain.model.User;

import java.util.List;

public interface DetailPostView {

    String getBody();



    void finishActivity();

    void getUsers(List<User> mListUser);

    void showAddFavorites(int message);

    void showError();

    void showMessage(String message);
}
