package com.example.napoleontest.main;

import android.content.Context;

import com.example.napoleontest.sqlite.SqliteController;

public class MainActivityPresenter {

    private MainActivityView mView;
    private Context context;
    SqliteController mSqliteController;

    public MainActivityPresenter(MainActivityView mView, Context context) {
        this.mView = mView;
        this.context = context;
        mSqliteController = new SqliteController(context);

    }



}
