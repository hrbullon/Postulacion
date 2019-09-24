package com.example.napoleontest.sqlite;

@SuppressWarnings("WeakerAccess")
public class SqlitePost {

    public static final String TABLE_POST = "post";
    public static final String COL_ID = " id";
    public static final String COL_POST_ID = " id_post";
    public static final String COL_POST_BODY = " body";
    public static final String COL_POST_USER = " userId";
    public static final String COL_POST_TITLE = " title";



    public static final String DB_POST = "CREATE TABLE " + TABLE_POST + "(" + COL_ID + " INTEGER PRIMARY KEY, "
            + COL_POST_ID + " TEXT, " + COL_POST_BODY + " TEXT, " + COL_POST_USER + " TEXT, " + COL_POST_TITLE + " TEXT " +   ")";
}
