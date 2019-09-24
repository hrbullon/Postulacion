package com.example.napoleontest.fragment;

import android.content.Context;

import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.networkservice.NetworkAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyPostPresenter {

    private MyPostView mView;
    private Context context;
    private Retrofit retrofit;
    private SecurityRepository mSecurityRepository;

    public MyPostPresenter(MyPostView mView, Context context, Retrofit mRetrofit, SecurityRepository mSecurityRepository) {
        this.mView = mView;
        this.context = context;
        this.retrofit = mRetrofit;
        this.mSecurityRepository = mSecurityRepository;
    }


    public void initDataPostUserRecyclerview(String idUser) {
        mView.showProgressDialog();
        Call call = retrofit.create(NetworkAPI.class).getAllPostUser(idUser);
        call.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {

                if(response.code() == 200){
                    mView.hideProgressDialog();
                    List<PostUser> mListPostUser = (List<PostUser>)response.body();
                    mView.getDataUser(mListPostUser);
                }

            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {

            }
        });
    }
}
