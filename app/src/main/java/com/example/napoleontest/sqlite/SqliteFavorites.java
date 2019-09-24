package com.example.napoleontest.sqlite;

@SuppressWarnings("WeakerAccess")
public class SqliteFavorites {

    public static final String TABLE_FAVORITE = "favorite";
    public static final String COL_FAVORITE_ID = " id";
    public static final String COL_FAVORITE_ID_POST = " idpost";
    public static final String COL_FAVORITE_ID_USER = " iduser";


    public static final String DB_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + "(" + COL_FAVORITE_ID + " INTEGER PRIMARY KEY, "
            + COL_FAVORITE_ID_POST + " TEXT, " + COL_FAVORITE_ID_USER + " TEXT " + ")";
}
