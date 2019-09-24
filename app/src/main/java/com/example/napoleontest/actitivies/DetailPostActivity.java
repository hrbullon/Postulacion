package com.example.napoleontest.actitivies;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.napoleontest.R;
import com.example.napoleontest.aplicattion.MasterApp;
import com.example.napoleontest.databinding.ActivityDetailPostBinding;
import com.example.napoleontest.domain.model.User;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class DetailPostActivity extends AppCompatActivity implements DetailPostView{

    ActivityDetailPostBinding mBinding;
    DetailPostPresenter mDetailPostPresenter;

    @Inject
    public Retrofit mRetrofit;

    @Inject
    public SecurityRepository mSecurityRepository;

    String  idPost,body,idUser, name, userName, userEmail, idUserLog;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail_post);
        MasterApp.getAppComponent().inject(this);


        initDetailPost();
    }

    private void initDetailPost() {
        Bundle bundle=getIntent().getExtras();
        idUserLog = bundle.getString("iduserlog");
        idUser = bundle.getString("iduser");
        idPost = bundle.getString("idpost");
        body = bundle.getString("body");

        mDetailPostPresenter = new DetailPostPresenter(this,this,mRetrofit,mSecurityRepository, idPost,idUser, idUserLog);
        mBinding.setPresenter(mDetailPostPresenter);

        mDetailPostPresenter.getDataUserPost(idUser);

        mBinding.txvBody.setText(body);

    }

    @Override
    public String getBody() {
        return mBinding.txvBody.toString().trim();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void getUsers(List<User> mListUser) {

        for(int i = 0;i<mListUser.size(); i ++){
            if(idUser.equals(mListUser.get(i).getId())){
                idUser = mListUser.get(i).getId();
                name = mListUser.get(i).getName();
                userName = mListUser.get(i).getUsername();
                userEmail = mListUser.get(i).getEmail();
                break;
            }
        }
        mBinding.txvName.setText("Publicado por: " + name);
        mBinding.txvEmail.setText("Email: " + userEmail);
    }

    @Override
    public void showAddFavorites(int message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        Toast.makeText(this,R.string.error_add_favorites, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}
