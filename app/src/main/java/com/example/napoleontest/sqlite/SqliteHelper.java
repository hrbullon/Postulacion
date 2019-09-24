package com.example.napoleontest.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SqliteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "test_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = SqliteHelper.class.getSimpleName();

    public SqliteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //  this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqliteFavorites.DB_FAVORITE);
        db.execSQL(SqlitePost.DB_POST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SqliteFavorites.DB_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + SqlitePost.DB_POST);
        onCreate(db);
    }

    public boolean insertData(String table, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, values);
        if (result == -1){
            Log.d(TAG, "ah fallado el guardado!");
            return false;
        }else{
            Log.d(TAG, "se ha guardado exitosamnte");
            return true;
        }
    }

    public boolean checkExistData(String idPost, String idUser) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT *from favorite where " + SqliteFavorites.COL_FAVORITE_ID_POST + " = " + idPost + " and " + SqliteFavorites.COL_FAVORITE_ID_USER + " = " + idUser,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0){
            Log.d(TAG, "return true");
            return true;
        }
        return false;

    }

    public boolean countDataTablePost() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT *from post " ,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0){
            Log.d(TAG, "return true");
            return true;
        }
        return false;
    }
}
