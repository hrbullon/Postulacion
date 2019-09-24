package com.example.napoleontest.fragment;

import android.content.Context;
import android.util.Log;

import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.networkservice.NetworkAPI;
import com.example.napoleontest.sqlite.SqliteController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllPostPresenter {

    private AllPostView mView;
    private Context context;
    private Retrofit retrofit;
    private SecurityRepository mSecurityRepository;
    SqliteController mSqliteController;

    public AllPostPresenter(AllPostView mView, Context context, Retrofit retrofit, SecurityRepository mSecurityRepository) {
        this.mView = mView;
        this.context = context;
        this.retrofit = retrofit;
        this.mSecurityRepository = mSecurityRepository;
        mSqliteController = new SqliteController(context);
    }


    public void initDataPostAllRecyclerview(String idUser) {
        mView.showProgressDialog();
        Call call = retrofit.create(NetworkAPI.class).getAllPost();
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
            mView.hideProgressDialog();
                if(response.code() == 200){
                    List<PostUser> mListPostUser = (List<PostUser>)response.body();
                    mView.getDataPost(mListPostUser);
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                mView.hideProgressDialog();
                Log.i("error",t.getMessage());
            }
        });

    }

    public boolean checkExistDataTablePost() {
        return (mSqliteController.checkDataTablePost());
    }

    public void savePostSqlite(List<PostUser> mListPost) {
        if(mSqliteController.saveAllPost(mListPost)){
            mView.showSaveSqlite();
        }
    }
}
