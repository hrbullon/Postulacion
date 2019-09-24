package com.example.napoleontest.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.napoleontest.R;
import com.example.napoleontest.aplicattion.MasterApp;
import com.example.napoleontest.databinding.ActivityLoginBinding;
import com.example.napoleontest.domain.model.User;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.main.MainActivity;
import com.example.napoleontest.utilities.CustomToast;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements LoginActivityView{
    private static View view;
    private ActivityLoginBinding mBinding;
    private LoginActivityPresenter mLoginActivityPresenter;
    private String name, userName, userEmail,idUser;
    boolean userfind = false;
    ProgressDialog pd;

    private static Animation shakeAnimation;

    @Inject
    public Retrofit mRetrofit;

    @Inject
    public SecurityRepository mSecurityRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        view = mBinding.getRoot();
        MasterApp.getAppComponent().inject(this);

        getSupportActionBar().hide();
        initLogin();
    }

    private void initLogin() {
        //mLoginActivityPresenter = new LoginActivityPresenter(this, this);
        mLoginActivityPresenter = new LoginActivityPresenter(this, this, mRetrofit, mSecurityRepository);
        mBinding.setPresenter(mLoginActivityPresenter);

        shakeAnimation = AnimationUtils.loadAnimation(this,
                R.anim.shake);
    }

    @Override
    public String getIdUser() {
        return mBinding.iduser.getText().toString();
    }

    @Override
    public void showMessage(String prueba) {
        Toast.makeText(this,prueba,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAnimationCredentials() {
        mBinding.loginLayout.startAnimation(shakeAnimation);
        new CustomToast().Show_Toast(this, view,
                "Se requiere ingrese su ID.");
    }

    @Override
    public void showProgressDialog() {
        pd = new ProgressDialog(this);
        pd.setMessage("Ingresando");
        pd.show();
    }

    @Override
    public void hideProgressDialog() {
        pd.dismiss();
    }

    @Override
    public void getDataUser(List<User> mListUser) {

        for(int i = 0;i<mListUser.size(); i ++){

            if(getIdUser().equals(mListUser.get(i).getId())){
                idUser = mListUser.get(i).getId();
                name = mListUser.get(i).getName();
                userName = mListUser.get(i).getUsername();
                userEmail = mListUser.get(i).getEmail();
                userfind = true;
                break;
            }
        }
        //user found
        if(userfind){
            callToMainActivity();
        }else{
            Toast.makeText(this, R.string.user_not_find,Toast.LENGTH_LONG).show();
            mBinding.iduser.setText("");
        }
    }

    private void callToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("idUser",idUser);
        intent.putExtra("name",name);
        intent.putExtra("userName",userName);
        intent.putExtra("userEmail",userEmail);
        startActivity(intent);

    }
}
