package com.example.naokihonda.calculator;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.naokihonda.calculator.Form.warikan;


/**
 * Created by naokihonda on 2017/09/25.
 */

public class UserOpenHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "cal.db";

    private final static int DB_VER = 1;

    public UserOpenHelper(Context c){
        super(c,DB_NAME,null,DB_VER);
    }



    /*
 * onCreateメソッド
 * データベースが作成された時に呼ばれます。
 * テーブルの作成などを行います。
 */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        sql += "create table MyTable (";
        sql += "No integer primary key autoincrement";
        sql += ",Date string";
        sql += ",Title string";
        sql += ",Result integer";
        sql += ")";
        db.execSQL(sql);
    }

    /*
     * onUpgradeメソッド
     * onUpgrade()メソッドはデータベースをバージョンアップした時に呼ばれます。
     * 現在のレコードを退避し、テーブルを再作成した後、退避したレコードを戻すなどの処理を行います。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

