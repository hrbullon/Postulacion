package com.example.napoleontest.fragment;

import android.content.Context;
import android.util.Log;

import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.networkservice.NetworkAPI;
import com.example.napoleontest.sqlite.SqliteController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FavoritePresenter {

    private FavoriteView mView;
    private Context context;
    String idUser;
    private Retrofit retrofit;
    private SecurityRepository mSecurityRepository;
    SqliteController mSqliteController;

    public FavoritePresenter(FavoriteView mView, Context context, Retrofit retrofit, SecurityRepository mSecurityRepository, String idUser) {
        this.mView = mView;
        this.context = context;
        this.retrofit = retrofit;
        this.mSecurityRepository = mSecurityRepository;
        this.idUser = idUser;
        mSqliteController = new SqliteController(context);
    }


    public void initDataFavorite() {
        mView.showProgressDialog();
        Call call = retrofit.create(NetworkAPI.class).getAllPost();
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                mView.hideProgressDialog();
                if(response.code() == 200){
                    List<PostUser> mListPostUser = (List<PostUser>)response.body();
                    mView.getAllDataPost(mListPostUser);
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                mView.hideProgressDialog();
                Log.i("error",t.getMessage());
            }
        });

    }

    public void selectMyFavoritePost(List<PostUser> mListClean) {
        ArrayList<PostUser> mListFavorite = new ArrayList<PostUser>();
        int i=0;
        while(i<mListClean.size()){

            if(mSqliteController.checkExistFavorite(mListClean.get(i).getId(),idUser)){
                mListFavorite.add(mListClean.get(i));
            }
            i = i + 1;
        }

        mView.getDataFavoritePost(mListFavorite);

    }
}
