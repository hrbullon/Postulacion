package com.example.napoleontest.fragment;

import com.example.napoleontest.domain.model.PostUser;

import java.util.List;

public interface MyPostView {

    void getDataUser(List<PostUser> mListPostUser);

    void showProgressDialog();
    void hideProgressDialog();
}
