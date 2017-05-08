package com.example.video.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Video on 2017/2/28.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context) {
        super(context, "newsdb.db", null, 1);//创建数据库
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //建立数据表
        db.execSQL("create table type(_id integer primary key autoincrement,subid integer,subgroup text)");
        db.execSQL("create table news (_id integer primary key autoincrement,type integer,nid integer,stamp text,icon text,title text,summary text,link text)");
        db.execSQL("create table lovenews(_id integer primary key autoincrement,type integer,nid integer,stamp text,icon text,title text,summary text,link text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
