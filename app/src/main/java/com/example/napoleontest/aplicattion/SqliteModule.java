package com.example.napoleontest.aplicattion;

import android.content.Context;

import com.example.napoleontest.sqlite.SqliteHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SqliteModule {

    @Provides
    @Singleton
    public SqliteHelper provideSqliteRepository(Context mContext){
        return new SqliteHelper(mContext);
    }
}
