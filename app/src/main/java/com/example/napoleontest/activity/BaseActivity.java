package com.example.napoleontest.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.napoleontest.aplicattion.MasterApp;
import com.example.napoleontest.Infraestructure.InternetManager;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Inject
    protected InternetManager mInternetManager;

    public static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        MasterApp.setCurrentScreenTag(getTAG());

    }

    public abstract String getTAG();

    public void showProgressDialog(String title, String message) {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
        }

        if (!mProgressDialog.isShowing())
        {
            mProgressDialog.setTitle(title);
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if(mProgressDialog!=null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    public void showAlertDialog(@Nullable String title, @Nullable String message, @Nullable String okButton, @Nullable DialogInterface.OnClickListener okListener, @Nullable String cancelButton, @Nullable DialogInterface.OnClickListener cancelListener, boolean cancelable)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title).setMessage(message);

        if (okButton != null && okListener != null)
        {
            builder.setPositiveButton(okButton, okListener);
        }

        if (cancelButton != null && cancelListener != null)
        {
            builder.setNegativeButton(cancelButton, cancelListener);
        }

        builder.setCancelable(cancelable);

        builder.show();
    }

    public void showAlertDialog(View customView, @Nullable String title, boolean cancelable)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);

        builder.setView(customView);

        builder.setCancelable(cancelable);

        builder.show();
    }
}
