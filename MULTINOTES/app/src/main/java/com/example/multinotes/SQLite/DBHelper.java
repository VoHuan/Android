package com.example.multinotes.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Note";
    public static final int DB_VERSION = 16;

    public DBHelper(Context context) {
        super(context, DB_NAME, null , DB_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Note(id integer primary key AUTOINCREMENT ,title text , time TIMESTAMP, content text, hour int, minute int, image blod, status int )";
        sqLiteDatabase. execSQL (sql);
    }
    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql ="  DROP TABLE IF EXISTS Note";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

}

