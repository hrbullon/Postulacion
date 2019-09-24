package com.example.napoleontest.fragment;

import com.example.napoleontest.domain.model.PostUser;

import java.util.ArrayList;
import java.util.List;

public interface FavoriteView {
    void showProgressDialog();
    void hideProgressDialog();

    void getAllDataPost(List<PostUser> mListPostUser);

    void showMessage(String aja);

    void getDataFavoritePost(ArrayList<PostUser> mListFavorite);
}
