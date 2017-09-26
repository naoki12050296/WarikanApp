package com.example.naokihonda.calculator;

import android.provider.BaseColumns;

import static com.example.naokihonda.calculator.Form.warikan;

/**
 * Created by naokihonda on 2017/09/25.
 */

public final class UserContract {

    //何もしない
    public UserContract(){

    }

    public static abstract class Users implements BaseColumns{  //_id
        public static final String TABLE_NAME = "Accounting_history";
        public static final String COL_SUMPRICE = "sum_price";
        public static final String COL_SUMPERSONS = "sum_persons";
        public static final String COL_CAMPAIGN = "campaign";
        public static final int COL_RESULT = warikan;
        public static final String COL_NAME = "name";
        public static final String COL_SCORE = "score";
    }


}
