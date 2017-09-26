package com.example.naokihonda.calculator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.naokihonda.calculator.Form.warikan;


/**
 * Created by naokihonda on 2017/09/25.
 */

public class UserOpenHelper extends SQLiteOpenHelper {

    public UserOpenHelper(Context context){
        super(context, "calculator.db", null, 1);
    }
        public static final String DB_NAME = "myapp.db";
        public static final int DB_VERSION = 1;
        public static final String CREATE_TABLE =
                "create table" + UserContract.Users.TABLE_NAME + "(" +
                        UserContract.Users._ID + "_id integer primary key autoincrement," +
                        UserContract.Users.COL_RESULT + warikan + ")";

                /*UserContract.Users.COL_SUMPRICE + "integer," +
                UserContract.Users.COL_SUMPERSONS + "integer," +
                UserContract.Users.COL_RESULT + "integer)";*/

        public static final int INIT_TABLE =
                Integer.parseInt("insert into Accounting_history (warikan) values " +
                        warikan);

        public static final String DROP_TABLE =
                "drop table if exists Accounting_history";

        @Override
        public void onCreate (SQLiteDatabase sqLiteDatabase){
            //create table テーブル作成
            sqLiteDatabase.execSQL(CREATE_TABLE);
            //init table 初期データ作成
            sqLiteDatabase.execSQL(String.valueOf(INIT_TABLE));
        }

        @Override
        public void onUpgrade (SQLiteDatabase sqLiteDatabase,int i, int i1){
            //drop table
            sqLiteDatabase.execSQL(DROP_TABLE);
            //onCreate
            onCreate(sqLiteDatabase);
        }
    }

