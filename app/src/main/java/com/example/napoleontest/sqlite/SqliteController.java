package com.example.napoleontest.sqlite;

import android.content.ContentValues;
import android.content.Context;

import com.example.napoleontest.domain.model.PostUser;

import java.util.List;

public class SqliteController {

    private SqliteHelper sqliteHelper;
    Context context;

    public SqliteController(Context mContext) {
        this.context = mContext;
        sqliteHelper = new SqliteHelper(context);
    }

    public boolean savePostFavorite(String idPost, String idUser) {
        ContentValues values = new ContentValues();
        values.put(SqliteFavorites.COL_FAVORITE_ID_POST, idPost);
        values.put(SqliteFavorites.COL_FAVORITE_ID_USER, idUser);

        return sqliteHelper.insertData(SqliteFavorites.TABLE_FAVORITE, values);
    }

    public boolean checkExistFavorite(String idPost, String idUser) {
        return sqliteHelper.checkExistData(idPost, idUser);
    }

    public boolean saveAllPost(List<PostUser> mListPostUser) {
        ContentValues values = new ContentValues();
        int i = 0;
        while (i < mListPostUser.size()) {
            values.put(SqlitePost.COL_POST_ID, mListPostUser.get(i).getId());
            values.put(SqlitePost.COL_POST_USER, mListPostUser.get(i).getUserId());
            values.put(SqlitePost.COL_POST_TITLE, quitarSaltos(mListPostUser.get(i).getTitle()));
            values.put(SqlitePost.COL_POST_BODY, quitarSaltos(mListPostUser.get(i).getBody()));

            i = i + 1;
            sqliteHelper.insertData(SqlitePost.TABLE_POST, values);
        }
        if (i == mListPostUser.size()) {
            return true;
        }
        return false;
    }

    private String quitarSaltos(String cadena) {
        return cadena.replaceAll("\n", "");
    }

    public boolean checkDataTablePost() {
        return sqliteHelper.countDataTablePost();
    }
}
