package com.example.napoleontest.fragment;

import com.example.napoleontest.domain.model.PostUser;

import java.util.List;

public interface AllPostView {
    void getDataPost(List<PostUser> mListPostUser);

    void showProgressDialog();

    void hideProgressDialog();

    void showSaveSqlite();
}
