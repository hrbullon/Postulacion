package com.example.napoleontest.actitivies;

import android.content.Context;
import android.widget.Toast;

import com.example.napoleontest.R;
import com.example.napoleontest.domain.model.User;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.networkservice.NetworkAPI;
import com.example.napoleontest.sqlite.SqliteController;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailPostPresenter {

    private DetailPostView mView;
    private Context context;
    private Retrofit retrofit;
    private SecurityRepository mSecurityRepository;
    SqliteController mSqliteController;
    String idPost;
    String idUser;
    String idUserlog;

    public DetailPostPresenter(DetailPostView mView, Context context, Retrofit retrofit, SecurityRepository mSecurityRepository,String idPost, String idUser, String  iduserlog) {
        this.mView = mView;
        this.context = context;
        this.retrofit = retrofit;
        this.mSecurityRepository = mSecurityRepository;
        this.idPost = idPost;
        this.idUser = idUser;
        this.idUserlog = iduserlog;
        mSqliteController = new SqliteController(context);
    }

    public void onBackClick(){
        mView.finishActivity();
    }

    public void onAddFavoritesClick(){

        if(!mSqliteController.checkExistFavorite(idPost, idUserlog)){
            if(addPostToFavorites(idPost)){
                mView.showAddFavorites(R.string.add_favorites);
            }else{
                mView.showError();
            }
        }else{
            mView.showMessage("Ya el post está incluido en favoritos");
        }


    }

    private boolean addPostToFavorites(String idPost) {
          return  mSqliteController.savePostFavorite(idPost,idUserlog);
    }

    public void getDataUserPost(String idUser) {

        Call call = retrofit.create(NetworkAPI.class).getDataPostUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.code()== 200){

                    List<User> mListUser = (List<User>) response.body();
                    mView.getUsers(mListUser);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
